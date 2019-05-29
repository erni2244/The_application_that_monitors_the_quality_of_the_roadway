package com.example.gsensor.MapaSerwer;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class wysylanie {

    double szerokoscg;
    double dlugoscg;

    public wysylanie(){
    szerokoscg=0;
    dlugoscg=0;
    }

    public void wyslij(String adres, double szer, double dlug){

        szerokoscg = szer;
        dlugoscg = dlug;
        new WebServiceHandler().execute(adres);

    }


    private class WebServiceHandler extends AsyncTask<String, Void, String> {

        double szerokosc= szerokoscg;
        double wlugosc= dlugoscg;
        @Override
        protected void onPostExecute(String s) {
            //super.onPostExecute(s);
        }

        @Override
        protected String doInBackground(String... urls) {

            try {
                // zakładamy, że jest tylko jeden URL
                URL url = new URL(urls[0]);
                HttpURLConnection connection = (HttpURLConnection) url
                        .openConnection();
                connection.setReadTimeout(10000 /* milliseconds */);
                connection.setConnectTimeout(15000 /* milliseconds */);

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



            return null;
        }















    }

}
