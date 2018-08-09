package org.techtown.widgettest01;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextClock;
import android.widget.TextView;

public class MyPage extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page);

        // 내 지역 변경하기
        final String Item[] = {"강남구", "강동구", "강북구", "강서구", "관악구", "광진구", "구로구", "금천구", "노원구", "도봉구", "동대문구",
                "동작구", "마포구", "서대문구", "서초구", "성동구", "성북구", "송파구", "양천구", "영등포구", "용산구", "은평구", "종로구", "중구", "중랑구"};
        final AlertDialog.Builder oDialog = new AlertDialog.Builder(this, android.R.style.Theme_DeviceDefault_Light_Dialog_Alert);
        final TextView location = (TextView) findViewById(R.id.showLocation);
        Button changeLocation = (Button) findViewById(R.id.changeLocation);

        changeLocation.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                oDialog.setTitle("내 지역 선택").setItems(Item, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        location.setText(Item[i]);
                    }
                }).setCancelable(true).show();
            }
        });

        // 지역 변경 후 변경사항 저장

        // 내가 쓴 글
    }
}
