package com.example.moodable;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;


import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;
public class MonthPage extends AppCompatActivity {
    CalendarView calendarView;

    Button backButton;
    Button historyButton;
    ImageButton emoji1;
    ImageButton emoji2;
    ImageButton emoji3;
    ImageButton emoji4;
    static ProgressBar progressBar;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_month_page);

        MoodableViewModel viewModel = new ViewModelProvider(
                this,
                ViewModelProvider.Factory.from(MoodableViewModel.initializer)
        ).get(MoodableViewModel.class);

        //vybrany cas
        GregorianCalendar selectedDate = new GregorianCalendar();
        //nastavuje hodinu minutu sekundu milisekundu na nulu, aby mohl porovnat datumy na radce 91,
        //jinak vybrany cas je porad driv nez soucasny cas
        selectedDate.set(Calendar.MONTH, selectedDate.get(Calendar.MONTH)+1);
        setTimeToZero(selectedDate);

        //kalendar view a udalosti
        calendarView = findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                month = month+1;
                Toast.makeText(getApplicationContext(), year + "-" + month + "-" + day, Toast.LENGTH_LONG).show();
                selectedDate.set(year, month, day);
            }
        });

        //ruzna emoji a udalosti: 69-111
        emoji1 = findViewById(R.id.emoji1);
        emoji2 = findViewById(R.id.emoji2);
        emoji3 = findViewById(R.id.emoji3);
        emoji4 = findViewById(R.id.emoji4);

        ImageButton[] emojis = new ImageButton[]{emoji1, emoji2, emoji3, emoji4};
        String[] textsForEmojis = new String[]{"happy", "in love", "angry", "sad"};
        Emotion[] emotions = new Emotion[]{Emotion.HAPPY, Emotion.IN_LOVE, Emotion.ANGRY, Emotion.SAD};

        for (int i=0; i<emojis.length; i++) {
            int finalI = i;
            emojis[i].setOnClickListener(new View.OnClickListener() {
                @SuppressLint("DefaultLocale")
                @Override
                public void onClick(View v) {
                    String verb;
                    String date;
                    Calendar currentDate = new GregorianCalendar();
                    currentDate.set(Calendar.MONTH, currentDate.get(Calendar.MONTH)+1);
                    setTimeToZero(currentDate);
                    if (currentDate.compareTo(selectedDate) == -1){
                        verb = "cannot choose";
                        date = "future date";
                        String content = String.format("You %s %s",
                                verb,
                                date);
                        Toast.makeText(getApplicationContext(), content, Toast.LENGTH_LONG).show();
                        return;
                    } else if (currentDate.compareTo(selectedDate) == 1){
                        verb = "were";
                        date = String.format("on %d-%d-%d",
                                selectedDate.get(Calendar.DAY_OF_MONTH),
                                selectedDate.get(Calendar.MONTH),
                                selectedDate.get(Calendar.YEAR));
                    } else {
                        verb = "are";
                        date = "today";
                    }
                    String content = String.format("You %s %s %s",
                            verb,
                            textsForEmojis[finalI],
                            date);
                    Toast.makeText(getApplicationContext(), content, Toast.LENGTH_LONG).show();
                    EmotionItem emotionItem = new EmotionItem(selectedDate, emotions[finalI]);
                    viewModel.write(emotionItem);
                }

            });
        }

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        historyButton = findViewById(R.id.historyButton);

        final boolean[] pressedHistoryButton = {false};
        historyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // progress bar je viditelny, kdyz uzivatel klikne na historyButton
                pressedHistoryButton[0] = true;
                progressBar.setVisibility(View.VISIBLE);
                Intent intent = new Intent(MonthPage.this, EmoHistory.class);
                startActivity(intent);
            }
        });

        backButton = findViewById(R.id.backToMainButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void setTimeToZero(Calendar calendar) {
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }
}