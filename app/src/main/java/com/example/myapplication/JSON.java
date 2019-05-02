package com.example.myapplication;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class JSON extends AsyncTask<String, Void, String> {

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    //this method will be called after execution
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }

    //in this method we are fetching the json string
    @Override
    protected String doInBackground(String... urlEnvoyee) {



        try {
            //Crée l'URL
            URL url = new URL(urlEnvoyee[0]);

            //Ouvre l'URL
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            //Crée une StringBuilder et un BufferedReader
            StringBuilder sb = new StringBuilder();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

            String json;

            //Lire tant que la valeur n'est pas nulle
            while ((json = bufferedReader.readLine()) != null) {
                //Ajouter la valeur
                sb.append(json + "\n");
            }
            //Retourner la string
            return sb.toString().trim();
        } catch (Exception e) {
            return null;
        }

    }
}
