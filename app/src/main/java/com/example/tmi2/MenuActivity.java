package com.example.tmi2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MenuActivity extends AppCompatActivity {
    // 메뉴 이동을 위한 클래스.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void onButtonReservationClicked(View view) {
        Toast.makeText(this, "버스 예매 화면입니다. 버스 탑승을 위한 정보를 입력해주세요.", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, ReservationActivity.class);
        startActivity(intent);
    }

    public void onButtonChecknCancelClicked(View view) {
        Toast.makeText(this, "예매한 버스 정보 조회 화면입니다.", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, CheckCancelActivity.class);
        startActivity(intent);
    }
}
