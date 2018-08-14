package org.techtown.widgettest01;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

/**
 * Created by 수연 on 2018-08-14.
 */

public class UVAPI extends AsyncTask<Map<String, String>, Integer, String> {
    String urlStr;
    String line;
    String longitude;
    String latitude;

    public UVAPI(String urlStr, String longitude, String latitude) {
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

                String result = builder.toString();
                String comment = "";

                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONObject weather = jsonObject.getJSONObject("weather");
                    JSONObject wIndex = weather.getJSONObject("wIndex");
                    JSONArray wctIndex  = wIndex.getJSONArray("uvindex");
                    JSONObject c = wctIndex.getJSONObject(0);

                    JSONObject currentJSON = c.getJSONObject("day00");

                    comment = currentJSON.getString("comment");

                    Log.d(">>>>> comment", comment);
                } catch (JSONException e) {
                    Log.d("제이슨 만들기", "실패");
                    e.printStackTrace();
                }

                urlConnection.disconnect();
                return comment;
            } else {
                Log.d("UV-API연결", "실패");
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
