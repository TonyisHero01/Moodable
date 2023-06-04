package com.example.moodable;

public class DBWriteThread extends Thread{
    EmotionItemDao emotionItemDao;
    EmotionItem emotionItem;
    public DBWriteThread(EmotionItemDao emotionItemDao, EmotionItem emotionItem){
        this.emotionItemDao = emotionItemDao;
        this.emotionItem = emotionItem;
    }
    @Override
    public void run() {
        emotionItemDao.addItem(emotionItem);
    }
}
