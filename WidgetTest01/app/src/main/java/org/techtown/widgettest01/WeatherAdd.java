package org.techtown.widgettest01;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class WeatherAdd extends AppCompatActivity {

    Button myLocation;
    // 날씨
    /*
    TextView humidity = (TextView)findViewById(R.id.humidity);
    TextView wind = (TextView)findViewById(R.id.wind);

    // 옷
    TextView top = (TextView)findViewById(R.id.top);
    TextView bottom = (TextView)findViewById(R.id.bottom);
    TextView outer = (TextView)findViewById(R.id.outer);
*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_add);

        final String Item[] = {"강남구", "강동구", "강북구", "강서구", "관악구", "광진구", "구로구", "금천구", "노원구", "도봉구", "동대문구",
                "동작구", "마포구", "서대문구", "서초구", "성동구", "성북구", "송파구", "양천구", "영등포구", "용산구", "은평구", "종로구", "중구", "중랑구"};
        final AlertDialog.Builder oDialog = new AlertDialog.Builder(this, android.R.style.Theme_DeviceDefault_Light_Dialog_Alert);

        myLocation = (Button) findViewById(R.id.myLocation);
        myLocation.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                oDialog.setTitle("내 지역 선택").setItems(Item, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        myLocation.setText(Item[i]);
                    }
                }).setCancelable(true).show();
            }
        });
    }

    public void popupW(View v){
        Intent intent = new Intent(this, popup_weather.class);
        startActivityForResult(intent, 1);
    }

    public void popupC(View v){
        Intent intent = new Intent(this, popup_clothes.class);
        startActivityForResult(intent, 1);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 넘어갔던 화면에서 되돌아 왔을 때
        if (resultCode == 100) { // 정상 반환일 경우에만 동작하겠다. 100: 날씨 정보
            TextView temperature = (TextView)findViewById(R.id.temperature);
            String temp = data.getStringExtra("temperature");
            temperature.setText(temp);

            TextView humidity = (TextView)findViewById(R.id.humidity);
            temp = data.getStringExtra("humidity");
            humidity.setText(temp);

            TextView wind = (TextView)findViewById(R.id.wind);
            temp = data.getStringExtra("wind");
            wind.setText(temp);
        }
        else if(resultCode == 200){
            String temp;
            TextView top = (TextView)findViewById(R.id.top);
            temp = data.getStringExtra("top");
            top.setText(temp);

            TextView bottom = (TextView)findViewById(R.id.bottom);
            temp = data.getStringExtra("bottom");
            bottom.setText(temp);

            TextView outer = (TextView)findViewById(R.id.outer);
            temp = data.getStringExtra("outer");
            outer.setText(temp);

        }
    }

}
