package com.example.tmi2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private Button signin;
    private EditText email_enter, password_enter;
    private TextView signUp;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private String adminID = "admin@naver.com";
    private String adminPW = "admin1234";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        firebaseAuth = FirebaseAuth.getInstance();

//        //이미 로그인중이라면 프로필 페이지로 이동합니다.
//        if(firebaseAuth.getCurrentUser()!=null){
//            Intent intent = new Intent(getApplicationContext(), profile_page.class);
//            startActivityForResult(intent, 102);
//        }

        signin = (Button) findViewById(R.id.signin_button);
        email_enter = (EditText) findViewById(R.id.email_enter);
        password_enter = (EditText) findViewById(R.id.password_enter);
        signUp = (TextView) findViewById(R.id.textViewSignup);
        progressDialog = new ProgressDialog(this);


        //로그인 버튼에 대한 메소드
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogin();
            }
        });

        //회원이 아닐시 회원가입페이지로 넘기는 텍스트에 대한 메소드
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //회원가입 페이지를 띄웁니다.
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivityForResult(intent, 101);
            }
        });
    }

    //로그인에 해당하는 메소드.
    public void userLogin() {
        String email = email_enter.getText().toString().trim();
        String password = password_enter.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "이메일을 입력해주세요.", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "비밀번호를 입력해주세요.", Toast.LENGTH_LONG).show();
            return;
        }

        //ProgressDialog를 통해 로그인 중이라는 걸 알려줌으로써 사용자가 다른 행동을 하지 않도록 합니다.
        //이런 알림이 없다면 등록중에 뒤로가기 버튼이나 다른 행동을 함으로써 오류가 날 수 있기 때문입니다.
        progressDialog.setMessage("로그인 중...");
        progressDialog.show();

        //이메일과 패스워드를 입력받아 로그인하는 메소드
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss(); //ProgressDialog창을 내리는 메소드.
                        //로그인에 성공시
                        Intent intent;
                        if (task.isSuccessful()) {
                            //Menu 선택창으로 이동.
                            if(email.equals(adminID) && password.equals(adminPW)) {
                                intent = new Intent(getApplicationContext(), AdminActivity.class);
                            }
                            else {
                                intent = new Intent(getApplicationContext(), MenuActivity.class);
                            }
                            startActivityForResult(intent, 103);
                        } else {
                            //실패시 토스트 메시지 띄움.
                            Toast.makeText(LoginActivity.this, "로그인에 실패했습니다. 비밀번호와 이메일을 점검해주세요.", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
