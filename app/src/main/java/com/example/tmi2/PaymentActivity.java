package com.example.tmi2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tmi2.model.Reservation;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PaymentActivity extends AppCompatActivity {
    Reservation reservation;
    int fee = 15000;
    int totalFee;
    TextView totalFeeText;
    EditText cardNumInput, cardPasswordInput;
    Button cancelBtn, payBtn;

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        totalFeeText = findViewById(R.id.totalFeeText);
        cardNumInput = findViewById(R.id.cardNumInputText);
        cardPasswordInput = findViewById(R.id.cardPasswordInput);
        cancelBtn = findViewById(R.id.cancel);
        payBtn = findViewById(R.id.pay);

        reservation = (Reservation) getIntent().getSerializableExtra("reservation");
        totalFee = fee * reservation.getPeopleNum();
        reservation.setFee(totalFee);
        totalFeeText.setText(totalFeeText.getText().toString() + totalFee);

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        payBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cardNumInput.getText().toString().length() == 16 && cardPasswordInput.getText().toString().length() == 4) {
                    reservation.setCardNum(cardNumInput.getText().toString());
                    reservation.setCardPassword(cardPasswordInput.getText().toString());
                    mDatabase.child("reservations").child(reservation.getUid()).child(reservation.getKey()).setValue(reservation)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(getApplicationContext(), "예매 성공", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    e.printStackTrace();
                                }
                            });
                } else {
                    Toast.makeText(getApplicationContext(), "결제 정보를 확인해주세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}
