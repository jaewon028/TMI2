package com.example.tmi2;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class test extends AppCompatActivity {
    Random Number;
    String Rnumber = " ";

    private TextView[]txt;  // single dimensional array

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        Button btnFirst = (Button)findViewById(R.id.btnFirstClass);

        btnFirst.setOnClickListener(new View.OnClickListener() {
            LinearLayout layout1 = (LinearLayout) findViewById(R.id.L1); // get the reference
            @Override
            public void onClick(View v) {
                //  String[] a = new String[5];
                TextView result = (TextView)findViewById(R.id.txtResult);
                txt = new TextView[5];
                Number = new Random();
                Rnumber = "0" + (Number.nextInt(5) +1);
                for (int i = 0 ; i < layout1.getChildCount() ; i++) { //getChildCount() returns the # of Obj
                    txt[i] = (TextView) layout1.getChildAt(i);
                    ColorDrawable cd = (ColorDrawable)txt[i].getBackground();
                    if(Rnumber.equals(txt[i].getText()))
                    {
                        if(cd.getColor() == Color.RED)
                        {
                            Toast.makeText(test.this, txt[i].getText() + "this seat is booked", Toast.LENGTH_SHORT).show();
                            result.setText(Rnumber + "this seat is booked. click again");
                            break;
                        }
                        else
                        {
                            txt[i].setBackgroundColor(Color.RED);
                            result.setText("your seat is" + Rnumber);
                        }
                    }
                    //     Toast.makeText(MainActivity.this, txt[i].getText() + " and " + Rnumber,Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
