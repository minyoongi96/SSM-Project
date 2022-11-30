from selenium import webdriver as wb
from selenium.webdriver.common.by import By
from selenium.webdriver.common.keys import Keys
import time
import pandas as pd
from navershopping import naver_review
from vivienda import brand_review
import os
from datetime import datetime

def review_to_csv(url_list):
    # os.chdir('C:/Crawling')
    # print(os.getcwd())
    
    now = datetime.now()
    timeformat = str(now.year)[-2:] + str(now.month) + str(now.day) + str(now.hour) + str(now.minute)

    naver_list = []
    brand_list = []
    
    # 쇼핑 페이지인지 비비엔다 페이지인지 구분해서 리스트에 다시 담기
    for url in url_list:
        if 'brand.naver.com/vivienda' in url:
            brand_list.append(url)
        elif 'search.shopping.naver.com' in url:
            naver_list.append(url)
        else:
            pass      # 잘못된 url 방지
        
    # url 종류에 따라 크롤링 하고 csv파일로 저장
    brand_review_filename = f'{timeformat}_brandShopping'
    naver_review_filename = f'{timeformat}_naverShopping'
    total_csv_name = f'{timeformat}_totalReview.csv'
    
    brand_review(brand_list,brand_review_filename)
    naver_review(naver_list,naver_review_filename)
    time.sleep(3)
    
    # 저장한 두 파일 다시 읽어오기
    brandShopping = pd.read_csv(f'C:/Crawling/CSV/{brand_review_filename}.csv')
    naverShopping = pd.read_csv(f'C:/Crawling/CSV/{naver_review_filename}.csv')
    
    # 삭제
    os.remove(f'C:/Crawling/CSV/{brand_review_filename}.csv')
    os.remove(f'C:/Crawling/CSV/{naver_review_filename}.csv')
    
    # 같은 방송의 제품 url들 이니까 다시 합쳐서 "데이터프레임화" 시키기
    data = pd.concat([brandShopping,naverShopping],axis=0)
    # 저장
    data.to_csv(f"C:/Crawling/CSV/{total_csv_name}",index=False, encoding='UTF-8-sig')
    
