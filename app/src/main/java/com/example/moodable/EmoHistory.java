package com.example.moodable;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

public class EmoHistory extends AppCompatActivity {
    Button backToMonthButton;
    Button clearButton;
    Button averageButton;
    static Map<Emotion, Integer> emotionsToDrawables;
    static {
        emotionsToDrawables = new HashMap<>();
        emotionsToDrawables.put(Emotion.ANGRY, R.drawable.angry);
        emotionsToDrawables.put(Emotion.HAPPY, R.drawable.happy);
        emotionsToDrawables.put(Emotion.IN_LOVE, R.drawable.in_love);
        emotionsToDrawables.put(Emotion.SAD, R.drawable.sad);
    }
    @SuppressLint({"MissingInflatedId", "ResourceType"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.emo_history);

        //nacteni dat emoci
        //MoodableViewModel viewModel = new MoodableViewModel(this.getApplication());
        MoodableViewModel viewModel = new ViewModelProvider(
                this,
                ViewModelProvider.Factory.from(MoodableViewModel.initializer)
        ).get(MoodableViewModel.class);
        Context context = this;
        viewModel.readAll();
        final Observer<List<EmotionItem>> emotionItemsObserver = new Observer<List<EmotionItem>>() {
            @Override
            public void onChanged(@Nullable final List<EmotionItem> emotionItems) {
                // Update the UI
                List<Data> dataSet = emotionItemsToData(emotionItems);

                //adapter pro recycler view
                CustomAdapter customAdapter = new CustomAdapter(dataSet);

                RecyclerView recyclerView = findViewById(R.id.recyclerView);
                recyclerView.setAdapter(customAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            }
        };
        viewModel.getEmotionItems().observe(this, emotionItemsObserver);
        //po kliknuti se presmeruje do stranky vetsinou emoci kazdeho mesice
        averageButton = findViewById(R.id.averageButton);
        averageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EmoHistory.this, AveragePerMonth.class);
                startActivity(intent);
            }
        });

        //po kliknuti clear smaze vsechny dat v databazi
        clearButton = findViewById(R.id.clearButton);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.deleteAll();
                finish();
                startActivity(getIntent());
            }
        });

        //po kliknuti back se presmeruje do stranky mesice/kalendare
        backToMonthButton = findViewById(R.id.backToMonthButton);

        backToMonthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MonthPage.progressBar.setVisibility(View.INVISIBLE);
                finish();
            }
        });
    }

    private List<Data> emotionItemsToData(List<EmotionItem> emotionItems) {
        List<Data> data = new ArrayList<>();
        for (EmotionItem emotionItem : emotionItems) {
            Data d = new Data(emotionItem.day, emotionItem.month, emotionItem.year, emotionsToDrawables.get(emotionItem.emotion));
            data.add(d);
        }
        return data;
    }
}