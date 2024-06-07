package com.example.admin.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.example.admin.R;

public class Home extends AppCompatActivity {
    Button btnAdminList, btnAdminAdd, btnAdminOrder, btnMessage, btnAdminLogout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        AnhXa ();
        btnAdminList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, ListFood.class);
                startActivity(intent);
            }
        });

        btnAdminLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this, Signin.class);
                startActivity(intent);
            }
        });
    }

    public void AnhXa () {
        btnAdminList = findViewById(R.id.btnAdminList);
        btnAdminAdd = findViewById(R.id.btnAdminAdd);
        btnAdminOrder = findViewById(R.id.btnAdminOrder);
        btnMessage = findViewById(R.id.btnMessage);
        btnAdminLogout = (Button) findViewById(R.id.btnAdminLogout);
    }
}
