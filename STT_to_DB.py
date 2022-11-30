import pandas as pd
import numpy as np
import Keyword

# 방송 STT 데이터 파일 리스트
def get_broadcast_stt(filename, filepath = './SSM/BroadCast_Data/broadcast'):
    FILEPATH = filepath + "/" + filename
    data = pd.read_csv(FILEPATH, encoding='euc-kr', index_col=0)
    
    return data

# 키워드 : 등장 시간(분) 쌍의 딕셔너리 변환
def data_transform(data):
    kw = Keyword.keyword()
    
    # 정규표현식 활용, 한글과 공백 빼고 모두 제거
    data['stt_word'] = data['stt_word'].str.replace("[^ㄱ-ㅎㅏ-ㅣ가-힣 ]","")
    data['stt_word'].replace('', np.nan, inplace=True)
    
    # 등장 단어 : 등장 시간 리스트 쌍
    word_appear = {}

    for i in range(kw.size()):
        keyword = kw.data.iloc[i, 0]
        
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
        for v in value:
            final.append((key, v))
    
    k = [f[0] for f in final]
    v = [f[1] for f in final]
    result = pd.DataFrame({'keyword' : k, 'category' : v})

    return result

if __name__ == "__main__":
    filename = '비비엔다아이싱침구편1.csv'
    data = get_broadcast_stt(filename)
    result = data_transform(data)
    print(result)