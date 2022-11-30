import pymysql
import pandas as pd
import numpy as np
import os


class DBController:
    # DB 연결 정보
    def __init__(self, HOST='project-db-stu.ddns.net', PORT=3307, USER = 'gogum0203', PASSWD='Alwjddlwh123!', DB='gogum0203', CHARSET='utf8'):
        # 연결 객체
        self.conn = pymysql.connect(host=HOST,
                                    port=PORT,
                                    user=USER,
                                    passwd=PASSWD,
                                    db=DB,
                                    charset=CHARSET)
    # 커서 객체
    def cursor(self):
        return self.conn.cursor(pymysql.cursors.DictCursor)
    
    # CRUD 쿼리
    def execute(self, sql):
        with self.cursor() as cur:
            cur.execute(sql)
            self.commit()
        
    def selectWhere(self, sql):
        with self.cursor() as cur:
            cur.execute(sql)
            result = cur.fetchall()
        return result
    
    def selectAll(self, TABLE):
        sql = f'select * from {TABLE}'
        with self.cursor() as cur:
            cur.execute(sql)
            result = cur.fetchall()
        return result
    
    def selectOne(self, sql):
        with self.cursor() as cur:
            cur.execute(sql)
            result = cur.fetchone()
        return result
    
    def commit(self):
        self.conn.commit()
        
    # 모두 종료
    def connClose(self):
        self.conn.close()

if __name__ == "__main__":
    db = DBController()
    cate_names = ['726948', '506274', '634492']
    for val in cate_names:
        sql = f'insert into categories(cate_name) values({val})'
        db.execute(sql)
        
    db.connClose()
    