from kobert_predict import predict
from bertClassifier import BERTClassifier

import kss
import pickle
import torch
import numpy as np
import os
import pandas as pd

# model, tokenizer, vacab loading
def loading(PATH = '/config/workspace/2nd_project_for_linux/SSM/KoBERT_predict/'):
    device = torch.device("cpu")

    # 학습된 kobert 모델 로드
    load_model = torch.load(PATH + 'emotion_kobert_model.pt', map_location=device)
    checkpoint = torch.load(PATH + 'emotion_kobert_model_all.tar', map_location=device)
    load_model.load_state_dict(checkpoint['model'])

    # tokenizer, vocab 로딩
    with open(PATH + 'tok.pkl', 'rb') as f:
        tok = pickle.load(f)

    with open(PATH + './vocab.pkl', 'rb') as f:
        vocab = pickle.load(f)

    return (load_model, tok, vocab)

def scoring(review):
    MODEL, tok, vocab = loading()

    scores = []
    review_sentences = kss.split_sentences(review)
    
    for idx, r in enumerate(review_sentences):
        score = predict(MODEL, tok, vocab, r)
        print(r)
        print(idx, "번째 점수 :", score)
        scores.append(score)

    print("최종 점수 :", np.mean(scores))
    return np.mean(scores)


if __name__ == "__main__":
    print(os.getcwd())
    path = './Data/'
    file_list = os.listdir(path)
    print(file_list[0])
    
    review_data = pd.read_csv(f'./Data/{file_list[0]}', index_col=False)
    review_data = review_data[:3]
    review_data['감성지수'] = review_data['내용'].apply(lambda x : scoring(str(x)))
    review_data.to_csv('./Data/test.csv', encoding='utf-8-sig')
    print('작업 끝')
    
    # example4 = "생각보다 화사한 노란색은 아니고 털은 부드러워요 퀼팅이 되거나 했음 더 좋았을 것 같아요 이불 가운데가 뜨는 게 저는 좀 별로네요"
    # scoring(example4, MODEL, tok, vocab)