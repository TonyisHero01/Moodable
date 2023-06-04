package com.example.moodable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Calendar;
import java.util.GregorianCalendar;

@Entity(tableName = "EmotionItems")
public class EmotionItem {
    @PrimaryKey
    public GregorianCalendar date;

    @ColumnInfo
    public int day;

    @ColumnInfo
    public int month;

    @ColumnInfo
    public int year;
    @ColumnInfo
    public Emotion emotion;

    public EmotionItem(GregorianCalendar date, Emotion emotion) {
        this.date = date;
        this.day = date.get(Calendar.DAY_OF_MONTH);
        this.month = date.get(Calendar.MONTH);
        this.year = date.get(Calendar.YEAR);
        this.emotion = emotion;
    }
}
