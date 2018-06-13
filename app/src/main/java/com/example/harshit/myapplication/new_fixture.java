package com.example.harshit.myapplication;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class new_fixture extends AppCompatActivity {

    Spinner team1Spin,team2Spin,dateSpin,venueSpin;
    String teams[] = {"ARG", "BEL", "AUS", "BRA", "COL", "CRC", "CRO", "DEN", "EGY", "ENG", "FRA", "GER", "MAR", "NGA", "SEN", "TUN", "IRN", "JPN", "KOR", "KSA", "MEX", "PAN", "PER", "URU", "ISL", "POL", "POR", "RUS", "SRB", "ESP", "SWE", "SUI"};
    String date[] = {"14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29"};
    String venue[] = {"Ekaterinburg", "Kaliningrad", "Kazan", "Moscow", "Nizhny Novgorod", "Rostov-on-don", "Saint Petersburg", "Samara", "Saransk", "Sochi", "Volgograd"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_fixture);

        team1Spin = findViewById(R.id.team1_spin);
        team2Spin = findViewById(R.id.team2_spin);
        dateSpin = findViewById(R.id.date_spin);
        venueSpin = findViewById(R.id.venue_spin);

        ArrayAdapter<String> teamAdapter = new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item,teams);
        team1Spin.setAdapter(teamAdapter);
        team2Spin.setAdapter(teamAdapter);

        ArrayAdapter<String> dateAdapter = new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item,date);
        dateSpin.setAdapter(dateAdapter);

        ArrayAdapter<String> venueAdapter = new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item,venue);
        venueSpin.setAdapter(venueAdapter);

        Button save = findViewById(R.id.save_btn);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(team1Spin.getSelectedItem().toString().matches(team2Spin.getSelectedItem().toString()))
                    Toast.makeText(getApplicationContext(),"Select Different Teams",Toast.LENGTH_SHORT).show();
                else
                addToTable();
            }
        });

    }

    public void addToTable()
    {
        SQLiteDatabase db;
        DataBaseHelper dataBaseHelper= new DataBaseHelper(this);
        int id= dataBaseHelper.no_of_matches()+1;
        db=dataBaseHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("_id",id);
        cv.put("team1",team1Spin.getSelectedItem().toString());
        cv.put("team2",team2Spin.getSelectedItem().toString());
        cv.put("date",dateSpin.getSelectedItem().toString()+" June");
        cv.put("venue",venueSpin.getSelectedItem().toString());
        db.insert("matches",null,cv);
        Toast.makeText(getApplicationContext(),"Saved Successfully",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
    }
}

