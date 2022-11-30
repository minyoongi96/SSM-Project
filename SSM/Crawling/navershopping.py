from selenium import webdriver as wb
from selenium.webdriver.common.by import By
from selenium.webdriver.common.keys import Keys
import time
import pandas as pd

def naver_review(url_list,csv_name):

    result = []

    for j in range(len(url_list)):
        
        driver = wb.Chrome('C:/Crawling/chromedriver.exe')
        driver.get(url_list[j])
        # print(f"{j}번째 url")
        driver.implicitly_wait(5)
    
        #제품 이름 저장
        product_name = driver.find_elements(By.CSS_SELECTOR, '#__next > div > div.style_container__D_mqP > div.style_inner__ZMO5R > div.top_summary_title__ViyrM > h2')[0].text
        #제품 가격 저장
        product_price = driver.find_elements(By.CSS_SELECTOR, '#__next > div > div.style_container__D_mqP > div.style_inner__ZMO5R > div.style_content_wrap__78pql > div.style_content__v25xx > div > div.summary_info_area__NP6l5 > div.lowestPrice_price_area__VDBfj > div.lowestPrice_low_price__Ypmmk > em')[0].text
        
        # 카테고리 저장 부분
        keyword_list_css = '#section_review > div.filter_sort_group__2G1QI > div.filter_evaluation_tap__V1J3G > div > ul > li > a'
        keyword_list = driver.find_elements(By.CSS_SELECTOR, keyword_list_css)[1:]

        # 카테고리 돌면서 데이터 저장
        for k in keyword_list:
            time.sleep(0.5)
            k.send_keys(Keys.ENTER)  # 카테고리 클릭
            time.sleep(1) # 안하면 에러남
            
            # print(f'{j}째url {k.text} 크롤링 시작')     # 내용 담은 변수 프린트 & 진행도 체크용

            do = 0  # 반복문 탈출을 위한 임시 변수

            ## -------페이지 넘기기-------
            # 페이지 바가 있으면 변수에 페이지 바, 현재페이지를 저장
            try:
                page_bar = driver.find_elements(By.CSS_SELECTOR,'#section_review > div.pagination_pagination__JW7zT')[0]
                pages = page_bar.find_elements(By.CSS_SELECTOR,'a')
                page_now = page_bar.find_elements(By.CSS_SELECTOR,'a.pagination_now__Ey_sR')[0].text.replace('현재 페이지\n','')

                while do==0:
                    if do==1:
                        break
                    # 페이지 바 순회
                    page_bar = driver.find_elements(By.CSS_SELECTOR,'#section_review > div.pagination_pagination__JW7zT')[0]
                    pages = page_bar.find_elements(By.CSS_SELECTOR,'a')
                    page_now = page_bar.find_elements(By.CSS_SELECTOR,'a.pagination_now__Ey_sR')[0].text.replace('현재 페이지\n','')
                    page_end = pages[-1].text


                    for page in pages:
                        page_num = page.text
                        # print(page_num,"현재 페이지:",page_now)
                        if page_num in ['이전']:
                            pass
                            time.sleep(0.5)
                        elif page_num== '현재 페이지\n'+page_now:     # 현재 페이지인 경우
                            for i in range(len(driver.find_elements(By.CLASS_NAME,'reviewItems_text__XrSSf'))):
                                content = driver.find_elements(By.CLASS_NAME,'reviewItems_text__XrSSf')[i].text.replace('\n',' ')
                                score = driver.find_elements(By.CSS_SELECTOR,'span.reviewItems_average__0kLWX')[i].text[-1]
                                result.append(product_name + "||$" + k.text + "||$" + content + "||$" + score+"||$"+product_price)
                            if page_end == page_num:
                                do = 1
                                time.sleep(0.5)
                                break
                        elif page_num =='다음':
                            driver.find_elements(By.CSS_SELECTOR,'#section_review > div.pagination_pagination__JW7zT > a.pagination_next__3_3ip')[0].click()
                            page_now = str(int(page_now)+1)
                            time.sleep(0.5)
                        elif int(page_num)>int(page_now):    # 현재페이지+1 로 이동 후 크롤링
                            page_now = page_num
                            if int(page_num)%10 == 0:
                                driver.find_elements(By.CSS_SELECTOR,'#section_review > div.pagination_pagination__JW7zT > a:nth-child(10)')[0].click()
                                time.sleep(0.5)                        
                            else:
                                driver.find_elements(By.CSS_SELECTOR,f'#section_review > div.pagination_pagination__JW7zT > a:nth-child({str(int(page_num)%10)})')[0].click()
                                time.sleep(0.5)
                            for i in range(len(driver.find_elements(By.CLASS_NAME,'reviewItems_text__XrSSf'))):
                                content = driver.find_elements(By.CLASS_NAME,'reviewItems_text__XrSSf')[i].text.replace('\n',' ')
                                score = driver.find_elements(By.CSS_SELECTOR,'span.reviewItems_average__0kLWX')[i].text[-1]
                                result.append(product_name + "||$" + k.text + "||$" + content + "||$" + score+"||$"+product_price)
                            if page_end == page_num:
                                do = 1
                                time.sleep(0.5)
                                break

            except:
                # 페이지 바 없는 경우
                for i in range(len(driver.find_elements(By.CLASS_NAME,'reviewItems_text__XrSSf'))):
                    content = driver.find_elements(By.CLASS_NAME,'reviewItems_text__XrSSf')[i].text.replace('\n',' ')
                    score = driver.find_elements(By.CSS_SELECTOR,'span.reviewItems_average__0kLWX')[i].text[-1]
                    result.append(product_name + "||$" + k.text + "||$" + content + "||$" + score+"||$"+product_price)
#         if k == keyword_list[-1]:
#             driver.close()
    
    result2 = []
    for r in result:
        result2.append(r.split("||$"))
    result2

    data1 = []
    data2 = []
    data3 = []
    data4 = []
    data5 = []
    for r in result2:
        data1.append(r[0])
        data2.append(r[1])
        data3.append(r[2])
        data4.append(r[3])
        data5.append(r[4])
    data1 = pd.Series(data1)
    data2 = pd.Series(data2)
    data3 = pd.Series(data3)
    data4 = pd.Series(data4)
    data5 = pd.Series(data5)
    
    data = pd.concat([data1, data2, data3,data4,data5],axis=1)
    data.columns = ['제품명','카테고리','내용','별점','가격']
    data.to_csv("C:/Crawling/CSV/"+csv_name+".csv",index=False, encoding='UTF-8-sig')
    
    # print("종료")
    # 확인용
    return result
