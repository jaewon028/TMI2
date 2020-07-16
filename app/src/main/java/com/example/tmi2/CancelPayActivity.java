package com.example.tmi2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tmi2.model.Reservation;

public class CancelPayActivity extends AppCompatActivity {

    Reservation reservation;
    TextView totalFeeText;
    EditText cardNumInput, cardPasswordInput;
    Button cancelBtn, payBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        //reservation에 있는 객체에 접근해
        reservation = (Reservation) getIntent().getSerializableExtra("reservation");
        totalFeeText = findViewById(R.id.totalFeeText);
        cardNumInput = findViewById(R.id.cardNumInputText);
        cardPasswordInput = findViewById(R.id.cardPasswordInput);
        cancelBtn = findViewById(R.id.cancel);
        payBtn = findViewById(R.id.pay);

        //결제 취소하는 메소드
        payBtn.setText("결제 취소");
        payBtn.setOnClickListener(view -> {
            //카드번호와 카드 비밀번호가 일치하면 result_ok (-1)을 반환함으로써 결체 취소 완료 설정
            if (cardNumInput.getText().toString().equals(reservation.getCardNum()) &&
                    cardPasswordInput.getText().toString().equals(reservation.getCardPassword())) {
                setResult(RESULT_OK);
                Toast.makeText(getApplicationContext(), "결제 취소 성공", Toast.LENGTH_SHORT).show();
                finish();
            } else
                Toast.makeText(getApplicationContext(), "결제 정보가 잘못되었습니다.", Toast.LENGTH_SHORT).show();
        });

        cancelBtn.setOnClickListener(view -> finish());

    }
}
