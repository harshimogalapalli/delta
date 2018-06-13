package com.example.harshit.myapplication;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Filter_Matches extends AppCompatActivity {
    String team, t1, t2;
    LinearLayout linearLayout;
    int i;
    DataBaseHelper dataBaseHelper = new DataBaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter__matches);

        linearLayout = findViewById(R.id.linearLayout);
        Intent filter = getIntent();
        team = filter.getStringExtra("item");

        for (i = 0; i < dataBaseHelper.no_of_matches(); i++) {
            if (team.matches(dataBaseHelper.getTeam1(i)) || team.matches(dataBaseHelper.getTeam2(i))) {
                display(i);
            }
        }
    }

    public void display(int i) {
            t1 = dataBaseHelper.getTeam1(i);
            t2 = dataBaseHelper.getTeam2(i);
            LinearLayout main = new LinearLayout(this);
            main.setOrientation(LinearLayout.VERTICAL);
            LinearLayout ll = new LinearLayout(this);
            ll.setOrientation(LinearLayout.HORIZONTAL);
            TextView team1 = new TextView(this);
            TextView team2 = new TextView(this);
            TextView vs = new TextView(this);
            TextView date = new TextView(this);
            TextView venue = new TextView(this);
            ImageView icon1 = new ImageView(this);
            ImageView icon2 = new ImageView(this);

            team1.setTextSize(40);
            team2.setTextSize(40);
            vs.setTextSize(30);
            date.setTextSize(30);
            venue.setTextSize(30);

            icon1.setImageURI(null);
            icon1.setImageBitmap(dataBaseHelper.getImgId(dataBaseHelper.getTeam1(i), i, getApplicationContext()));
            icon2.setImageURI(null);
            icon2.setImageBitmap(dataBaseHelper.getImgId(dataBaseHelper.getTeam2(i), i, getApplicationContext()));

            team1.setText(dataBaseHelper.getTeam1(i));
            vs.setText(" vs ");
            team2.setText(dataBaseHelper.getTeam2(i));
            venue.setText(dataBaseHelper.getVenue(i));
            date.setText(dataBaseHelper.getDate(i));

            main.setGravity(Gravity.CENTER);
            venue.setTypeface(venue.getTypeface(), Typeface.BOLD);

            ll.addView(icon1, 150, ViewGroup.LayoutParams.MATCH_PARENT);
            ll.addView(team1,ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            ll.addView(vs, ViewGroup.LayoutParams.WRAP_CONTENT);
            ll.addView(team2,ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            ll.addView(icon2,150,ViewGroup.LayoutParams.MATCH_PARENT);
            main.addView(ll, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            main.addView(date, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            main.addView(venue, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            linearLayout.addView(main, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

