package com.example.cronometro;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Chronometer chronometer;
    private Button startButton, stopButton, lapButton, resetButton;
    private ListView lapList;

    private long timeWhenStopped = 0;
    private boolean running = false;
    private List<String> laps = new ArrayList<>();
    private ArrayAdapter<String> lapAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chronometer = findViewById(R.id.chronometer);
        startButton = findViewById(R.id.start_button);
        stopButton = findViewById(R.id.stop_button);
        lapButton = findViewById(R.id.lap_button);
        resetButton = findViewById(R.id.reset_button);
        lapList = findViewById(R.id.lap_list);

        lapAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, laps);
        lapList.setAdapter(lapAdapter);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!running) {
                    chronometer.setBase(SystemClock.elapsedRealtime() - timeWhenStopped);
                    chronometer.start();
                    running = true;
                }
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (running) {
                    chronometer.stop();
                    timeWhenStopped = SystemClock.elapsedRealtime() - chronometer.getBase();
                    running = false;
                }
            }
        });

        lapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (running) {
                    String lapTime = chronometer.getText().toString();
                    laps.add(0, lapTime);
                    lapAdapter.notifyDataSetChanged();
                }
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chronometer.stop();
                chronometer.setBase(SystemClock.elapsedRealtime());
                timeWhenStopped = 0;
                running = false;
                laps.clear();
                lapAdapter.notifyDataSetChanged();
            }
        });
    }
}
