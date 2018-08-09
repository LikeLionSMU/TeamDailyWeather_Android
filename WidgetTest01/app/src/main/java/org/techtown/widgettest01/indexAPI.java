package org.techtown.widgettest01;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

/**
 * Created by 수연 on 2018-08-07.
 */

public class indexAPI extends AsyncTask<Map<String, String>, Integer, String> {
    String urlStr;
    String line;
    String longitude;
    String latitude;

    public indexAPI(String urlStr, String longitude, String latitude) {
        this.urlStr = urlStr;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    @Override
    protected String doInBackground(Map<String, String>[] maps) {
        Log.d("urlString", urlStr);
        try {
            URL url = new URL(urlStr);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream in = urlConnection.getInputStream();

                StringBuilder builder = new StringBuilder();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
                while ((line = reader.readLine()) != null) {
                    builder.append(line + "\n");
                }
            } else {
                Log.d("API연결", "실패");
            }
        } catch (MalformedURLException e) {
            Log.d("에러1", "MalformedURL");
            e.printStackTrace();
        } catch (IOException e) {
            Log.d("에러2", "IOException");
            e.printStackTrace();
        }
        return null;
    }
}