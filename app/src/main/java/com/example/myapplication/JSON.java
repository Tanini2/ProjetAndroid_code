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
            //creating a URL
            URL url = new URL(urlEnvoyee[0]);

            //Opening the URL using HttpURLConnection
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            //StringBuilder object to read the string from the service
            StringBuilder sb = new StringBuilder();

            //We will use a buffered reader to read the string from service
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

            //A simple string to read values from each line
            String json;

            //reading until we don't find null
            while ((json = bufferedReader.readLine()) != null) {

                //appending it to string builder
                sb.append(json + "\n");
            }

            //finally returning the read string
            return sb.toString().trim();
        } catch (Exception e) {
            return null;
        }

    }
}
