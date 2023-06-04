package com.example.moodable;

import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class DBReadThread extends Thread{
    EmotionItemDao emotionItemDao;

    private MutableLiveData<List<EmotionItem>> emotionItems;
    public DBReadThread(EmotionItemDao emotionItemDao, MutableLiveData<List<EmotionItem>> emotionItems){
        this.emotionItemDao = emotionItemDao;
        this.emotionItems = emotionItems;
    }
    @Override
    public void run() {
        emotionItems.postValue(emotionItemDao.getAll());
    }
}
