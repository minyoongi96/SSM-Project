from selenium import webdriver as wb
from selenium.webdriver.common.by import By
from selenium.webdriver.common.keys import Keys
import time
import pandas as pd

def brand_review(url_list,csv_name):

    result = []
    
    for j in range(len(url_list)):
        
        driver = wb.Chrome('C:/Crawling/chromedriver.exe')
        driver.get(url_list[j])
        # print(f"{j}번째 url")
        driver.implicitly_wait(5)
    
        #제품 이름 저장
        product_name = driver.find_elements(By.CSS_SELECTOR, '#content > div > div._2-I30XS1lA > div._2QCa6wHHPy > fieldset > div._1ziwSSdAv8 > div.CxNYUPvHfB > h3')[0].text
        # 제품 가격 저장
        product_price = driver.find_elements(By.CSS_SELECTOR, '#content > div > div._2-I30XS1lA > div._2QCa6wHHPy > fieldset > div._1ziwSSdAv8 > div.WrkQhIlUY0 > div > strong > span._1LY7DqCnwR')[0].text
        
        # 카테고리 저장 부분
        keyword_list=[]
        for c in range(2,10):
            cate = driver.find_elements(By.CSS_SELECTOR, f'#REVIEW > div > div._180GG7_7yx > div._2PqWvCMC3e > div.smzzzmpK4X > div > div > div > div > button:nth-child({c})')
            if cate != []:
                keyword_list.append(cate[0])  
        # 카테고리 돌면서 데이터 저장
        for k in keyword_list:
            k_text = k.text.replace("#","")
            time.sleep(0.5)
            k.send_keys(Keys.ENTER)  # 카테고리 클릭
            time.sleep(1) # 안하면 에러남
            
            # print(f'{j}째url {k_text} 크롤링 시작')     # 내용 담은 변수 프린트 & 진행도 체크용

            do = 0  # 반복문 탈출을 위한 임시 변수

            ## -------페이지 넘기기-------
            # 페이지 바가 있으면 변수에 페이지 바, 현재페이지를 저장
            try:
                page_bar = driver.find_elements(By.CSS_SELECTOR,'#REVIEW > div > div._180GG7_7yx > div.cv6id6JEkg > div > div')[0]
                pages = page_bar.find_elements(By.CSS_SELECTOR,'a')

                while do==0:
                    if do==1:
                        break
                    # 페이지 바 순회
                    page_bar = driver.find_elements(By.CSS_SELECTOR,'#REVIEW > div > div._180GG7_7yx > div.cv6id6JEkg > div > div')[0]
                    pages = page_bar.find_elements(By.CSS_SELECTOR,'a')
                    page_now = pages[1].text
                    page_end = pages[-2].text
                    

                    for page in pages:
                        page_num = page.text
                        # print("현재 페이지:",page_num)
                        if page_num in ['이전','']:
                            if page_end == page_now and page_now != '1':
                                do = 1
                                time.sleep(0.5)
                                break
                            pass
                        elif page_num == page_now:
                            content_list = driver.find_elements(By.CSS_SELECTOR, 'div.YEtwtZFLDz span._3QDEeS6NLn')
                            score_list = driver.find_elements(By.CSS_SELECTOR, 'div._37TlmH3OaI em._15NU42F3kT')
                            for cs in range(len(content_list)):
                                content = content_list[cs].text.replace('\n',' ')
                                score = score_list[cs].text
                                result.append(product_name + "||$" + k_text + "||$" + content + "||$" + score+"||$"+product_price)
                            if page_end == page_now and pages[-1].text=='':
                                do = 1
                                time.sleep(0.5)
                                break
                        elif page_num =='다음':
                            page.click()
                            time.sleep(0.5)
                        elif int(page_num)>int(page_now):    # 현재페이지+1 로 이동 후 크롤링
                            page_now = str(int(page_now)+1)
                            page.click()
                            time.sleep(0.5)
                            content_list = driver.find_elements(By.CSS_SELECTOR, 'div.YEtwtZFLDz span._3QDEeS6NLn')
                            score_list = driver.find_elements(By.CSS_SELECTOR, 'div._37TlmH3OaI em._15NU42F3kT')
                            for cs in range(len(content_list)):
                                content = content_list[cs].text.replace('\n',' ')
                                score = driver.find_elements(By.CLASS_NAME, '_15NU42F3kT')[cs+4].text
                                result.append(product_name + "||$" + k_text + "||$" + content + "||$" + score+"||$"+product_price)
                            if page_end == page_now and pages[-1].text=='':
                                do = 1
                                time.sleep(0.5)
                                break



            except Exception as e:
                # 페이지 바 없는 경우
                content_list = driver.find_elements(By.CSS_SELECTOR, 'div.YEtwtZFLDz span._3QDEeS6NLn')
                score_list = driver.find_elements(By.CSS_SELECTOR, 'div._37TlmH3OaI em._15NU42F3kT')
                for cs in range(len(content_list)):
                    content = content_list[cs].text.replace('\n',' ')
                    score = driver.find_elements(By.CLASS_NAME, '_15NU42F3kT')[cs+4].text
                    result.append(product_name + "||$" + k_text + "||$" + content + "||$" + score+"||$"+product_price)
                    
    
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
