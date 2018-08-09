package org.techtown.widgettest01;

import android.content.ContentValues;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    TextView tc;
    TextView minNmax;
    FloatingActionButton fab_add, fab_mypage, fab_main;
    String[] items = {"강남구", "강동구", "강북구", "강서구", "관악구", "광진구", "구로구", "금천구", "노원구", "도봉구", "동대문구", "동작구", "마포구", "서대문구", "서초구", "성동구", "성북구", "송파구", "양천구", "영등포구", "용산구", "은평구", "종로구", "중구", "중랑구"};
    String[] villages = {"삼성동", "성내1동", "수유3동", "화곡6동", "봉천동", "자양동", "구로동", "시흥동", "상계6.7동", "방학동", "용두동", "노량진2동", "성산동", "연희동", "서초2동", "행당동", "삼선동", "신천동", "신정6동", "당산동", "이태원동", "녹번동", "수송동", "광희동", "신내동"};
    int countyPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textView);
        minNmax = (TextView) findViewById(R.id.minNmax);
        tc = (TextView) findViewById(R.id.tc);

        fab_add = (FloatingActionButton)findViewById(R.id.fab_add);
        fab_mypage = (FloatingActionButton)findViewById(R.id.fab_mypage);
        fab_main = (FloatingActionButton)findViewById(R.id.fab_main);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        // 아이템 선택 이벤트 처리
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            // 아이템 선택시 호출됨
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                textView.setText(items[position] + "  " +villages[position]);
                countyPosition = position;

                String baseUrl = "https://api2.sktelecom.com/weather/current/minutely?appkey=8ab05bc6-8297-4251-bb89-e7a085516286&version=1";
                String city = "&city=서울";
                String county = "&county=" + items[countyPosition];
                String village = "&village=" + villages[countyPosition];
                String url = baseUrl + city + county + village;
                String longitude = "";
                String latitude = "";

                weatherAPI wAPI = new weatherAPI(url);
                try {
                    String[] tInfo = wAPI.execute().get().split("/");

                    tc.setText(tInfo[0]);
                    minNmax.setText(tInfo[1] + " | " + tInfo[2]);

                    longitude = tInfo[3];
                    latitude = tInfo[4];
                } catch (InterruptedException e) {
                    Log.d("weatherAPI 결과1", "에러1");
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    Log.d("weatherAPI 결과2", "에러2");
                    e.printStackTrace();
                }

                baseUrl = "";
                url = baseUrl + longitude + latitude;
                indexAPI iAPI = new indexAPI(url, longitude, latitude);
                try {
                    String index = iAPI.execute().get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

            }
            // 아무것도 선택되지 않았을때 호출됨 -> 여기에 기본 설정 지역을..
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                textView.setText("");
            }
        });

        fab_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToggleFab();
            }
        });

        // 서버통신
        /*
        NetworkTask2 networkTask = new NetworkTask2();
        Map<String, String> params = new HashMap<String, String>();
        params.put("id", "test1");
        params.put("password", "123a4");
        params.put("gu", "GangNam");
        networkTask.execute(params);
        */
    }

    private void ToggleFab(){
        // 버튼이 보여지고 있는 상태인 경우 숨겨줌
        if(fab_add.getVisibility() == View.VISIBLE) {
            fab_add.hide();
            fab_mypage.hide();
            fab_add.animate().translationY(0);
            fab_mypage.animate().translationY(0);
        }
        // 버튼이 숨겨져있는 상태인 경우 위로 올라오면서 보여줌
        else{
            // 중심이 되는 버튼의 높이 + 마진 만큼 거리 계산
            int dy = fab_main.getHeight() + 20;
            fab_add.show();
            fab_mypage.show();

            fab_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent transferIntent = new Intent(getApplicationContext(), WeatherAdd.class);
                    startActivity(transferIntent);
                    Toast.makeText(getApplication(), "글쓰기버튼이 눌렸습니다.", Toast.LENGTH_LONG).show();
                }
            });

            fab_mypage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent transferIntent = new Intent(getApplicationContext(), MyPage.class);
                    startActivity(transferIntent);
                    Toast.makeText(getApplication(), "마이페이지로 이동했습니다.", Toast.LENGTH_LONG).show();
                }
            });

            // 계산된 거리만큼 이동하는 애니메이션을 입력
            fab_add.animate().translationY(-dy*2);
            fab_mypage.animate().translationY(-dy);
        }
    }
}
