package com.example.moodable;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView titleTextView;
    Button enterButton;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        titleTextView = findViewById(R.id.titleTextView);
        enterButton = findViewById(R.id.enterButton);

        //po kliknuti enter presmerujeme do stranky mesice/kalendare
        enterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.println(Log.ASSERT, "enter", "clicked");
                Intent intent = new Intent(MainActivity.this, MonthPage.class);
                startActivity(intent);
            }
        });

    }
}