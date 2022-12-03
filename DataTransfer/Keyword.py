import pandas as pd
import numpy as np
from CRUD import DBController

class keyword:
    def __init__(self, path='./SSM/BroadCast_Data/KEYWORD.csv'):
        self.path = path
        self.data = pd.read_csv(path, index_col=False)
    
    # 키워드 데이터 추가
    def add(self, name, category):
        df = pd.DataFrame({'키워드' : name, '카테고리' : category}, index=[0])
        
        self.data = pd.concat([self.data, df], ignore_index=True)
    
    # 사이즈 반환
    def size(self):
        return len(self.data)
    
    # csv파일 저장
    def save(self):
        self.data.to_csv(self.path, encoding='utf-8')
        
    # DB에 갱신 (Update Keywords Table)
    # -> csv파일 불러와서 현재 DB에 저장된 값이 아닌 값만 insert(새로 csv파일에 추가된 값)
    def dbRefresh(self):
        db = DBController()
        rows = db.selectAll('keywords') # 현재 테이블에 있는 keywords 모든 row 가져오기
        
        if len(rows) > 0:
            keyword_name_set = set([i['keyword_name'] for i in rows])   #모든 row의 키워드
            cur_name_set = set([i['키워드'] for i in self.data])    # csv파일의 모든 키워드
            diff = cur_name_set - keyword_name_set  # db엔 없고 csv파일에만 있는 키워드 구하기
            
            # 값이 있으면
            if len(diff) > 0:
                for i in diff:
                    name = i    # 현재 for문 키워드
                    category = self.data[self.data['키워드'] == name].iloc[0, 1]    # 해당 키워드의 카테고리 값
                    sql = f"insert into keywords(keyword_name, keyword_category) values ('{name}', '{category}')"
                    db.execute(sql)
                    
        # db에 아예 값이 없을땐 모두 insert
        else:
            for i in range(len(self.data)):
                name = str(self.data.iloc[i]['키워드'])
                print(name)
                category = str(self.data.iloc[i]['카테고리'])
                print(category)
                sql = f"insert into keywords(keyword_name, keyword_category) values ('{name}', '{category}')"
                db.execute(sql)
                
        db.connClose()
        
if __name__ == "__main__":
    kw = keyword()
    kw.dbRefresh()