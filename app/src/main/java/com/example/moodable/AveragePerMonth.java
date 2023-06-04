package com.example.moodable;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AveragePerMonth extends AppCompatActivity {
    Button backButton;
    static Map<Emotion, Integer> emotionsToDrawables;
    static {
        emotionsToDrawables = new HashMap<>();
        emotionsToDrawables.put(Emotion.ANGRY, R.drawable.angry);
        emotionsToDrawables.put(Emotion.HAPPY, R.drawable.happy);
        emotionsToDrawables.put(Emotion.IN_LOVE, R.drawable.in_love);
        emotionsToDrawables.put(Emotion.SAD, R.drawable.sad);
    }
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.average_per_month);

        //nacteni dat emoci
        MoodableViewModel viewModel = new ViewModelProvider(
                this,
                ViewModelProvider.Factory.from(MoodableViewModel.initializer)
        ).get(MoodableViewModel.class);
        Context context = this;

        viewModel.readAverage();
        RecyclerView recyclerView = findViewById(R.id.recyclerViewAverage);
        final Observer<List<EmotionMonthYearTuple>> emotionMonthYearTuplesObserver = new Observer<List<EmotionMonthYearTuple>>() {
            @Override
            public void onChanged(@Nullable final List<EmotionMonthYearTuple> emotionMonthYearTuples) {
                // Update the UI
                List<Data> dataSet = emotionItemsToData(emotionMonthYearTuples);

                //adapter pro recycler view
                CustomAdapter customAdapter = new CustomAdapter(dataSet);

                recyclerView.setAdapter(customAdapter);
            }
        };
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        viewModel.getEmotionMonthYearTuples().observe(this, emotionMonthYearTuplesObserver);

        backButton = findViewById(R.id.backButtonAverage);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private List<Data> emotionItemsToData(List<EmotionMonthYearTuple> emotionMonthYearTuples) {
        List<Data> data = new ArrayList<>();
        for (EmotionMonthYearTuple emotionMonthYearTuple : emotionMonthYearTuples) {
            Data d = new Data(-1, emotionMonthYearTuple.month, emotionMonthYearTuple.year, emotionsToDrawables.get(emotionMonthYearTuple.emotion));
            data.add(d);
        }
        return data;
    }
}