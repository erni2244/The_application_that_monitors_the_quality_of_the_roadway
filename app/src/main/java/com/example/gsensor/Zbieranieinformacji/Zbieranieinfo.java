package com.example.gsensor.Zbieranieinformacji;

import android.Manifest;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gsensor.Bazadanychpaczka.Bazadanych;
import com.example.gsensor.Bazadanychpaczka.Bazatabele;
import com.example.gsensor.GPSlocalization;
import com.example.gsensor.MapaSerwer.wysylanie;
import com.example.gsensor.R;

public class Zbieranieinfo extends AppCompatActivity implements SensorEventListener {

    private int PERMISION_GPS=1;
    private Bazadanych mBazadanych;
    private GPSlocalization GPSpolozenie;
    private GPSlistener gpslistener;
    public TextView oskax, oskay, oskaz, lokaliz;
    public TextView wiadomosc;
    public Sensor mojsensor;
    public SensorManager GS;
    private long lastUpdate = 0;
    Button start;
    public int zmiennastart=0;
    EditText editText;

    private wysylanie wysyl;

    //*****************grawitacja********************
    private double[] gravity;
    private double alpha=0.95;

    double czulosc=4.0;
    //***********************************************

Button but;

    public int ii = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zbieranieinfo);
        lokaliz =(TextView) findViewById(R.id.GPS) ;
        start = (Button)findViewById(R.id.start1);

//*************sprawdzenie uprawnien*************
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},PERMISION_GPS);
        }

        GPSpolozenie= new GPSlocalization(this);
        gpslistener = new GPSlistener(this,this);
        mBazadanych = new Bazadanych(this);

        try {//tym nizej mozna oddzielac kolejne wlacaenia apki
            Cursor cursor = wesbaze();
            //pokazbaze(cursor);
        } finally {
            mBazadanych.close();
        }




        GS = (SensorManager) getSystemService(SENSOR_SERVICE);
        mojsensor = GS.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        GS.registerListener(this, mojsensor, SensorManager.SENSOR_DELAY_NORMAL);

        oskax = (TextView) findViewById(R.id.OSX1);
        oskay = (TextView) findViewById(R.id.OSY1);
        oskaz = (TextView) findViewById(R.id.OSZ1);
        wiadomosc = (TextView) findViewById(R.id.wiadomosc);



        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(zmiennastart==0) {zmiennastart = 1; start.setText("STOP");start.setBackgroundResource(android.R.color.holo_red_light);}else{zmiennastart=0;start.setText("START");start.setBackgroundResource(android.R.color.holo_blue_light);}
            }
        });


wysyl = new wysylanie();

    }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

//*********wyswietla zawartosc bazy***************
    private void pokazbaze(Cursor cursor) {
        StringBuilder budowaczstringa = new StringBuilder("BNSit trainings: \n");
        while(cursor.moveToNext()){
            long id = cursor.getLong(0);
            String title = cursor.getString(1);
            String osx = cursor.getString(2);
            String osy = cursor.getString(3);
            String osz = cursor.getString(4);
            String dlugosc = cursor.getString(5);
            String szerokosc = cursor.getString(6);
            budowaczstringa.append(id + ":");
            budowaczstringa.append(title +":");
            budowaczstringa.append(osx +":");
            budowaczstringa.append(osy +":");
            budowaczstringa.append(osz +" GPS: ");
            budowaczstringa.append(dlugosc + ":");
            budowaczstringa.append(szerokosc + "\n");

        }

        //tutaj miejsce gdzie wyswietlam !!!!!!!!!!!!! jakis leyaut / text view
    }


    //************
    private Cursor wesbaze() {
        SQLiteDatabase database = mBazadanych.getReadableDatabase();
        //UWAGA OSTATNIA WARTOSC TEGO PONIZEJ TO SORTOWANIE !!!!!!!!!!!!!!!
        Cursor cursor = database.query(Bazatabele.TABLE_NAME,new String[]{Bazatabele._ID, Bazatabele.TITLE, Bazatabele.osxx, Bazatabele.osyy, Bazatabele.oszz, Bazatabele.dlugosc, Bazatabele.szerokosc},null,null,null,null,null);
        startManagingCursor(cursor);
        return cursor;
    }

