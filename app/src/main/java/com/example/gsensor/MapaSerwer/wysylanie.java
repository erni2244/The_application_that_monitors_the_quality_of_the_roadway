package com.example.gsensor.MapaSerwer;

import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

import okhttp3.OkHttpClient;

public class wysylanie {

    double szerokoscg;
    double dlugoscg;

    public wysylanie(){
    szerokoscg=0;
    dlugoscg=0;

    }

    public void wyslij(String adres, double szer, double dlug){

        szerokoscg = dlug;
        dlugoscg = szer;

        new WebServiceHandler().execute(adres);

    }


    private class WebServiceHandler extends AsyncTask<String, Void, String> {

        double szerokosc= szerokoscg;
        double dlugosc= dlugoscg;
        @Override
        protected void onPostExecute(String s) {
            //super.onPostExecute(s);
        }

        @Override
        protected String doInBackground(String... urls) {

StringBuilder sb = new StringBuilder();

            try{
                String dlugo= dlugosc+"";
                String szerok = szerokosc+"";

                String link3="http://46.41.148.242/add.php"+"?"+"latitude="+szerokosc+"&"+"longitude="+dlugosc;
                URL url = new URL(link3);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setReadTimeout(10000);
                        urlConnection.setConnectTimeout(15000);

InputStream in = new BufferedInputStream(urlConnection.getInputStream());
BufferedReader bin = new BufferedReader(new InputStreamReader(in));
String inputLine;
while ((inputLine=bin.readLine()) != null ){
    sb.append(inputLine);
}

            } catch(Exception e){
                return new String("Exception: " + e.getMessage());
            }








            try{

                String link1="http://46.41.148.242/generate_json_map.php";
                URL url = new URL(link1);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setReadTimeout(10000);
                urlConnection.setConnectTimeout(15000);

                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader bin = new BufferedReader(new InputStreamReader(in));
                String inputLine;
                while ((inputLine=bin.readLine()) != null ){
                    sb.append(inputLine);
                }

            } catch(Exception e){
                return new String("Exception: " + e.getMessage());
            }









            /*
            try {
                // zakładamy, że jest tylko jeden URL
                URL url = new URL(urls[0]);
                HttpURLConnection connection = (HttpURLConnection) url
                        .openConnection();
                connection.setReadTimeout(10000 *//* milliseconds */    /*);
                connection.setConnectTimeout(15000 *//* milliseconds */      /*);

                // zezwolenie na wysyłanie danych
                connection.setDoOutput(true);
                // ustawienie typu wysyłanych danych
                connection.setRequestProperty("Content-Type",
                        "application/json");
                // ustawienie metody
                connection.setRequestMethod("POST");

                // stworzenie obiektu do wysłania
                JSONObject data = new JSONObject();
                data.put("name", "Ernest");

                // wysłanie obiektu
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(connection.getOutputStream(),
                                "UTF-8"));
                writer.write(data.toString());
                writer.close();

                // sprawdzenie kodu odpowiedzi, 200 = OK
                if (connection.getResponseCode() != 200) {
                    throw new Exception("Bad Request");
                }


            } catch (Exception e) {
                // obsłuż wyjątek
                return null;
            }
*/


            return null;
        }















    }

}
