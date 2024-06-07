package com.example.foodee.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.foodee.R;

public class CompleteOrder extends AppCompatActivity {
    Button btnHome, btnWatchOrder;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(androidx.appcompat.R.style.Theme_AppCompat_DayNight_NoActionBar);
        setContentView(R.layout.activity_complete_order);
        MainActivity.arrayCart = null;
        AnhXa ();
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        btnWatchOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CompleteOrder.this, "Tính năng đang nâng cấp", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void AnhXa () {
        btnHome = findViewById(R.id.btnHome);
        btnWatchOrder = findViewById(R.id.btnWatchOrder);
    }
}
