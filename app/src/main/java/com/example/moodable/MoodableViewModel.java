package com.example.moodable;

import static androidx.lifecycle.SavedStateHandleSupport.createSavedStateHandle;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.viewmodel.ViewModelInitializer;
import static androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY;

import java.util.List;

public class MoodableViewModel extends ViewModel {

    private MutableLiveData<List<EmotionItem>> emotionItems = new MutableLiveData<>();
    private MutableLiveData<List<EmotionMonthYearTuple>> emotionMonthYearTuples = new MutableLiveData<>();

    EmotionItemDao emotionItemDao;
    public MoodableViewModel(Application application) {
        MoodableDataBase db = MoodableDataBase.getDatabase(application);
        emotionItemDao = db.emotionItemDao();
    }
    static final ViewModelInitializer<MoodableViewModel> initializer = new ViewModelInitializer<>(
            MoodableViewModel.class,
            creationExtras -> {
                Application app = (Application) creationExtras.get(APPLICATION_KEY);
                assert app != null;

                return new MoodableViewModel(app);
            }
    );
    public MutableLiveData<List<EmotionItem>> getEmotionItems() {
        return emotionItems;
    }

    public MutableLiveData<List<EmotionMonthYearTuple>> getEmotionMonthYearTuples() {
        return emotionMonthYearTuples;
    }

    public void readAll(){
        DBReadThread dbReadThread = new DBReadThread(emotionItemDao, emotionItems);
        dbReadThread.start();
    }

    public void deleteAll() {
        DBClearThread dbClearThread = new DBClearThread(emotionItemDao);
        dbClearThread.start();
    }
    public void write(EmotionItem emotionItem) {
        DBWriteThread dbWriteThread = new DBWriteThread(emotionItemDao, emotionItem);
        dbWriteThread.start();
    }
    public void readAverage() {
        DBReadAverageThread dbReadAverageThread = new DBReadAverageThread(emotionItemDao, emotionMonthYearTuples);
        dbReadAverageThread.start();
    }
}
