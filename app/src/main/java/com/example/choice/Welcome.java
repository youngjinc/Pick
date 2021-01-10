package com.example.choice;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Welcome extends AppCompatActivity {
    private EditText et_id, et_pass;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        et_id = findViewById(R.id.editEmail);
        et_pass = findViewById(R.id.editPwd);
        final TextView textView = (TextView) findViewById(R.id.signUp);

        //가입하기 버튼 클릭시 가입페이지로 이동
        textView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                textView.setTextColor(ContextCompat.getColor(Welcome.this, R.color.purple));
                Intent Signup1intent = new Intent(getApplicationContext(), Signup1.class);
                Welcome.this.startActivity(Signup1intent);
            }
        });
    }

    //로그인 버튼 클릭 시 수행
    public void onClick(View v){

        String email = et_id.getText().toString();
        String pw = et_pass.getText().toString();

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");
                    if (success) { // 로그인에 성공한 경우
                        String email = jsonObject.getString("email");
                        String pw = jsonObject.getString("pw");

                        Toast.makeText(getApplicationContext(),"로그인에 성공하였습니다.",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Welcome.this, MainActivity.class);
                        startActivity(intent);

                        //자동 로그인을 위한 정보 저장 (테스트 할때는 주석처리하는 것을 추천)
                        SaveSharedPreferences.setUserEmail(Welcome.this, email);
                        SaveSharedPreferences.setUserPwd(Welcome.this, pw);

                    } else { // 로그인에 실패한 경우
                        Toast.makeText(getApplicationContext(),"로그인에 실패하였습니다.",Toast.LENGTH_SHORT).show();
                        return;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        LoginRequest loginRequest = new LoginRequest(email, pw, responseListener);
        RequestQueue queue = Volley.newRequestQueue(Welcome.this);
        queue.add(loginRequest);
    }
}
