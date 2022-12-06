import pandas as pd
import numpy as np
import os
from CRUD import DBController

# 키워드 : 등장 시간(분) 쌍의 딕셔너리 변환
def data_transform(filename, filepath = './SSM/BroadCast_Data/broadcast'):
    FILEPATH = filepath + '/' + filename
    data = pd.read_csv(FILEPATH, encoding='euc-kr', index_col=0)
    
    # bc_seq
    sql = f'select bc_seq from broadcasting where bc_title = "{filename[:-4]}"'
    db = DBController()
    bc_seq = db.selectOne(sql)['bc_seq']
    db.connClose()
    
    # Keywords 테이블에서 keyword_seq 가져오기
    db = DBController()
    kw_table = db.selectAll('keywords')
    db.connClose()
    
    # 정규표현식 활용, 한글과 공백 빼고 모두 제거
    data['stt_word'] = data['stt_word'].str.replace("[^ㄱ-ㅎㅏ-ㅣ가-힣 ]","")
    data['stt_word'].replace('', np.nan, inplace=True)
    data = data[data['stt_word'].notnull()]   # null 제외
    # 등장 단어 : 등장 시간 리스트 쌍
    word_appear = {}

    for i in range(len(kw_table)):
        keyword = kw_table[i]['keyword_name']
            
        if keyword not in word_appear:
            word_appear[keyword] = []
        
        word_appear[keyword]= data[data['stt_word'].str.contains(keyword)]['stt_time'].values
        
    # 한번도 안나온 단어들 검색
    no_word = []
    for key, value in word_appear.items():
        if value.size == 0:
            no_word.append(key)

    # 한번도 안나온 단어들 딕셔너리에서 삭제
    for n in no_word:
        del word_appear[n]
    
    # 단어 등장 시간 초 -> 분단위로 변환
    word_appear_minute = {}
    for key, value in word_appear.items():
        word_appear_minute[key] = [int(v.split(":")[0]) for v in value if len(v) < 6]
        
    # final -> 데이터프레임으로 만들기
    # 등장시간 | 키워드 DataFrame 만들기
    final = []
    for key, value in word_appear_minute.items():
        db = DBController()
        sql = f'select keyword_seq from keywords where keyword_name = "{key}"'
        keyword_seq = db.selectOne(sql)['keyword_seq']
        db.connClose()
        
        for time in value:
            sql = f'''
            select vr_seq from viewer_reaction where vr_times = {time}
            '''
            db = DBController()
            vr_seq = db.selectOne(sql)['vr_seq']
            db.connClose()
            
            final.append({'bc_seq':bc_seq, 'speech_keyword':key, 'speech_time':time, 'keyword_seq':keyword_seq, 'vr_seq':vr_seq})

    for i in final:
        sql = f'''
            insert into speech_analysis (bc_seq, speech_keyword, speech_time, keyword_seq, vr_seq)
            values ({i['bc_seq']}, "{i['speech_keyword']}", {i['speech_time']}, {i['keyword_seq']}, {i['vr_seq']})
            '''
        db = DBController()
        db.execute(sql)
        db.connClose()
        print('f{filename} 트랜잭션 작업 끝')

if __name__ == "__main__":
    PATH = 'C:/JupyterProject/SSM/BroadCast_Data/'
    file_list = os.listdir(PATH)
    print(file_list)
    # for file in file_list:
    #     data_transform(file, filepath=PATH)
    