package com.example.foodee.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodee.R;
import com.example.foodee.adapter.CartAdapter;
import com.example.foodee.adapter.FoodAdapter;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class Cart extends AppCompatActivity {
    Button btnContinue, btnPayCart;
    public static CartAdapter cartAdapter;
    RecyclerView rvCart;
    static TextView tvTotalPrice;
    public static TextView tvEmpty;
    Toolbar car_toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(androidx.appcompat.R.style.Theme_AppCompat_DayNight_NoActionBar);
        setContentView(R.layout.activity_cart);

        car_toolbar = findViewById(R.id.car_toolbar);
        setSupportActionBar(car_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        car_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tvEmpty = findViewById(R.id.tvEmpty);
        tvTotalPrice = findViewById(R.id.tvTotalPrice);
        btnContinue = findViewById(R.id.btnContinue);
        btnPayCart = findViewById(R.id.btnPayCart);
        rvCart = findViewById(R.id.rvCart);
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Cart.this, MainActivity.class);
                startActivity(intent);
            }
        });
        btnPayCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.arrayCart.size() <= 0){
                    Toast.makeText(Cart.this, "Giỏ hàng của bạn đang rỗng. Vui lòng mua hàng và quay lại nhé !", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent = new Intent(getApplicationContext(), Order.class);
                    startActivity(intent);
                }
            }
        });

        rvCart.setLayoutManager(new LinearLayoutManager(this));
        cartAdapter = new CartAdapter(MainActivity.arrayCart, this);
        rvCart.setAdapter(cartAdapter);
        registerForContextMenu(rvCart);
        checkData ();
        setTotalPrice();
    }

    private void checkData() {
        if (MainActivity.arrayCart.size() <= 0) {
            tvEmpty.setVisibility(View.VISIBLE);
            rvCart.setVisibility(View.INVISIBLE);
        }
        else {
            tvEmpty.setVisibility(View.INVISIBLE);
            rvCart.setVisibility(View.VISIBLE);
        }
    }

    public static void setTotalPrice () {
        long totalPrices = 0;
        for (int i = 0; i < MainActivity.arrayCart.size(); i++) {
            totalPrices += MainActivity.arrayCart.get(i).getPrice();
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        tvTotalPrice.setText("Tổng tiền: " + decimalFormat.format(totalPrices )+ " VND");
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(cartAdapter!=null)
            cartAdapter.release();
    }

}