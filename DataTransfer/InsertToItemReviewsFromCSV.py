import pandas as pd
import numpy as np
import os
from CRUD import DBController
from pymysql.err import DataError

def convertToReviews(PATH):
    rows = []
    review_data = pd.read_csv(PATH, index_col=False)
    review_data['가격'] = review_data['가격'].str.replace(',', '').astype('int64')
    
    item_names = review_data['제품명'].unique()
    for item_name in item_names:
        
        item = review_data[review_data['제품명'] == item_name]
        
        db = DBController()
        sql = f'select item_seq from items where item_name = "{item_name}"'
        item_seq = db.selectOne(sql)
        db.connClose()
        
        temp = item.groupby(['제품명', '카테고리'])[['별점', '가격', '감성점수']].mean()
        
        for i in range(len(temp)):
            cur = temp.iloc[i, :]
            
            item_cate = temp.index[i][1]
            item_rate = round(temp.iloc[i, :]['별점'], 1)
            item_sentiment = round(temp.iloc[i, :]['감성점수'], 3)
            
            rows.append([item_seq['item_seq'], item_cate, item_sentiment, item_rate])
                
    return rows

# 파일 경로 넣어서 ITEMS 테이블에 필요한 [제품명, 가격]으로 반환
def convertToItems(PATH):
    rows = []
    review_data = pd.read_csv(PATH, index_col=False)
    review_data['가격'] = review_data['가격'].str.replace(',', '').astype('int64')
        
    item_names = review_data['제품명'].unique()
    
    for item_name in item_names:
        item = review_data[review_data['제품명'] == item_name].iloc[0, :]
        rows.append([item['제품명'], item['가격']])
    
    return rows

# def convertToItemsSentiment(PATH):
#     rows = []
#     review_data = pd.read_csv(PATH, index_col=False)
#     review_data['가격'] = review_data['가격'].str.replace(',', '').astype('int64')
        
#     item_names = review_data['제품명'].unique()
    
#     for item_name in item_names:
#         item = review_data[review_data['제품명'] == item_name].iloc[0, :]
#         rows.append([item['제품명'], item['가격']])
    
#     return rows

if __name__ == "__main__":
    # Item 테이블에 데이터 삽입
    PATH = 'C:/2nd_project/SSM/Crawling/CSV/ScoredData/'
    file_list = os.listdir(PATH)
    file_list = [file for file in file_list if file[-4:] == '.csv']    # csv파일만

    # 파일 목록 전체 반복
    for file in file_list:
        rows = convertToItems(PATH + str(file))

        db = DBController()

        for row in rows:
            try:
                sql = f'insert into items (our_seq, item_name, item_price) values (1, "{row[0]}", "{row[-1]}")'
                db.execute(sql)
            except DataError:
                print(row)
        
        db.connClose()
        print(f"{file} 트랜잭션 끝")
        
    # Reviews 테이블에 데이터 삽입
    for file in file_list:
        rows = convertToReviews(PATH + str(file))

        
        db = DBController()

        for row in rows:
            try:
                sql = f'insert into reviews (item_seq, review_category, review_sentiment, review_star) values ("{row[0]}", "{row[1]}", "{row[2]}",  {row[3]})'
                db.execute(sql)
            except DataError:
                print(row)
        
        db.connClose()
        print(f"{file} 작업 끝")