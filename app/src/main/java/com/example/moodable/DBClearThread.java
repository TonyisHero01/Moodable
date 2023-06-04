package com.example.moodable;

public class DBClearThread extends Thread{
    EmotionItemDao emotionItemDao;
    public DBClearThread(EmotionItemDao emotionItemDao){
        this.emotionItemDao = emotionItemDao;
    }
    @Override
    public void run() {
        emotionItemDao.deleteAll();
    }
}