if __name__ == "__main__":
    url_list = ['https://search.shopping.naver.com/catalog/27618500205?query=%5B%EA%B5%AD%EB%AF%BC%ED%85%9C%EC%84%A0%EC%A0%95%5D%20%EB%B9%84%EB%B9%84%EC%97%94%EB%8B%A4%20%EB%AA%A8%EB%8B%AC%20%EB%8B%A4%EC%9A%B0%EB%8B%88%ED%8D%BC%20%EC%8B%A0%EC%83%9D%EC%95%84%20%EC%95%84%EA%B8%B0%20%EA%B2%A8%EC%9A%B8%20%EB%B8%94%EB%9E%AD%ED%82%B7%20%EC%9D%B4%EB%B6%88&NaPm=ct%3Dlb0dctmw%7Cci%3D6ac3bdc180097a4f9cfa5bf70b7e466a5c44c52e%7Ctr%3Dslsl%7Csn%3D95694%7Chk%3D567d5b38f08f9fe64d4329fca1a92f22c51f0538',
        'https://search.shopping.naver.com/catalog/28779824081?query=%EB%B9%84%EB%B9%84%EC%97%94%EB%8B%A4%20%EB%AA%A8%EB%8B%AC%20%EC%8B%A0%EC%83%9D%EC%95%84%20%EC%95%84%EA%B8%B0%20%EC%B0%A8%EB%A0%B5%EC%9D%B4%EB%B6%88&NaPm=ct%3Dlb0de35s%7Cci%3D80ad636eff54cce2f94a6de1e3800605e1a3d984%7Ctr%3Dslsl%7Csn%3D95694%7Chk%3D4e4b0d9831209ac942fcfcf5f75c6329aeb76b47',
        'https://search.shopping.naver.com/catalog/20573863257?query=%EB%B9%84%EB%B9%84%EC%97%94%EB%8B%A4%20%ED%94%84%EB%A6%AC%EB%AF%B8%EC%97%84%20%EC%9D%B4%EC%A4%91%EA%B1%B0%EC%A6%88%20%EC%95%84%EA%B8%B0%20%EB%B8%94%EB%9E%AD%ED%82%B7%20%EC%9D%B4%EB%B6%88&NaPm=ct%3Dlb0df2nk%7Cci%3D41015cb09c46915d3cd91bd18c7e9e4bce085fbe%7Ctr%3Dslsl%7Csn%3D95694%7Chk%3D9e50361a7912ac29c4192612488ae8f10abf6a41',
        'https://search.shopping.naver.com/catalog/32146891301?query=%EB%B9%84%EB%B9%84%EC%97%94%EB%8B%A4%20%EC%88%9C%EC%88%98%204%EC%A4%91%20%EA%B1%B0%EC%A6%88%20%EC%95%84%EA%B8%B0%20%EC%9E%90%EC%88%98%20%EB%B8%94%EB%9E%AD%ED%82%B7%20%EC%8B%A0%EC%83%9D%EC%95%84%20%EC%82%AC%EA%B3%84%EC%A0%88%20%EC%9D%B4%EB%B6%88%20%EB%8B%B4%EC%9A%94&NaPm=ct%3Dlb0dfiuw%7Cci%3D0c5fa253f5e23dafde54336e075ca7d1ebb064ac%7Ctr%3Dslsl%7Csn%3D95694%7Chk%3Dc30791c4b84b0ce4ef7ae776ef83445f318e7813','https://brand.naver.com/vivienda/products/7394795978?NaPm=ct%3Dlb0ddq1k%7Cci%3D400dd9f74e4566c70226dbef0edf10b8e6ec9033%7Ctr%3Dslsl%7Csn%3D435240%7Chk%3D8e7c21d00e64baa5b3cf0be396b79636b5b56471',
        'https://brand.naver.com/vivienda/products/7399664609?NaPm=ct%3Dlb0delog%7Cci%3D4aaae6065ba5ad10ed9bfa4a0956ead09c93c4af%7Ctr%3Dslsl%7Csn%3D435240%7Chk%3D0a58eebf1c119fc1425f5f09fed56a2f2861a449',
        'https://brand.naver.com/vivienda/products/2207423609?NaPm=ct%3Dlb0dfzu0%7Cci%3D1a71f347b9362eddc286cc07df447d9a23800d16%7Ctr%3Dslsl%7Csn%3D435240%7Chk%3D9cfd2a068b2c37cb262f5a0720d449b4ce577fc3',
        'https://brand.naver.com/vivienda/products/3923993000?NaPm=ct%3Dlb0dgf9k%7Cci%3D4ce270ad9d47b8458449bcf01919feb28390d96e%7Ctr%3Dslsl%7Csn%3D435240%7Chk%3D88d385421365c5793b6cf2ca5741777cc919c27e',
        'https://brand.naver.com/vivienda/products/3936774536?NaPm=ct%3Dlb0dh0vc%7Cci%3D6b1a12564aab6403d3c527b857b5acbd45b68869%7Ctr%3Dslsl%7Csn%3D435240%7Chk%3D798974a56560d6bab538de8097ae3cb7f0ead21b']
    url_list2 = ['https://brand.naver.com/vivienda/products/2917645926?NaPm=ct%3Dlb1hted4%7Cci%3D560f37928d6db260448bfdb735c4212e469ae059%7Ctr%3Dslsl%7Csn%3D435240%7Chk%3D7084c5f97e02ec73c9e2fc1396011246b6869290',
        'https://brand.naver.com/vivienda/products/2931198139?NaPm=ct%3Dlb1hwof4%7Cci%3D4121ab52e7882a922c3aa18f8c5bca8a18c7e2a9%7Ctr%3Dslsl%7Csn%3D435240%7Chk%3Db926de34da1040d9d8dedd7118700403ab878207',
        'https://brand.naver.com/vivienda/products/732145235?NaPm=ct%3Dlb1hxnww%7Cci%3D546b8da39921ccacc55e80438ef18c66a287760c%7Ctr%3Dslsl%7Csn%3D435240%7Chk%3D642fb4fecaeda325526064f3be8f8a39de1e8c2a',
        'https://brand.naver.com/vivienda/products/732040380?NaPm=ct%3Dlb1i0cd4%7Cci%3D1fe2449c977796e11f7e1eb388c9f2ee23b5a771%7Ctr%3Dslsl%7Csn%3D435240%7Chk%3D4fcfec9628aaf27476346a28ec4e552ac47c3ab4',
        'https://brand.naver.com/vivienda/products/2935859919?NaPm=ct%3Dlb1i1pqw%7Cci%3D904aab1a7f87ae3ff839908ca731cc769c241f66%7Ctr%3Dslsl%7Csn%3D435240%7Chk%3Dad1a600a9d1293b6d0edf22c7a18536534b4c904','https://search.shopping.naver.com/catalog/23502514592?query=%EB%B9%84%EB%B9%84%EC%97%94%EB%8B%A4%20%EB%85%BC%EC%8A%AC%EB%A6%BD%20%EC%9D%B8%EA%B2%AC%20%EC%95%84%EA%B8%B0%20%EC%BF%A8%EB%A7%A4%ED%8A%B8%20%EC%97%AC%EB%A6%84%20%EC%9D%B4%EB%B6%88%EC%84%B8%ED%8A%B8&NaPm=ct%3Dlb1hz90g%7Cci%3D0663057940c0d685d1b65551d9d27aa3d3a48473%7Ctr%3Dslsl%7Csn%3D95694%7Chk%3D3295ee7c0184fb0f169e56c32931c691f6690e3d',
        'https://search.shopping.naver.com/catalog/22256340956?query=%EB%B9%84%EB%B9%84%EC%97%94%EB%8B%A4%20%EC%96%91%EB%A9%B4%EC%82%AC%EC%9A%A9%20%EC%9D%B8%EA%B2%AC%20%EC%95%84%EA%B8%B0%20%EC%97%AC%EB%A6%84%ED%8C%A8%EB%93%9C%20%EC%9D%B4%EB%B6%88%EC%84%B8%ED%8A%B8&NaPm=ct%3Dlb1hzmwg%7Cci%3D3385ae2c9b522f8a37b0fb7732ceca743e2e53df%7Ctr%3Dslsl%7Csn%3D95694%7Chk%3Dddf957d3637c6956b25f3b437bf7d30a6e11b531',
        'https://search.shopping.naver.com/catalog/16764656061?query=%EB%B9%84%EB%B9%84%EC%97%94%EB%8B%A4%20%EC%82%AC%EA%B3%84%EC%A0%88%20%EB%B9%84%EB%B9%84%ED%95%84%EB%A1%9C%EC%9A%B0%20%EC%8B%A0%EC%83%9D%EC%95%84%20%EC%9C%A0%EC%95%84%20%EC%95%84%EA%B8%B0%20%EC%A7%B1%EA%B5%AC%EB%B2%A0%EA%B0%9C%20%EC%BF%A8%EB%B2%A0%EA%B0%9C&NaPm=ct%3Dlb1i0x74%7Cci%3Daef054c99f856e75f1edc907ec3d5f76e0ad6007%7Ctr%3Dslsl%7Csn%3D95694%7Chk%3Da633fa965bd6c4fd7d4e03fddc0891559d3ed6cf']
    url_list3 = ['https://brand.naver.com/vivienda/products/5601069502?NaPm=ct%3Dlb1ieguo%7Cci%3D4422188b0303b7a21cef09d3e65eb02124c4c5ce%7Ctr%3Dslsl%7Csn%3D435240%7Chk%3D47d1cda714f6a217f1b66c072623d2fd28558f7f',
        'https://brand.naver.com/vivienda/products/5656296576?NaPm=ct%3Dlb1if5jk%7Cci%3D19a74b649b4fd7c985d5c55096b74565f61c616f%7Ctr%3Dslsl%7Csn%3D435240%7Chk%3Db44efc10875b11f6eb21bb59f6f51507041fd425',
        'https://brand.naver.com/vivienda/products/6571589951?NaPm=ct%3Dlb1ih4j4%7Cci%3D4d05babc6dbe6cb5ba9bacd699fbe1b4e198d2ea%7Ctr%3Dslsl%7Csn%3D435240%7Chk%3Dad3ecd7c402df494b91e39e20453446928b1a056',
        'https://brand.naver.com/vivienda/products/6571813595?NaPm=ct%3Dlb1ihkqg%7Cci%3D13c0937548d52a44e10b76ee5b66c2492d3edb8a%7Ctr%3Dslsl%7Csn%3D435240%7Chk%3D7f9c9684151f901d4f1d1d539dbc4c06fee0bc25',
        'https://brand.naver.com/vivienda/products/6618836029?NaPm=ct%3Dlb1ii1pk%7Cci%3D19e7f71a09d292fc2220e522cd666d8ee0b77702%7Ctr%3Dslsl%7Csn%3D435240%7Chk%3Dc5486e9bb2d5fe9ca399cb2f6e0ad1e76f866412',
        'https://brand.naver.com/vivienda/products/6659507432?NaPm=ct%3Dlb1iimjk%7Cci%3Db5cd4605ba02b685e5ddd1b0deac3603068049a8%7Ctr%3Dslsl%7Csn%3D435240%7Chk%3D105541ebfa70cc75d2cc9feb0b287046d4ff23a3',
        'https://brand.naver.com/vivienda/products/5697936080?NaPm=ct%3Dlb1ij8x4%7Cci%3D72e3765034f72065b5debe404e3b783498615bcb%7Ctr%3Dslsl%7Csn%3D435240%7Chk%3Dd99d720548e756bbd506fff8be5cce50c7c74db8',
        'https://brand.naver.com/vivienda/products/5664275714?NaPm=ct%3Dlb1ijrfs%7Cci%3D98b40b72e0e8a8e0e91b19e80afb879ea618a78b%7Ctr%3Dslsl%7Csn%3D435240%7Chk%3D7f21f002d9a944edb0442024fd158194fe0c7749','https://search.shopping.naver.com/catalog/33213558481?query=%EB%B9%84%EB%B9%84%EC%97%94%EB%8B%A4%20%EC%95%84%EC%9D%B4%EC%8B%B1%20%EB%83%89%EA%B0%90%20%EC%95%84%EA%B8%B0%20%EC%9B%90%ED%98%95%EB%9F%AC%EA%B7%B8%20%EC%97%AC%EB%A6%84%20%EC%8B%A0%EC%83%9D%EC%95%84%20%EC%95%84%EC%9D%B4%EB%B0%A9%20%EB%86%80%EC%9D%B4%EB%A7%A4%ED%8A%B8&NaPm=ct%3Dlb1ifjfk%7Cci%3Db5b72589f62beb27abea332bce10b2743ea65847%7Ctr%3Dslsl%7Csn%3D95694%7Chk%3D9d4399d23258f1277c867130f720a2b7d78fbcf2',
        'https://search.shopping.naver.com/catalog/33065302691?query=%EB%B9%84%EB%B9%84%EC%97%94%EB%8B%A4%20%EC%95%84%EC%9D%B4%EC%8B%B1%20%EB%83%89%EA%B0%90%20%EC%95%84%EA%B8%B0%20%EB%B0%94%EB%94%94%ED%95%84%EB%A1%9C%EC%9A%B0%20%EB%A1%B1%EC%BF%A0%EC%85%98%20%EC%97%AC%EB%A6%84%20%EC%A3%BD%EB%B6%80%EC%9D%B8%20%EB%B2%A0%EA%B0%9C&NaPm=ct%3Dlb1igobs%7Cci%3D8f1be5233647dfa78a291667c3b0c49bb94502e1%7Ctr%3Dslsl%7Csn%3D95694%7Chk%3D8d38a9adf75fac4ee3b0e84dc01ff3823e0ccb70']
    
    total_url = [url_list, url_list2, url_list3]
    
    for url in total_url:
        review_to_csv(url)
    