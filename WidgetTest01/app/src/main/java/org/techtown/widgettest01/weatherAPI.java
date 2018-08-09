package org.techtown.widgettest01;

import android.os.AsyncTask;
import android.os.Message;
import android.util.JsonReader;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Node;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by 수연 on 2018-08-02.
 */

public class weatherAPI extends AsyncTask<Map<String, String>, Integer, String> {
    String urlStr;
    String line;

    public weatherAPI(String url){
        this.urlStr = url;
    }

    @Override
    protected String doInBackground(Map<String, String>[] maps) {
//        urlStr = "https://api2.sktelecom.com/weather/current/minutely?appkey=8ab05bc6-8297-4251-bb89-e7a085516286&version=1&city=서울&county=강남구&village=삼성동";
        Log.d("urlString", urlStr);

        // 현재 날씨 최고기온 최저기온 받아오는 (분별)API
        try {
            URL url = new URL(urlStr);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            if(urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
                InputStream in = urlConnection.getInputStream();

                StringBuilder builder = new StringBuilder();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
                while((line = reader.readLine()) != null){
                    builder.append(line + "\n");
                }
                String result = builder.toString();

                String[] temperature = new String[5];
                String tc;
                String tmax;
                String tmin;
                String longitude;
                String latitude;

                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONObject weather = jsonObject.getJSONObject("weather");
                    JSONArray minutely  = weather.getJSONArray("minutely");
                    JSONObject c = minutely.getJSONObject(0);

                    JSONObject temperatureJSON = c.getJSONObject("temperature");
                    JSONObject stationJSON = c.getJSONObject("station");

                    tc = temperatureJSON.getString("tc");
                    tmax = temperatureJSON.getString("tmax");
                    tmin = temperatureJSON.getString("tmin");
                    longitude = stationJSON.getString("longitude");
                    latitude = stationJSON.getString("latitude");

                    Log.d(">>>>> tc", tc);
                    Log.d(">>>>> tmax", tmax);
                    Log.d(">>>>> tmin", tmin);
                    Log.d(">>>>> longitude", longitude);
                    Log.d(">>>>> latitude", latitude);

                    temperature[0] = tc;
                    temperature[1] = tmax;
                    temperature[2] = tmin;
                    temperature[3] = longitude;
                    temperature[4] = latitude;

                } catch (JSONException e) {
                    Log.d("제이슨 만들기", "실패");
                    e.printStackTrace();
                }

                urlConnection.disconnect();
                return temperature[0]+"/"+temperature[1]+"/"+temperature[2]+"/"+temperature[3]+"/"+temperature[4];
            }else{
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
//
//    public String[] weatherJSONParser(String jsonString){
//        String[] resultStr = new String[3];
//        String tc;
//        String tmax;
//        String tmin;
//        try {
//            JSONArray jobj = new JSONObject(jsonString).getJSONArray("minutely");
//            Log.d("json Array length", String.valueOf(jobj.length()));
//
//            for(int i = 0; i < jobj.length(); i++){
//                JSONObject jObject = jobj.getJSONObject(i);
//
//                tc = jObject.optString("tc");
//                tmax = jObject.optString("tmax");
//                tmin = jObject.optString("tmin");
//
//                Log.d(">>>>> tc", tc);
//                Log.d(">>>>> tmax", tmax);
//                Log.d(">>>>> tmin", tmin);
//
//
//                resultStr[0] = tc;
//                resultStr[1] = tmax;
//                resultStr[2] = tmin;
//            }
//            return resultStr;
//        } catch (JSONException e) {
//            Log.d("ㅁㅁㅁㅁㅁ", "error");
//            e.printStackTrace();
//        }
//
//        return resultStr;
//    }
}
