package org.techtown.widgettest01;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import static android.graphics.Color.parseColor;

public class popup_weather extends AppCompatActivity {
    int t = -1;
    int h = -1;
    int w = -1;
    final ImageButton imageButton[] = new ImageButton[9];
    final String heat[] = {"더움", "짱더움", "짱짱더움"};
    final String cold[] = {"추움", "짱추움", "짱짱추움"};
    final String temperatureInfo[] = new String[heat.length];
    final String humidityInfo[] = {"건조", "뽀송", "습해"};
    final String windInfo[] = {"바람", "바람바람", "바람바람바람"};

    public void loop(ImageButton[] ibs, int i){
        /*
          0 - 전체 돌림
          1 - temperature
          2 - humidity
          3 - wind
         */
        int idx;
        int end;
        int why;
        if(i == 0){
            why = 0;
            end = ibs.length;
        } else {
            why = (i - 1) * 3;
            end = i * 3;
        }
        for(idx = why; idx < end; idx++){
            ibs[idx].setColorFilter(parseColor("#BDBDBD"), PorterDuff.Mode.SRC_ATOP);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_popup_weather);

        String serverTemperatrue = "heat";
        if(serverTemperatrue.equals("heat")){
            for(int i = 0; i < heat.length; i++){
                temperatureInfo[i] = heat[i];
            }
        } else if(serverTemperatrue.equals("cold")){
            for(int i = 0; i < cold.length; i++){
                temperatureInfo[i] = cold[i];
            }
        }

        LinearLayout popupButtons = (LinearLayout) findViewById(R.id.popupButtons); // 전체 레이아웃
        int idx = 0; // 이미지버튼 배열 인덱스
        for(int i = 0; i < popupButtons.getChildCount(); i++){
            View v = popupButtons.getChildAt(i);

            if(v instanceof LinearLayout){ // 그 중에서 Linear layout 고름
                LinearLayout layouts = (LinearLayout) v;
                for(int j = 0; j < layouts.getChildCount(); j++){
                    View v2 = layouts.getChildAt(j);
                    if(v2 instanceof ImageView){ // 각 Linear layout 에서 imageView만 가져옴
                        int id = v2.getId();
                        ImageButton ib = (ImageButton) findViewById(id);
                        ib.setOnClickListener(listener); // listener 달기
                        ib.setColorFilter(parseColor("#BDBDBD"), PorterDuff.Mode.SRC_ATOP); // 버튼 회색으로 초기화
                        imageButton[idx] = ib;
                        idx++;
                    }
                }
            }
        }
    }

    Button.OnClickListener listener = new Button.OnClickListener() {
        public void onClick(View v) {
            final Intent intent = new Intent();
            int id = v.getId();
//            if(t >= 0 || h >= 0 || w >= 0){
//                imageButton[t + 0].setColorFilter(null);
//                imageButton[h + 3].setColorFilter(null);
//                imageButton[w + 6].setColorFilter(null);
//            } 다시 날씨 선택할때 기존에 있던거 그대로 있었으면 좋겠지만 실패......

            switch (id) {
                /*
                    각 이미지 버튼이 눌렸을때
                    단계 설정 (0, 1, 2단계)
                    해당 카테고리 전체 회색 처리 후 (상의는 1, 하의는 2, 겉옷은 3)
                    선택된 이미지 버튼만 색 넣어주기
                 */
                case R.id.imageButton1:
                    t = 0;
                    loop(imageButton, 1);
                    imageButton[t + 0].setColorFilter(null);
                    break;
                case R.id.imageButton2:
                    t = 1;
                    loop(imageButton, 1);
                    imageButton[t + 0].setColorFilter(null);
                    break;
                case R.id.imageButton3:
                    t = 2;
                    loop(imageButton, 1);
                    imageButton[t + 0].setColorFilter(null);
                    break;
                ////////////////////////////////
                case R.id.imageButton4:
                    h = 0;
                    loop(imageButton, 2);
                    imageButton[h + 3].setColorFilter(null);
                    break;
                case R.id.imageButton5:
                    h = 1;
                    loop(imageButton, 2);
                    imageButton[h + 3].setColorFilter(null);
                    break;
                case R.id.imageButton6:
                    h = 2;
                    loop(imageButton, 2);
                    imageButton[h + 3].setColorFilter(null);
                    break;
                ////////////////////////////////
                case R.id.imageButton7:
                    w = 0;
                    loop(imageButton, 3);
                    imageButton[w + 6].setColorFilter(null);
                    break;
                case R.id.imageButton8:
                    w = 1;
                    loop(imageButton, 3);
                    imageButton[w + 6].setColorFilter(null);
                    break;
                case R.id.imageButton9:
                    w = 2;
                    loop(imageButton, 3);
                    imageButton[w + 6].setColorFilter(null);
                    break;

                default:
            }

            String tStr, hStr, wStr;
            if(t >= 0){
                tStr = temperatureInfo[t];
            } else {
                tStr = "";
            }

            if (h >= 0) {
                hStr = humidityInfo[h];
            } else {
                hStr = "";
            }

            if (w >= 0) {
                wStr = windInfo[w];
            } else {
                wStr = "";
            }
            intent.putExtra("temperature", tStr);
            intent.putExtra("humidity", hStr);
            intent.putExtra("wind", wStr);

            setResult(100, intent);
        }
    };

    public void popupClose(View v) {
        finish();
    }
}

