package com.example.home.workout;


import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class StopwatchFragment extends Fragment implements View.OnClickListener {

    private int seconds = 0;
    private boolean running = false;
    private boolean wasRunning = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, //<- Макет фрагмента назначается в методе onCreateView()
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_stopwatch, container, false);
        runTime(layout);

        Button startButton = (Button) layout.findViewById(R.id.btn_start);
        startButton.setOnClickListener(this);
        Button stopButton = (Button) layout.findViewById(R.id.btn_stop);
        stopButton.setOnClickListener(this);
        Button resetButton = (Button) layout.findViewById(R.id.btn_reset);
        resetButton.setOnClickListener(this);



        return layout;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (wasRunning)
            running = true;
    }

    @Override
    public void onPause() {
        super.onPause();
        wasRunning = running;
        running = false;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
            wasRunning = savedInstanceState.getBoolean("wasRunning");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt("seconds", seconds);
        savedInstanceState.putBoolean("running", running);
        savedInstanceState.putBoolean("wasRunning", wasRunning);
    }

    public void onClickStart(View view) {
        running = true;
    }

    public void onClickStop(View view) {
        running = false;
    }

    public void onClickReset(View view) {
        running = false;
        seconds = 0;
    }

    private void runTime(View view) {
        final TextView timeView = (TextView) view.findViewById(R.id.time_view);
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = seconds / 3600;
                int minutes = (seconds % 3600) / 60;
                int secs = seconds % 60;

                String time = String.format("%d:%02d:%02d", hours, minutes, secs);
                timeView.setText(time);

                if (running)
                    seconds++;

                handler.postDelayed(this, 1000);
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start:
                onClickStart(v);
                break;
            case R.id.btn_stop:
                onClickStop(v);
                break;
            case R.id.btn_reset:
                onClickReset(v);
                break;
        }
    }
}
