package com.example.foodee.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodee.R;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Login extends AppCompatActivity {
    EditText edtUsername, edtPassword;
    TextView tvForgorPassword;
    Button btnLogin, btnSignup;
    FirebaseDatabase mDatabase;
    public static DatabaseReference _myRefAccount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabase = FirebaseDatabase.getInstance();
                _myRefAccount = mDatabase.getReference();

                if (edtUsername.getText().toString().equals("luuduchai") && edtPassword.getText().toString().equals("12345678")){
                    Toast.makeText(Login.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                    MainActivity.loginStatus = true;
                    Intent intent = new Intent(Login.this, MainActivity.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(Login.this, "Đăng nhập thất bại. Vui lòng thử lại", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnSignup = findViewById(R.id.btnSignup);
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Signup.class);
                startActivity(intent);
            }
        });
        tvForgorPassword = findViewById(R.id.tvForgotPassword);
        tvForgorPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://id.foody.vn/tai-khoan/quen-mat-khau");
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, uri);
                if (browserIntent.resolveActivity(getPackageManager()) != null) {  startActivity(browserIntent);
                }
            }
        });
    }
}
