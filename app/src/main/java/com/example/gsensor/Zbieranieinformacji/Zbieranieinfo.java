package com.example.gsensor.Zbieranieinformacji;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
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
//import com.example.gsensor.GPSlocalization;
import com.example.gsensor.MapaSerwer.wysylanie;
import com.example.gsensor.R;

public class Zbieranieinfo extends AppCompatActivity {

    private int PERMISION_GPS=1;
  //  public TextView oskax, oskay, oskaz, lokaliz;
   // public TextView wiadomosc;
    Button start;
    public static int zmiennastartactivity=0;
    Intent serviceIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zbieranieinfo);
    //    lokaliz =(TextView) findViewById(R.id.GPS) ;
        start = (Button)findViewById(R.id.start1);

//*************sprawdzenie uprawnien*************
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},PERMISION_GPS);
        }


  /*      try {//tym nizej mozna oddzielac kolejne wlacaenia apki
            cursor = wesbaze();
            //pokazbaze(cursor);
        } finally {
            mBazadanych.close();
            cursor.close();
        }

*/

     /*   oskax = (TextView) findViewById(R.id.OSX1);
        oskay = (TextView) findViewById(R.id.OSY1);
        oskaz = (TextView) findViewById(R.id.OSZ1);
        wiadomosc = (TextView) findViewById(R.id.wiadomosc);
*/


        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(zmiennastartactivity==0) {
                    zmiennastartactivity = 1;
                    start.setText("STOP");
                    start.setBackgroundResource(android.R.color.holo_red_light);
                    startMyService();
                }else{
                    zmiennastartactivity=0;
                    start.setText("START");
                    start.setBackgroundResource(android.R.color.holo_blue_light);
                    stopMyService();
                }
            }
        });

    }

    private void startMyService() {
        Toast.makeText(this,"start ",Toast.LENGTH_LONG).show();
        serviceIntent = new Intent(this,serviceclass.class);
        startService(serviceIntent);
    }
    private void stopMyService() {
        serviceIntent = new Intent(this,serviceclass.class);
        Toast.makeText(this,"end ",Toast.LENGTH_LONG).show();
        stopService(serviceIntent);
    }



/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

//*********wyswietla zawartosc bazy***************
 /*   private void pokazbaze(Cursor cursor) {
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

*/
    //************
  /*  private Cursor wesbaze() {
        SQLiteDatabase database = mBazadanych.getReadableDatabase();
        //UWAGA OSTATNIA WARTOSC TEGO PONIZEJ TO SORTOWANIE !!!!!!!!!!!!!!!
        Cursor cursor = database.query(Bazatabele.TABLE_NAME,new String[]{Bazatabele._ID, Bazatabele.TITLE, Bazatabele.osxx, Bazatabele.osyy, Bazatabele.oszz, Bazatabele.dlugosc, Bazatabele.szerokosc},null,null,null,null,null);
        startManagingCursor(cursor);
        return cursor;
    }
*/






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
            //Toast.makeText(this,"onStart!!!! ",Toast.LENGTH_LONG).show();
        super.onStart();
    }


    @Override
    protected void onStop() {
            //Toast.makeText(this,"onStop!!!! ",Toast.LENGTH_LONG).show();
        super.onStop();
    }


    @Override
    protected void onResume() {
            //Toast.makeText(this,"onResume!!!! ",Toast.LENGTH_LONG).show();
        super.onResume();
    }

    @Override
    protected void onRestart() {
               //Toast.makeText(this,"onRestart!!!! ",Toast.LENGTH_LONG).show();

        super.onRestart();

    }

    @Override
    protected void onDestroy() {
        //Toast.makeText(this,"onDestroy!!!! ",Toast.LENGTH_LONG).show();
        if(zmiennastartactivity==1){
        stopMyService();
        }
        super.onDestroy();
    }
}
