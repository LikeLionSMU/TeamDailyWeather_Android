package org.techtown.widgettest01;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import static android.graphics.Color.parseColor;

public class popup_clothes extends AppCompatActivity {
    int top = -1;
    int bottom = -1;
    int outer = -1;
    final String[] top_items = {"민소매", "반팔", "n부", "긴팔"};
    final String[] bottom_items = {"반바지", "긴바지", "짧은치마", "긴치마"};
    final String[] outer_items = {"가디건", "자켓", "패딩", "코트", "없음"};
    final ImageButton imageButton[] = new ImageButton[top_items.length + bottom_items.length + outer_items.length];

    public void loop(ImageButton[] ibs, int i){
        /*
          0 - 전체 돌림
          1 - top
          2 - bottom
          3 - outer
         */
        int idx;
        int end;
        int why;
        if(i == 0){
            why = 0;
            end = ibs.length;
        } else if(i == 1){
            why = 0;
            end = 4;
        } else if(i == 2){
            why = 4;
            end = 8;
        } else if(i == 3){
            why = 8;
            end = 13;
        } else {
            why = 0;
            end = 0;
        }
        for(idx = why; idx < end; idx++){
            ibs[idx].setColorFilter(parseColor("#BDBDBD"), PorterDuff.Mode.SRC_ATOP);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_popup_clothes);

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
            switch (id) {
                /*
                    각 이미지 버튼이 눌렸을때
                    단계 설정 (0, 1, 2단계)
                    해당 카테고리 전체 회색 처리 후 (더위는 1, 습도는 2, 풍량은 3)
                    선택된 이미지 버튼만 색 넣어주기
                 */
                case R.id.tankTop:
                    top = 0;
                    loop(imageButton, 1);
                    imageButton[top + 0].setColorFilter(null);
                    break;
                case R.id.shortSleeve:
                    top = 1;
                    loop(imageButton, 1);
                    imageButton[top + 0].setColorFilter(null);
                    break;
                case R.id.imageButton3:
                    top = 2;
                    loop(imageButton, 1);
                    imageButton[top + 0].setColorFilter(null);
                    break;
                case R.id.longSleeve:
                    top = 3;
                    loop(imageButton, 1);
                    imageButton[top + 0].setColorFilter(null);
                    break;
                ////////////////////////////////
                case R.id.shortPants:
                    bottom = 0;
                    loop(imageButton, 2);
                    imageButton[bottom + 4].setColorFilter(null);
                    break;
                case R.id.longPants:
                    bottom = 1;
                    loop(imageButton, 2);
                    imageButton[bottom + 4].setColorFilter(null);
                    break;
                case R.id.miniskirt:
                    bottom = 2;
                    loop(imageButton, 2);
                    imageButton[bottom + 4].setColorFilter(null);
                    break;
                case R.id.longSkirt:
                    bottom = 3;
                    loop(imageButton, 2);
                    imageButton[bottom + 4].setColorFilter(null);
                    break;
                ////////////////////////////////
                case R.id.cardigan:
                    outer = 0;
                    loop(imageButton, 3);
                    imageButton[outer + 8].setColorFilter(null);
                    break;
                case R.id.jacket:
                    outer = 1;
                    loop(imageButton, 3);
                    imageButton[outer + 8].setColorFilter(null);
                    break;
                case R.id.padding:
                    outer = 2;
                    loop(imageButton, 3);
                    imageButton[outer + 8].setColorFilter(null);
                    break;
                case R.id.coat:
                    outer = 3;
                    loop(imageButton, 3);
                    imageButton[outer + 8].setColorFilter(null);
                    break;
                case R.id.none:
                    outer = 4;
                    loop(imageButton, 3);
                    imageButton[outer + 8].setColorFilter(null);
                    break;
                default:
            }

            String topStr, bottomStr, outerStr;
            if(top >= 0){
                topStr = top_items[top];
            } else {
                topStr = "";
            }

            if (bottom >= 0) {
                bottomStr = bottom_items[bottom];
            } else {
                bottomStr = "";
            }

            if (outer >= 0) {
                outerStr = outer_items[outer];
            } else {
                outerStr = "";
            }
            intent.putExtra("top", topStr);
            intent.putExtra("bottom", bottomStr);
            intent.putExtra("outer", outerStr);
            setResult(200, intent);
        }
    };

    public void popupClose(View v){
        finish();
    }
}
