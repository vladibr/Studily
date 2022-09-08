package com.example.Studili.ui.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.Studili.R;
import com.example.Studili.classes.DAOuser;

import java.util.Timer;
import java.util.TimerTask;

import pl.droidsonroids.gif.GifImageView;

public class HomeFragment extends Fragment implements View.OnClickListener {

    View view;

    ProgressBar progressBar;
    GifImageView gif;
    TextView timer_textView,rank;
    Button timer_btn;

    Handler timerHandler;
    Timer timer = new Timer();
    TimerTask timerTask;

    boolean timerStarted = false;
    Integer time = 0;

    DAOuser user = new DAOuser();


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_home,container,false);

        initViews();

        initVarbs();

        initButtons();

        return view;
    }

    private void initButtons() {
        timer_btn.setOnClickListener(this);
    }

    private void initVarbs() {
        user.showRankAndGif(rank,gif,progressBar);
        timerHandler = new Handler();
    }

    private void initViews() {
        timer_btn = view.findViewById(R.id.timer_btn);
        timer_textView = view.findViewById(R.id.timer_textView);
        rank = view.findViewById(R.id.rank);
        gif = view.findViewById(R.id.rank_gif);
        progressBar = view.findViewById(R.id.progress_bar);
    }

    public void startTimer() {
        timerTask = new TimerTask() {
            public void run() {
                timerHandler.post(new Runnable() {
                    public void run() {
                        time++;
                        timer_textView.setText(getTimerText());
                    }
                });
            }};
    }

    private String getTimerText()
    {
        int rounded = Math.round(time);

        int seconds = ((rounded % 86400) % 3600) % 60;
        int minutes = ((rounded % 86400) % 3600) / 60;
        int hours = ((rounded % 86400) / 3600);

        return formatTime(seconds, minutes, hours);
    }

    public void startStopTapped()
    {
        if(!timerStarted) {
            timerStarted = true;
            timer_btn.setText("Stop");
            startTimer();
            timer.schedule(timerTask, 10, 1000);

        }
        else{
            timerStarted = false;
            timer_btn.setText("Start");
            timerTask.cancel();
            ResetTapped();
        }
    }

    public void ResetTapped()
    {
        AlertDialog.Builder resetAlert = new AlertDialog.Builder(getActivity());
        resetAlert.setTitle("Study Task");
        resetAlert.setMessage("Have you finished your tasks?");
        resetAlert.setPositiveButton("Yes", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                if(timerTask != null)
                {

                    timerStarted = false;
                    Integer exp = getExperienceFromStudy(getHalfHours());
                    timer_textView.setText(formatTime(0,0,0));
                    user.updateUserStats(exp,time);
                    time = 0;
                }
            }
        });

        resetAlert.setNeutralButton("No", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                //do nothing
            }
        });

        resetAlert.show();
    }

    public int getHalfHours(){

        int half_hours = time / 1800;
        return half_hours;
    }

    public int getExperienceFromStudy(int half_hours){
        if (half_hours <= 0)
            return 0;
        else
            return half_hours * 10;
    }

    private String formatTime(int seconds, int minutes, int hours)
    {
        return String.format("%02d",hours) + " : " + String.format("%02d",minutes) + " : " + String.format("%02d",seconds);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.timer_btn:
                startStopTapped();
                break;
        }
    }
}