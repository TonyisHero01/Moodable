package com.example.moodable;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {EmotionItem.class}, version = 1)
@TypeConverters({CalendarConverter.class})
public abstract class MoodableDataBase extends RoomDatabase {
    public abstract EmotionItemDao emotionItemDao();

    private static MoodableDataBase INSTANCE;

    static MoodableDataBase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (MoodableDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    MoodableDataBase.class, "emoData")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
