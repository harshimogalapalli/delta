package com.example.harshit.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    LinearLayout linearLayout;
    DataBaseHelper dataBaseHelper;
    int i;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataBaseHelper = new DataBaseHelper(this);
        linearLayout = findViewById(R.id.linearLayout);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

            if(!dataBaseHelper.isPresent1())
                dataBaseHelper.def_teams(getApplicationContext());
            if(!dataBaseHelper.isPresent2())
                dataBaseHelper.def_matches();

            disp_matches();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        switch (menuItem.getItemId()){
            case R.id.addFixture:{
                Intent new_fixture;
                new_fixture = new Intent(getApplicationContext(), new_fixture.class);
                startActivity(new_fixture);
                return true;
            }

            default: return super.onOptionsItemSelected(menuItem);
        }
    }

    public void disp_matches(){
        for(i=0;i<dataBaseHelper.no_of_matches();i++) {
            final int pass = i;
            final String t1=dataBaseHelper.getTeam1(i);
            final String t2=dataBaseHelper.getTeam2(i);
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
            icon1.setImageBitmap(dataBaseHelper.getImgId(dataBaseHelper.getTeam1(i),i,getApplicationContext()));
            icon2.setImageURI(null);
            icon2.setImageBitmap(dataBaseHelper.getImgId(dataBaseHelper.getTeam2(i),i,getApplicationContext()));
            icon1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent filter = new Intent(getApplicationContext(),Filter_Matches.class);
                    filter.putExtra("item",t1);
                    startActivity(filter);
                }
            });
            icon2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent filter = new Intent(getApplicationContext(),Filter_Matches.class);
                    filter.putExtra("item",t2);
                    startActivity(filter);
                }
            });
            team1.setText(dataBaseHelper.getTeam1(i));
            vs.setText(" vs ");
            team2.setText(dataBaseHelper.getTeam2(i));
            venue.setText(dataBaseHelper.getVenue(i));
            date.setText(dataBaseHelper.getDate(i));

            if(i%2==0) main.setBackgroundColor(Color.parseColor("#FF304FFE"));
            else main.setBackgroundColor(Color.parseColor("#FF8C9EFF"));

            main.setGravity(Gravity.CENTER);
            venue.setTypeface(venue.getTypeface(), Typeface.BOLD);

            main.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent edit_fixture = new Intent(getApplicationContext(), edit_fixtures.class);
                    edit_fixture.putExtra("team1", dataBaseHelper.getTeam1(pass));
                    edit_fixture.putExtra("team2", dataBaseHelper.getTeam2(pass));
                    edit_fixture.putExtra("date", dataBaseHelper.getDate(pass));
                    edit_fixture.putExtra("venue", dataBaseHelper.getVenue(pass));
                    edit_fixture.putExtra("id", pass+1);
                    startActivity(edit_fixture);
                }
            });

            main.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setMessage("Delete this fixture???");
                    builder.setTitle("WARNING");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dataBaseHelper.delete_row(pass+1);
                            Intent intent=getIntent();
                            finish();
                            startActivity(intent);
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder.show();
                    builder.setCancelable(false);

                    return false;
                }
            });

            ll.addView(icon1, 150,150);
            ll.addView(team1,ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            ll.addView(vs, ViewGroup.LayoutParams.WRAP_CONTENT);
            ll.addView(team2,ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            ll.addView(icon2,150,150);
            main.addView(ll, RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            main.addView(date, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            main.addView(venue, RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            linearLayout.addView(main, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        }
    }

}
