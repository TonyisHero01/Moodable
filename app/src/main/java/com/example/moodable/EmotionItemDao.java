package com.example.moodable;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

@Dao
public interface EmotionItemDao {
    @Query("SELECT * FROM EmotionItems ORDER BY date DESC")
    List<EmotionItem> getAll();

    @Update
    void update(EmotionItem emotionItem);

    @Insert
    void insert(EmotionItem emotionItem);

    @Query("DELETE FROM EmotionItems")
    void deleteAll();

    @Query("SELECT COUNT(*) FROM EmotionItems WHERE date=:date")
    int countRowsByDate(GregorianCalendar date);

    @Query("SELECT emotion FROM (SELECT emotion, count(*) AS count FROM EmotionItems WHERE month=:month AND year=:year GROUP BY emotion ORDER BY count DESC LIMIT 1)")
    Emotion findMostCommonEmotionInMonth(int month, int year);

    @Query("SELECT DISTINCT month, year FROM EmotionItems")
    List<MonthYearTuple> getAllDistinctMonths();
    default void addItem(EmotionItem emotionItem) {
        if (countRowsByDate(emotionItem.date) == 0) {
            insert(emotionItem);
        } else {
            update(emotionItem);
        }
    }

    default List<EmotionMonthYearTuple> findMostCommonEmotionPerMonth() {
        List<MonthYearTuple> allDistinctMonths = getAllDistinctMonths();
        List<EmotionMonthYearTuple> emotionMonthYearTuples = new ArrayList<>();

        for (MonthYearTuple monthYearTuple : allDistinctMonths) {
            Emotion emotion = findMostCommonEmotionInMonth(monthYearTuple.month, monthYearTuple.year);
            EmotionMonthYearTuple emotionMonthYearTuple = new EmotionMonthYearTuple(emotion, monthYearTuple.month, monthYearTuple.year);
            emotionMonthYearTuples.add(emotionMonthYearTuple);
        }
        return emotionMonthYearTuples;
    }
}
