package com.example.harshit.myapplication;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class edit_fixtures extends AppCompatActivity {
    Intent edit_fixture;
    Spinner team1Spin,team2Spin,dateSpin,venueSpin;
    String teams[] = {"ARG", "BEL", "AUS", "BRA", "COL", "CRC", "CRO", "DEN", "EGY", "ENG", "FRA", "GER", "MAR", "NGA", "SEN", "TUN", "IRN", "JPN", "KOR", "KSA", "MEX", "PAN", "PER", "URU", "ISL", "POL", "POR", "RUS", "SRB", "ESP", "SWE", "SUI"};
    String date[] = {"14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29"};
    String venue[] = {"Ekaterinburg", "Kaliningrad", "Kazan", "Moscow", "Nizhny Novgorod", "Rostov-on-don", "Saint Petersburg", "Samara", "Saransk", "Sochi", "Volgograd"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_fixtures);

        edit_fixture = getIntent();

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

        team1Spin.setSelection(getTeamPos(edit_fixture.getStringExtra("team1")));
        team2Spin.setSelection(getTeamPos(edit_fixture.getStringExtra("team2")));
        dateSpin.setSelection(getDatePos(edit_fixture.getStringExtra("date")));
        venueSpin.setSelection(getVenuePos(edit_fixture.getStringExtra("venue")));

        Button save = findViewById(R.id.save_btn);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateTable();
            }
        });

    }

    public int getTeamPos(String s){
        for(int i=0;i<32;i++)
            if (teams[i].matches(s))
                return i;
        return 10;
    }

    public int getDatePos(String s){
        for(int i=0;i<16;i++)
        if(s.substring(0,2).matches(date[i]))
            return i;
        return 0;
    }

    public int getVenuePos(String s){
        for(int i=0;i<32;i++)
            if (venue[i].matches(s))
                return i;
        return 0;
    }

    public void updateTable(){
        int id = edit_fixture.getIntExtra("id",1);
        Log.i("alert",String.valueOf(id));
        DataBaseHelper dataBaseHelper = new DataBaseHelper(this);
        SQLiteDatabase db;
        db = dataBaseHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("team1",team1Spin.getSelectedItem().toString());
        cv.put("team2",team2Spin.getSelectedItem().toString());
        cv.put("date",dateSpin.getSelectedItem().toString()+" June");
        cv.put("venue",venueSpin.getSelectedItem().toString());
        db.update("matches",cv,"_id=" + id,null);
        Toast.makeText(edit_fixtures.this,"Saved Successfully",Toast.LENGTH_SHORT).show();
    }
}
