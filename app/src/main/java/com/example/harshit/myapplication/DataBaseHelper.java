package com.example.harshit.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class DataBaseHelper extends SQLiteOpenHelper {
    SQLiteDatabase db;
    int num;

    public static final String DATABASE_NAME = "fifa";
    public static final String TABLE1_NAME = "teams";
    public static final String TABLE2_NAME = "matches";
    public static final String TABLE1_COL_ID = "_id";
    public static final String TABLE1_COL_NAME = "name";
    public static final String TABLE1_COL_ICON = "icon";
    public static final String TABLE2_COL_ID = "_id";
    public static final String TABLE2_COL_TEAM1 = "team1";
    public static final String TABLE2_COL_TEAM2 = "team2";
    public static final String TABLE2_COL_DATE = "date";
    public static final String TABLE2_COL_VENUE = "venue";

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

            db.execSQL("CREATE TABLE " + TABLE1_NAME + " ( " + TABLE1_COL_ID + " INTEGER," + TABLE1_COL_NAME + " TEXT ," + TABLE1_COL_ICON + " TEXT)");
            db.execSQL("CREATE TABLE " + TABLE2_NAME + " ( " + TABLE2_COL_ID + " INTEGER," + TABLE2_COL_TEAM1 + " TEXT ," + TABLE2_COL_TEAM2 + " TEXT ," + TABLE2_COL_DATE + " TEXT ," + TABLE2_COL_VENUE + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public Boolean isPresent1() {
        db = this.getReadableDatabase();
        long num = DatabaseUtils.queryNumEntries(db,TABLE2_NAME);
        return(!(num==0));
    }

    public Boolean isPresent2() {
        db = this.getReadableDatabase();
        long num = DatabaseUtils.queryNumEntries(db,TABLE2_NAME);
        return num != 0;
    }

    public void def_teams(Context context) {
        String[] teams = {"ARG", "AUS","BEL", "BRA", "COL", "CRC", "CRO", "DEN", "EGY", "ENG", "FRA", "GER", "MAR", "NGA", "SEN", "TUN", "IRN", "JPN", "KOR", "KSA", "MEX", "PAN", "PER", "URU", "ISL", "POL", "POR", "RUS", "SRB", "ESP", "SWE", "SUI"};
        int[] icon = {R.drawable.arg,R.drawable.aus,R.drawable.bel,R.drawable.bra,R.drawable.col,R.drawable.crc,R.drawable.cro,R.drawable.den,R.drawable.egy,R.drawable.eng,R.drawable.fra,R.drawable.ger,R.drawable.mar,R.drawable.nga,R.drawable.sen,R.drawable.tun,R.drawable.irn,R.drawable.jpn,R.drawable.kor,R.drawable.ksa,R.drawable.mex,R.drawable.pan,R.drawable.per,R.drawable.uru,R.drawable.isl,R.drawable.pol,R.drawable.por,R.drawable.rus,R.drawable.srb,R.drawable.esp,R.drawable.swe,R.drawable.sui};
        String[] pathName = new String[32];
        for(int i=0;i<32;i++) {
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),icon[i]);
            pathName[i] = saveToInternalStorage(bitmap,context,teams[i].toLowerCase());
        }
        db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        for (int i = 0; i < 32; i++) {
            cv.put(TABLE1_COL_ID,i+1);
            cv.put(TABLE1_COL_NAME, teams[i]);
            cv.put(TABLE1_COL_ICON,pathName[i]);
            db.insert(TABLE1_NAME, null, cv);
        }
        db.close();
    }

    public void def_matches(){
        num=16;
        String[] teams = {"ARG", "BEL", "AUS", "BRA", "COL", "CRC", "CRO", "DEN", "EGY", "ENG", "FRA", "GER", "MAR", "NGA", "SEN", "TUN", "IRN", "JPN", "KOR", "KSA", "MEX", "PAN", "PER", "URU", "ISL", "POL", "POR", "RUS", "SRB", "ESP", "SWE", "SUI"};
        String venue[] = {"Ekaterinburg", "Kaliningrad", "Kazan", "Moscow", "Nizhny Novgorod", "Rostov-on-don", "Saint Petersburg", "Samara", "Saransk", "Sochi", "Volgograd"};
        db=this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        for(int i=0;i<16;i++)
        {
            cv.put(TABLE2_COL_ID,i+1);
            cv.put(TABLE2_COL_TEAM1,teams[2*i]);
            cv.put(TABLE2_COL_TEAM2,teams[2*i+1]);
            cv.put(TABLE2_COL_DATE,14+i + " June");
            if(i<11)
                cv.put(TABLE2_COL_VENUE,venue[i]);
            else
                cv.put(TABLE2_COL_VENUE,venue[i-5]);
            db.insert(TABLE2_NAME,null,cv);
        }
        db.close();
    }

    public String getTeam1(int i){
        String name;
        db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT "+TABLE2_COL_TEAM1+" FROM "+ TABLE2_NAME + " WHERE "+ TABLE2_COL_ID+ " = "+(i+1),null);
        c.moveToFirst();
        name=c.getString(c.getColumnIndexOrThrow(TABLE2_COL_TEAM1));
        c.close();
        db.close();

        return name;
    }

    public Bitmap getImgId(String s,int i,Context context){
        db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT icon FROM teams WHERE _id="+(i+1),null);
        c.moveToFirst();
        String path = c.getString(c.getColumnIndexOrThrow("icon"));
        c.close();
        return loadImageFromStorage(path,s.toLowerCase());
    }

    public String getTeam2(int i){
        String name;
        db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT "+TABLE2_COL_TEAM2+" FROM "+ TABLE2_NAME + " WHERE "+ TABLE2_COL_ID+ " = "+(i+1),null);
        c.moveToFirst();
        name=c.getString(c.getColumnIndexOrThrow(TABLE2_COL_TEAM2));
        c.close();
        db.close();

        return name;
    }

    public String getDate(int i){
        String name;
        db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT "+TABLE2_COL_DATE+" FROM "+ TABLE2_NAME + " WHERE "+ TABLE2_COL_ID+ "="+(i+1),null);
        c.moveToFirst();
        name=c.getString(c.getColumnIndexOrThrow(TABLE2_COL_DATE));
        c.close();
        db.close();

        return name;
    }

    public String getVenue(int i){
        String name;
        db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT "+TABLE2_COL_VENUE+" FROM "+ TABLE2_NAME + " WHERE "+ TABLE2_COL_ID+ "="+(i+1),null);
        c.moveToFirst();
        name=c.getString(c.getColumnIndexOrThrow(TABLE2_COL_VENUE));
        c.close();
        db.close();

        return name;
    }

    public int no_of_matches(){
        SQLiteDatabase db;
        db = this.getReadableDatabase();
        int num =(int) DatabaseUtils.queryNumEntries(db,TABLE2_NAME);
        return num;
    }

    public void delete_row(int row){
        int n=no_of_matches();
        SQLiteDatabase db;
        db=this.getWritableDatabase();
        db.delete("matches","_id="+(row),null);
        for(int i=1;i<=n;i++)
            if (i > row)
            {
                db.execSQL("UPDATE matches SET _id=" + (i - 1) + " WHERE _id=" + i);
            }
    }

    public String saveToInternalStorage(Bitmap bitmapImage,Context context,String name){
        ContextWrapper cw = new ContextWrapper(context);
        File directory = cw.getDir("imageDir",Context.MODE_PRIVATE);
        File mypath = new File(directory,name);

        FileOutputStream fos = null;
        try{
            fos = new FileOutputStream(mypath);
            bitmapImage.compress(Bitmap.CompressFormat.PNG,100,fos);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
                fos.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
    }

    public Bitmap loadImageFromStorage(String path,String name){
        try {
            File f=new File(path, name);
            return BitmapFactory.decodeStream(new FileInputStream(f));
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

        return Bitmap.createBitmap(200,200,Bitmap.Config.ARGB_8888);
    }
}
