package com.example.foodee.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.foodee.R;
import com.example.foodee.model.Account;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Signup extends AppCompatActivity {
    EditText edtUsername, edtPassword, edtConfirmPassword;
    Button btnSignup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        edtConfirmPassword = findViewById(R.id.edtConfirmPassword);
        btnSignup = findViewById(R.id.btnSignup);
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtPassword.getText().toString().compareTo(edtConfirmPassword.getText().toString()) != 0){
                    Toast.makeText(Signup.this, "Mật khẩu xác nhận không hợp lệ, vui lòng thử lại", Toast.LENGTH_SHORT).show();
                }
                else if (edtPassword.getText().toString().trim().length() < 8) {
                    Toast.makeText(Signup.this, "Mật khẩu phải lớn hơn 8 kí tự", Toast.LENGTH_SHORT).show();
                }
                else {
                    upLoadToFirebase();
                    Toast.makeText(Signup.this, "Đăng ký tài khoản thành công", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Signup.this, Login.class);
                    startActivity(intent);
                }
            }
        });
    }

    public void upLoadToFirebase (){
        Login._myRefAccount = FirebaseDatabase.getInstance("https://foodee-50b4d-default-rtdb.firebaseio.com/").getReference();
        Account account = new Account(edtUsername.getText().toString().trim(), edtPassword.getText().toString().trim());
//        mDatabase.child("Accounts").push().child("username").setValue(edtUsername.getText().toString());
//        mDatabase.child("Accounts").push().child("password").setValue(edtPassword.getText().toString());
        Login._myRefAccount.child("Accounts").push().setValue(account);
    }
}
