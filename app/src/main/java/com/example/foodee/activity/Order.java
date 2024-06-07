package com.example.foodee.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodee.R;
import com.example.foodee.adapter.OrderAdapter;

import java.text.DecimalFormat;

public class Order extends AppCompatActivity {
    Toolbar order_toolbar;
    OrderAdapter orderAdapter;
    RecyclerView rvOrder;
    Button btnOrder;
    TextView tvTotalOrder;
    EditText edtFullName, edtTelephone, edtAddress, edtNote;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(androidx.appcompat.R.style.Theme_AppCompat_DayNight_NoActionBar);
        setContentView(R.layout.activity_order);
        AnhXa ();
        setSupportActionBar(order_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        order_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        rvOrder.setLayoutManager(new LinearLayoutManager(this));
        orderAdapter = new OrderAdapter(MainActivity.arrayCart, this);
        rvOrder.setAdapter(orderAdapter);
        registerForContextMenu(rvOrder);

        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtFullName.setHint("Nhập tên người nhận hàng");
                edtFullName.setHintTextColor(Color.GRAY);
                edtTelephone.setHint("Nhập số điện thoại");
                edtTelephone.setHintTextColor(Color.GRAY);
                edtAddress.setHint("Nhập địa chỉ giao hàng");
                edtAddress.setHintTextColor(Color.GRAY);
                if (edtFullName.getText().toString().trim().equals("")) {
                    edtFullName.setHint("Vui lòng nhập tên người nhận !");
                    edtFullName.setHintTextColor(Color.RED);
                }
                else if (edtTelephone.getText().toString().trim().equals("")) {
                    edtTelephone.setHint("Vui lòng nhập SĐT !");
                    edtTelephone.setHintTextColor(Color.RED);
                }
                else if (edtAddress.getText().toString().trim().equals("")) {
                    edtAddress.setHint("Vui lòng nhập địa chỉ nhận hàng");
                    edtAddress.setHintTextColor(Color.RED);
                }
                else {
                    Intent intent = new Intent(Order.this, CompleteOrder.class);
                    startActivity(intent);
                }
            }
        });

        long totalPrices = 0;
        for (int i = 0; i < MainActivity.arrayCart.size(); i++) {
            totalPrices += MainActivity.arrayCart.get(i).getPrice();
        }
        DecimalFormat decimalFormat1 = new DecimalFormat("###,###,###");
        tvTotalOrder.setText("Tổng: " + decimalFormat1.format(totalPrices )+ " VND");
    }

    private void AnhXa() {
        order_toolbar = findViewById(R.id.order_toolbar);
        rvOrder = findViewById(R.id.rvOrder);
        btnOrder = findViewById(R.id.btnOrder);
        tvTotalOrder = findViewById(R.id.tvTotalOrder);
        edtFullName = findViewById(R.id.edtFullName);
        edtTelephone = findViewById(R.id.edtTelephone);
        edtAddress = findViewById(R.id.edtAddress);
        edtNote = findViewById(R.id.edtNote);
    }
}