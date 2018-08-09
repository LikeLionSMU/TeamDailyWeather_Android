package org.techtown.widgettest01;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import java.util.Map;

/**
 * Created by pigpa on 2018-05-17.
 */


public class NetworkTask2 extends AsyncTask<Map<String, String>, Integer, String> {
    private Data data;
    public Data getData() {
        return this.data;
    }

    @Override
    protected String doInBackground(Map<String, String>... maps) { // 내가 전송하고 싶은 파라미터
        // Http 요청 준비 작업
        HttpClient.Builder http = new HttpClient.Builder("POST", "http://13.229.144.160:3000/api/users");

        // Parameter 를 전송한다.
        http.addAllParameters(maps[0]);

        //Http 요청 전송
        HttpClient post = http.create();
        post.request();

        // 응답 본문 가져오기
        String body = post.getBody();
        return body;
    }

    @Override
    protected void onPostExecute(String s) {
        Log.d("JSON_RESULT", s);
        Gson gson = new Gson();

        data = gson.fromJson(s, Data.class);

        Log.d("JSON_RESULT", "받은 success: " + data.getSuccess());
//        Log.d("JSON_RESULT", "받은 gu: " + data.getGu());

    }

}