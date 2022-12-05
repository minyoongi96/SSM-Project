from bertDataset import BERTDataset
import torch
import pickle
import numpy as np
import os

from kobert.pytorch_kobert import get_pytorch_kobert_model
from kobert_tokenizer import KoBERTTokenizer

def predict(MODEL, tok, vocab, predict_sentence):
    max_len = 64
    batch_size = 64
    device = torch.device("cpu")
    
    model = MODEL
    data = [predict_sentence, '0']
    dataset_another = [data]

    another_test = BERTDataset(dataset_another, 0, 1, tok, vocab, max_len, True, False)
    test_dataloader = torch.utils.data.DataLoader(another_test, batch_size=batch_size, num_workers=5)
    
    model.eval()

    for batch_id, (token_ids, valid_length, segment_ids, label) in enumerate(test_dataloader):
        token_ids = token_ids.long().to(device)
        segment_ids = segment_ids.long().to(device)

        valid_length= valid_length
        label = label.long().to(device)

        out = model(token_ids, valid_length, segment_ids)


        test_eval=[]

        for i in out:
            logits = i.detach().cpu().numpy()
    return np.argmax(logits)     # 0: 부정, 1: 긍정