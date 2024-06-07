package com.example.foodee.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodee.model.Cart;
import com.example.foodee.model.Food;
import com.example.foodee.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

public class DetailFoodActivity extends AppCompatActivity {
    Toolbar detail_toolbar;
    TextView  tvTenmon, tvGiatien, tvDetailFood;
    ImageView ivMonan;
    Spinner spinner;
    Button btnAddCart;

    int price;
    String image;
    String foodName, details;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_food);
        AnhXa ();
        SetToolbar ();

        Bundle bundle = getIntent().getExtras();
        if (bundle == null)
            return;
        Food fd = (Food) bundle.get("object_food");

        foodName = fd.getFoodName();
        tvTenmon.setText(foodName);

        price = fd.getPrice();
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        tvGiatien.setText("Giá tiền: " + decimalFormat.format(price) + " VND");
        image = fd.getImage();
        Picasso.with(this).load(image).into(ivMonan);

        details = fd.getDetails();
        tvDetailFood.setText(details);

        catchSpinner ();
        eventButton ();
    }

    private void eventButton() {
        btnAddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (Cart c : MainActivity.arrayCart) {
                    if (c.getFoodName().equals(foodName)) {
                        int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
                        if (c.getAmount() == 20) {
                            Toast.makeText(getApplicationContext(), "Món " + foodName + " trong giỏ hàng đã đầy", Toast.LENGTH_LONG).show();
                            return;
                        }
                        else if (c.getAmount() + soluong > 20){
                            Toast.makeText(getApplicationContext(), "Số lượng món lớn hơn mức cho phép", Toast.LENGTH_LONG).show();
                            return;
                        }
                    }
                }
                int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
                boolean check = false;
                for (int i = 0; i < MainActivity.arrayCart.size(); i++) {
                    if (MainActivity.arrayCart.get(i).getFoodName().equals(foodName)) {
                        int oriPrice = MainActivity.arrayCart.get(i).getPrice() / MainActivity.arrayCart.get(i).getAmount();
                        int newAmount = MainActivity.arrayCart.get(i).getAmount() + soluong;
                        MainActivity.arrayCart.get(i).setAmount(newAmount);
                        MainActivity.arrayCart.get(i).setPrice(oriPrice * newAmount);
                        check = true;
                    }
                }
                if (check == false) {
                    int newPrice = soluong * price;
                    MainActivity.arrayCart.add(new Cart(foodName, image, soluong, newPrice));
                }
                Intent intent = new Intent(getApplicationContext(), com.example.foodee.activity.Cart.class);
                startActivity(intent);
            }
        });
    }

    private void catchSpinner() {
        Integer [] soluong = new Integer [] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_dropdown_item, soluong);
        spinner.setAdapter(arrayAdapter);
    }

    private void SetToolbar() {
        setSupportActionBar(detail_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        detail_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Location:
                onCLickLocation ();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onCLickLocation () {
        Uri gmnIntentUri = Uri.parse("geo:10.017849,105.767423?q=Nhà hàng Foody");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmnIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }
    private void AnhXa() {
        tvTenmon = findViewById(R.id.tvTenmon);
        tvGiatien = findViewById(R.id.tvGiatien);
        tvDetailFood = findViewById(R.id.tvDetailFood);
        ivMonan = findViewById(R.id.ivMonan);
        spinner = findViewById(R.id.spinner);
        btnAddCart = findViewById(R.id.btnAddCart);
        detail_toolbar = findViewById(R.id.Detail_toolbar);
    }
}