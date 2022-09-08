package com.example.Studili.classes;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import androidx.annotation.NonNull;

import com.example.Studili.R;
import com.example.Studili.adapters.CardAdapter;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;


import pl.droidsonroids.gif.GifImageView;

public class DAOuser {
    private DatabaseReference myRef;
    private Integer[] gifs = {R.drawable.rank_1, R.drawable.rank_2, R.drawable.rank_3,
            R.drawable.rank_4, R.drawable.rank_5};
    private List<Integer> myGifs = new ArrayList<>();
    int currentIndex = 0;
    final int maxRank = 5;


    public DAOuser() {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        myRef = db.getReference("users");
    }

    public Task<Void> add(User user) {
        return myRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user);
    }

    public void addGif(Integer gif) {

        String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        myRef.child(currentUser).child("gifs").child(gif.toString()).setValue(true);
        setCurrentGif(gif);
    }

    public void showNavInfo(TextView userName, TextView userEmail) {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();

                String myName = snapshot.child(currentUser).child("name").getValue().toString();

                String myEmail = snapshot.child(currentUser).child("email").getValue().toString();

                userName.setText(myName);
                userEmail.setText(myEmail);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void showRankAndGif(TextView rank, GifImageView gif, ProgressBar progressBar) {

        myRef.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();

                String myRank = snapshot.child(currentUser).child("currentRank").getValue().toString();

                Integer currentGif = snapshot.child(currentUser).child("currentGif").getValue(Integer.class);

                Integer currentExp = snapshot.child(currentUser).child("stats").child("exp").getValue(Integer.class);

                rank.setText("Rank " + myRank);

                gif.setImageResource(currentGif);

                progressBar.setProgress(currentExp,true);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void updateUserStats(Integer exp, Integer time) {
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();

                Integer hours = (time/3600);

                Integer longerTimes = snapshot.child(currentUser).child("stats").child("longerTimeStudied").getValue(Integer.class);
                if (hours >= 3) {
                    myRef.child(currentUser).child("stats").child("longerTimeStudied").setValue(longerTimes + 1);
                }

                Integer longestHour = snapshot.child(currentUser).child("stats").child("longestHoursStudied").getValue(Integer.class);

                if (longestHour < hours) {
                    myRef.child(currentUser).child("stats").child("longestHoursStudied").setValue(hours);
                }

                Integer currentTotalTime = snapshot.child(currentUser).child("stats").child("time").getValue(Integer.class);

                Integer oldExp = snapshot.child(currentUser).child("stats").child("exp").getValue(Integer.class);

                Integer myRank = snapshot.child(currentUser).child("currentRank").getValue(Integer.class);

                Integer task = snapshot.child(currentUser).child("stats").child("tasksCompleted").getValue(Integer.class);



                myRef.child(currentUser).child("stats").child("tasksCompleted").setValue(task + 1);
                myRef.child(currentUser).child("stats").child("time").setValue(currentTotalTime + time);

                if (oldExp + exp >= 100 && myRank < maxRank) {
                    myRef.child(currentUser).child("currentRank").setValue(myRank + 1);

                    myRef.child(currentUser).child("stats").child("exp").setValue(0);
                    addGif(gifs[myRank]);



                } else {
                    myRef.child(currentUser).child("stats").child("exp").setValue(oldExp + exp);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void updateDaysLoggedIn() {
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();

                String currentDate = snapshot.child(currentUser).child("registerDate").getValue(String.class);

                LocalDate date = LocalDate.parse(currentDate);

                int days = ((int) ChronoUnit.DAYS.between(date, LocalDate.now())) + 1;

                myRef.child(currentUser).child("stats").child("totalDays").setValue(days);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void showAndSelectGif(ImageButton prev, ImageButton next, ImageSwitcher imageSwitcher, Context context) {

        String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();

        myRef.child(currentUser).child("gifs").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    myGifs.add(Integer.parseInt(d.getKey())) ;
                }
                Integer count = myGifs.size();
                currentIndex = 0;

                imageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
                    @Override
                    public View makeView() {
                        GifImageView gifImageView = new GifImageView(context.getApplicationContext());
                        gifImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        gifImageView.setLayoutParams(new ImageSwitcher.LayoutParams(
                                LinearLayout.LayoutParams.FILL_PARENT,
                                LinearLayout.LayoutParams.FILL_PARENT));
                        return gifImageView;
                    }
                });

                imageSwitcher.setImageResource(myGifs.get(0));


                prev.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        imageSwitcher.setInAnimation(context, R.anim.from_left);
                        imageSwitcher.setOutAnimation(context, R.anim.to_right);
                        --currentIndex;
                        if (currentIndex < 0) {
                            currentIndex = myGifs.size() - 1;
                        }
                        imageSwitcher.setImageResource(myGifs.get(currentIndex));
                    }
                });

                next.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        imageSwitcher.setInAnimation(context, R.anim.from_right);
                        imageSwitcher.setOutAnimation(context, R.anim.to_left);
                        currentIndex++;
                        if (currentIndex == count) {
                            currentIndex = 0;
                        }
                        imageSwitcher.setImageResource(myGifs.get(currentIndex));
                    }
                });

                imageSwitcher.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder confirmDialog = new AlertDialog.Builder(context);
                        confirmDialog.setTitle("Confirm Selection");
                        confirmDialog.setMessage("You sure you want to change?");
                        confirmDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i)
                            {
                                setCurrentGif(myGifs.get(currentIndex));
                            }
                        });

                        confirmDialog.setNeutralButton("No", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i)
                            {
                                //do nothing
                            }
                        });
                        confirmDialog.show();
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });

    }

    public void showUserStats( ListView card_list,Context context){

        ArrayList<Stat_Card> arr = new ArrayList<>();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();

                Stats stats = snapshot.child(currentUser).child("stats").getValue(Stats.class);

                String myRank =  snapshot.child(currentUser).child("currentRank").getValue().toString();

                arr.add(new Stat_Card(R.drawable.icon_1,"Total Time Studied",toHours(stats.getTime())));
                arr.add(new Stat_Card(R.drawable.icon_2,"Total Days Logged","Account Age:" + stats.getTotalDays().toString()));
                arr.add(new Stat_Card(R.drawable.icon_3,"Finished Tasks","Tasks Completed:" + stats.getTasksCompleted().toString()));
                arr.add(new Stat_Card(R.drawable.icon_6, "Studyholic ","Times studied more then 3 hours:" + stats.getLongerTimeStudied().toString()));
                arr.add(new Stat_Card(R.drawable.icon_7,"Student Marathon","Longest time studied in hours:" + stats.getLongestHoursStudied().toString()));
                arr.add(new Stat_Card(R.drawable.icon_4, "Student Rank","Current rank:" + myRank));
                arr.add(new Stat_Card(R.drawable.icon_5, "Machine","Total Experience:" + stats.getExp().toString()));

                CardAdapter adapter = new CardAdapter(context,0, arr);
                card_list.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
    }

    public String toHours(Integer time){

        Integer hours = (time/3600);
        return  hours.toString() + " Hours:";
    }

    public void setCurrentGif(Integer gif){

        String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                myRef.child(currentUser).child("currentGif").setValue(gif);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}

