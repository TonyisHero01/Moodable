package com.example.moodable;

import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class DBReadAverageThread extends Thread{
    EmotionItemDao emotionItemDao;
    MutableLiveData<List<EmotionMonthYearTuple>> emotionMonthYearTuples;
    public DBReadAverageThread(EmotionItemDao emotionItemDao, MutableLiveData<List<EmotionMonthYearTuple>> emotionMonthYearTuples){
        this.emotionItemDao = emotionItemDao;
        this.emotionMonthYearTuples = emotionMonthYearTuples;
    }
    @Override
    public void run() {
        emotionMonthYearTuples.postValue(emotionItemDao.findMostCommonEmotionPerMonth());
    }
}