//**************dodaje kolejne wartosci do bazy***********************
    private void dodajwartosc(String title, String osxxx, String osyyy, String oszzz, String dlugosc, String szerokosc) {
        SQLiteDatabase database = (SQLiteDatabase) mBazadanych.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Bazatabele.TITLE, title);
        values.put(Bazatabele.osxx, osxxx);
        values.put(Bazatabele.osyy, osyyy);
        values.put(Bazatabele.oszz, oszzz);
        values.put(Bazatabele.dlugosc, dlugosc);
        values.put(Bazatabele.szerokosc, szerokosc);
        database.insertOrThrow(Bazatabele.TABLE_NAME,null, values);
    }



//******************************metoda wywolywana gdy akcelerometr odczyta zmiane wartosci***************
    @Override
    public void onSensorChanged(SensorEvent zdazenie) {

        Sensor mySensor = zdazenie.sensor;
double[] linear_acceleration=new double[3];
double wypadkowa;
double wstrzas;

        if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            oskax.setText("Wartość x: " + zdazenie.values[0]);
            oskay.setText("Wartość y: " + zdazenie.values[1]);
            oskaz.setText("Wartość z: " + zdazenie.values[2]);
            String OSKAX = "" + zdazenie.values[0];
            String OSKAY = "" + zdazenie.values[1];
            String OSKAZ = "" + zdazenie.values[2];
            String dlugoscGEO;
            String szerokoscGEO;

            if (gravity == null) {
                gravity = new double[3];
                gravity[0] = zdazenie.values[0];
                gravity[0] = zdazenie.values[1];
                gravity[0] = zdazenie.values[2];
            } else{
//**************filtr dolnoprzepustowy********************
                gravity[0] = alpha * gravity[0] + (1 - alpha) * zdazenie.values[0];
                gravity[1] = alpha * gravity[1] + (1 - alpha) * zdazenie.values[1];
                gravity[2] = alpha * gravity[2] + (1 - alpha) * zdazenie.values[2];

//**************korekta***********************************
                linear_acceleration[0] = zdazenie.values[0] - gravity[0];
                linear_acceleration[1] = zdazenie.values[1] - gravity[1];
                linear_acceleration[2] = zdazenie.values[2] - gravity[2];

                wypadkowa=Math.sqrt(gravity[0]*gravity[0]+gravity[1]*gravity[1]+gravity[2]*gravity[2]);
                wstrzas=linear_acceleration[0]*gravity[0]/wypadkowa + linear_acceleration[1]*gravity[1]/wypadkowa + linear_acceleration[2]*gravity[2]/wypadkowa;

                long curTime = System.currentTimeMillis();

if(wstrzas>czulosc && GPSpolozenie.podajdlugosc()!=null && zmiennastart==1 &&(curTime - lastUpdate) > 500){
    Toast.makeText(this,"DZIURA!!!! "+wstrzas,Toast.LENGTH_LONG).show();
    ii = ii + 1;
    szerokoscGEO = gpslistener.podajszer();
    dlugoscGEO = gpslistener.podajdlug();

    dodajwartosc("Dziura"+ii,OSKAX,OSKAY,OSKAZ,dlugoscGEO,szerokoscGEO);
    wysyl.wyslij("http://damianchodorek.com/wsexample/", Double.parseDouble(dlugoscGEO),Double.parseDouble(szerokoscGEO));

    lastUpdate = curTime;

                lokaliz.setText("szerokosc/dlugosc: " + gpslistener.podajszer() + " : " + gpslistener.podajdlug());

                //---------------------------------------------
                wiadomosc.setText("Ilosc wykrytych dziur = " + ii);
            }


        }



        }
    }

//--------------------------------------------------------------------------------












    @Override
    public void onAccuracyChanged(Sensor sensor, int event) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. Th0e action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }




    @Override
    protected void onStart() {

        super.onStart();
    }


    @Override
    protected void onStop() {

        super.onStop();
    }


    @Override
    protected void onResume() {

        super.onResume();
    }


    @Override
    protected void onRestart() {

        super.onRestart();
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }
}
