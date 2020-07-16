package com.example.tmi2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class AdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        Toast.makeText(this, "This is an AdminActivity Page.", Toast.LENGTH_SHORT).show();
    }

    public void onButtonTestClicked(View view) {
        Toast.makeText(this, "Test화면입니다.", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, test.class);
        startActivity(intent);
    }
}
