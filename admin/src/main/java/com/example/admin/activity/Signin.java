package com.example.admin.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.admin.R;

public class Signin extends AppCompatActivity {
    EditText edtAdminUsername, edtAdminPassword;
    Button btnAdminSignin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        AnhXa ();
        btnAdminSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtAdminUsername.getText().toString().trim().equals("admin") && edtAdminPassword.getText().toString().trim().equals("admin")) {
                    Toast.makeText(Signin.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Signin.this, Home.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(Signin.this, "Thông tin đăng nhập không hợp lệ", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void AnhXa () {
        edtAdminUsername = findViewById(R.id.edtAdminUsername);
        edtAdminPassword = findViewById(R.id.edtAdminPassword);
        btnAdminSignin = findViewById(R.id.btnAdminSignin);
    }
}
