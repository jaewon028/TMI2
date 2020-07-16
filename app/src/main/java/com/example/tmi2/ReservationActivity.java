package com.example.tmi2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.tmi2.model.Reservation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class ReservationActivity extends AppCompatActivity implements View.OnClickListener {

    private int mYear, mMonth, mDay, mHour, mMinute;
    private TextView mTxtDate;
    private TextView mTxtTime;
    private Button submitBtn;

    private DatabaseReference rootRef;
    private DatabaseReference getDataChild;
    private Button btn_submit;
    private String getDept;
    private CheckBox c1, c2, c3, c4;
    private TextView tv1;
    private String a = "";
    private int CHECK_LIMIT;
    private int selectCount;
    private String dest;
    private String dept;
    private int fee = 15000;
    private DatabaseReference mDatabase;

    //버스예매를 위한 클래스
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        final String[] destList = getResources().getStringArray(R.array.destination);
        c1 = (CheckBox) findViewById(R.id.checkBox1);
        c2 = (CheckBox) findViewById(R.id.checkBox2);
        c3 = (CheckBox) findViewById(R.id.checkBox3);
        c4 = (CheckBox) findViewById(R.id.checkBox4);

        submitBtn = findViewById(R.id.btnSubmit);
        submitBtn.setOnClickListener(this);

        //----------------------출발지 목적지 spinner-------------------------
        //Spinner(출발지와 목적지 spinner layout설정)
        Spinner deptSpinner = (Spinner) findViewById(R.id.spinnerDeparture);
        ArrayAdapter deptAdapter = ArrayAdapter.createFromResource(this,
                R.array.departure, android.R.layout.simple_spinner_item);
        deptAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        deptSpinner.setAdapter(deptAdapter);
        dept = deptSpinner.getSelectedItem().toString();
        deptSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                dept = destList[i];
                checkBus();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Spinner destSpinner = (Spinner) findViewById(R.id.spinnerDestination);
        ArrayAdapter destAdapter = ArrayAdapter.createFromResource(this,
                R.array.destination, android.R.layout.simple_spinner_item);
        destAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        destSpinner.setAdapter(destAdapter);

        destSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                dest = destList[i];
                checkBus();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        dest = destSpinner.getSelectedItem().toString();
        // 선택된 출발지 get
//        getDept = destSpinner.getSelectedItem().toString();
//        addDept(getDept);

        //-----------------------------------날짜와 시간---------------------------------
        mTxtDate = (TextView) findViewById(R.id.txtdate);
        mTxtTime = (TextView) findViewById(R.id.txttime);


        //현재 날짜와 시간을 가져오기위한 Calendar 인스턴스 선언
        Calendar cal = new GregorianCalendar();
        mYear = cal.get(Calendar.YEAR);
        mMonth = cal.get(Calendar.MONTH);
        mDay = cal.get(Calendar.DAY_OF_MONTH);
        mHour = cal.get(Calendar.HOUR_OF_DAY);
        mMinute = cal.get(Calendar.MINUTE);

        UpdateNow();//화면에 텍스트뷰에 업데이트 해줌.


        //-----------------------인원수 spinner--------------------------------
        Spinner numpplSpinner = (Spinner) findViewById(R.id.spinnerNumppl);
        ArrayAdapter numpplAdapter = ArrayAdapter.createFromResource(this,
                R.array.numPeople, android.R.layout.simple_spinner_item);
        numpplAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        numpplSpinner.setAdapter(numpplAdapter);

        numpplSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                CHECK_LIMIT = i + 1;
                setChecker();
                checkBus();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        //--------------------------좌석 선택(checkbox)-----------------------------

        c1.setText("좌석1");
        c2.setText("좌석2");
        c3.setText("좌석3");
        c4.setText("좌석4");

        tv1 = (TextView) findViewById(R.id.textView1);

        Button btnSeatResult = (Button) findViewById(R.id.btnSeatResult);
        btnSeatResult.setText("결과보기");
        btnSeatResult.setOnClickListener(this);

    }

    //좌석 c1, c2, c3, c4에 대한 체크박스 체크여부 확인 method
    void setChecker() {
        CompoundButton.OnCheckedChangeListener checker = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b && selectCount != CHECK_LIMIT) {
                    selectCount++;
                } else if (!b) {
                    selectCount--;
                } else if (selectCount == CHECK_LIMIT) {
                    compoundButton.setChecked(false);
                    Toast.makeText(ReservationActivity.this, "인원을 확인해주세요.", Toast.LENGTH_SHORT).show();
                }
            }
        };
        c1.setOnCheckedChangeListener(checker);
        c2.setOnCheckedChangeListener(checker);
        c3.setOnCheckedChangeListener(checker);
        c4.setOnCheckedChangeListener(checker);
    }

    public void mOnClick(View v) {
        switch (v.getId()) {
            //날짜 대화상자 버튼이 눌리면 대화상자를 보여줌
            case R.id.btnchangedate:
                //여기서 리스너도 등록함
                new DatePickerDialog(ReservationActivity.this, mDateSetListener, mYear, mMonth, mDay).show();
                break;
            //시간 대화상자 버튼이 눌리면 대화상자를 보여줌
            case R.id.btnchangetime:
                //여기서 리스너도 등록함
                new TimePickerDialog(ReservationActivity.this, mTimeSetListener, mHour,
                        mMinute, false).show();
                break;

        }

    }


    //날짜 대화상자 리스너 부분

    DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    //사용자가 입력한 값을 가져온뒤
                    mYear = year;
                    mMonth = monthOfYear;
                    mDay = dayOfMonth;
                    //텍스트뷰의 값을 업데이트함
                    UpdateNow();
                    checkBus();
                }
            };


    //시간 대화상자 리스너 부분
    TimePickerDialog.OnTimeSetListener mTimeSetListener =
            new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    //사용자가 입력한 값을 가져온뒤
                    mHour = hourOfDay;
                    mMinute = minute;
                    //텍스트뷰의 값을 업데이트함
                    UpdateNow();
                    checkBus();
                }
            };


    //텍스트뷰의 값을 업데이트 하는 메소드
    void UpdateNow() {
        mTxtDate.setText(String.format("%d/%d/%d", mYear, mMonth + 1, mDay));
        mTxtTime.setText(String.format("%d:%d", mHour, mMinute));
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSeatResult:
                tv1.setText("총 요금 : " + fee * CHECK_LIMIT);
                break;
            case R.id.btnSubmit:
                String day = mYear + "/" + mMonth + "/" + mDay;
                String time = mHour + ":" + mMinute;
                String uid = FirebaseAuth.getInstance().getUid();
                ArrayList<Integer> seat = new ArrayList<>();

                if (c1.isChecked()) seat.add(1);
                if (c2.isChecked()) seat.add(2);
                if (c3.isChecked()) seat.add(3);
                if (c4.isChecked()) seat.add(4);

                if (seat.size() != CHECK_LIMIT) {
                    Toast.makeText(getApplicationContext(), "인원을 확인해주세요.", Toast.LENGTH_SHORT).show();
                } else if (dest.equals(dept)) {
                    Toast.makeText(getApplicationContext(), "목적지를 확인해주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    Reservation reservation = new Reservation(seat, dept, dest, uid, day, time, CHECK_LIMIT);
                    reservation.setKey(mDatabase.push().getKey());


                    Intent intent = new Intent(ReservationActivity.this, PaymentActivity.class);
                    intent.putExtra("reservation", reservation);
                    startActivity(intent);
                    finish();

                }

        }

    }

    //매진
    //compareTo 결과 같으면 0
    void checkBus() {
        c1.setChecked(false);
        c2.setChecked(false);
        c3.setChecked(false);
        c4.setChecked(false);


        selectCount = 0;
//        Toast.makeText(this, "compare 결과: " + dest.compareTo(dept), Toast.LENGTH_LONG).show();
        if(dest.compareTo(dept) == 0) {
            Toast.makeText(this, "목적지와 출발지가 같습니다.\n목적지와 출발지를 다시 설정해주세요.", Toast.LENGTH_SHORT).show();
        }
        if (dest.equals("부산") && dept.equals("서울") && mHour == 11) {
            submitBtn.setClickable(false);
            CHECK_LIMIT = 0;
            Toast.makeText(getApplicationContext(), "매진되었습니다.\n다른 버스를 선택해주세요.", Toast.LENGTH_SHORT).show();
        } else {
            submitBtn.setClickable(true);
        }
    }

}
