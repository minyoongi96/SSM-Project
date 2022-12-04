import pandas as pd
import numpy as np
import os
import json
from collections import OrderedDict

def DFtest(PATH='C:/2nd_project/SSM/Crawling/CSV/'):
    PATH = 'C:/2nd_project/SSM/Crawling/CSV/'
    file_list = [file for file in os.listdir(PATH) if file[-4:] == '.csv']
    
    for file in file_list:
        dic = OrderedDict()
        data = pd.read_csv(PATH + file, encoding='utf-8', index_col=False)
        
        dic['제품명'] = data['제품명']
        
        for col in list(data.columns):
            dic[col] = data[col].tolist()
        
        with open(f'{PATH + file[:-4]}.json', 'w', encoding='utf-8') as make_json:
            json.dump(dic, make_json, ensure_ascii=False, indent='\t')
        print(f'{file} > json 변환 끝')




if __name__ == "__main__":
    # DFtest()
    # PATH = 'C:/2nd_project/SSM/Crawling/CSV/'
    # file_list = [file for file in os.listdir(PATH) if file[-4:] == '.csv']
    # data = pd.read_csv(PATH + file_list[0], encoding='utf-8', index_col=False)
    
    # dic = OrderedDict()
    
    # dic['제품명'] = data['제품명'].unique().tolist()
    
    
    # print(dic)
    