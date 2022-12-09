from CRUD import DBController
import pandas as pd
import numpy as np
import os


def convertToViewerReaction(filename, filepath='C:/2nd_project/SSM/ViewerReaction_Data/'):
    
    db = DBController()
    sql = f'select bc_seq from broadcasting where bc_title like "%{filename[:-4]}%"'
    bc_seq = db.selectOne(sql)['bc_seq']
    db.connClose()
    
    data = pd.read_csv(filepath+filename, index_col=False, encoding='utf-8')
    
    for i in range(len(data)):
        vr_VO = data.iloc[i]
        vr_times = vr_VO['시간']
        vr_viewers = vr_VO['시청자수']
        vr_sales = vr_VO['결제']
        vr_lookings = vr_VO['상품조회']
        vr_baskets = vr_VO['장바구니']
        vr_comments = vr_VO['댓글수']
        vr_wishlists = vr_VO['찜']
        
        sql = f'''
        insert into viewer_reaction (bc_seq, vr_times, vr_viewers, vr_sales, vr_lookings, vr_baskets, vr_comments, vr_wishlists)
        values ({bc_seq}, {vr_times}, {vr_viewers}, {vr_sales}, {vr_lookings}, {vr_baskets}, {vr_comments}, {vr_wishlists})
        '''
        db = DBController()
        db.execute(sql)
        db.connClose()
        
        print(f'{filename} 트랜잭션 작업 끝')

if __name__ == "__main__":
    PATH = 'C:/2nd_project/SSM/ViewerReaction_Data/'
    # file_list = os.listdir(PATH)
    # for file in file_list:
    file = '22년 얼리버드 썸머특가! 비비엔다 인견편.csv'
    convertToViewerReaction(file, filepath=PATH)