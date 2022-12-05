import gluonnlp as nlp
from kobert.pytorch_kobert import get_pytorch_kobert_model
from kobert_tokenizer import KoBERTTokenizer
from torch.utils.data import Dataset
import numpy as np

class BERTDataset(Dataset):
    def __init__(self, dataset, sent_idx, label_idx, bert_tokenizer,vocab, max_len,
                pad, pair):
    
        transform = nlp.data.BERTSentenceTransform(
            bert_tokenizer, max_seq_length=max_len,vocab=vocab, pad=pad, pair=pair)
        
        self.sentences = [transform([i[sent_idx]]) for i in dataset]
        self.labels = [np.int32(i[label_idx]) for i in dataset]

    def __getitem__(self, i):
        return (self.sentences[i] + (self.labels[i], ))
        
    def __len__(self):
        return (len(self.labels))