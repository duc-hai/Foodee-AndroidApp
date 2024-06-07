package com.example.admin.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.admin.R;
import com.example.admin.model.Food;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

public class EditFood extends AppCompatActivity {
    ImageView ivPicFood;
    EditText edtTenmon, edtGiatien, edtThongtinTomtat, edtThongtinChitiet;
    Toolbar toolbar;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_food);
        AnhXa ();
        Bundle bundle = getIntent().getExtras();
        if (bundle == null)
            return;
        Food fd = (Food) bundle.get("object_food");
        id = fd.getId();
        edtTenmon.setText(fd.getFoodName());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        edtGiatien.setText(decimalFormat.format(fd.getPrice()));
        Picasso.with(this).load(fd.getImage()).into(ivPicFood);
        edtThongtinChitiet.setText(fd.getDetails());
        edtThongtinTomtat.setText(fd.getInfor());
        setToolbar();
    }

    public void setToolbar () {
        toolbar.setTitle("Chi tiết món ăn");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void AnhXa() {
        ivPicFood = findViewById(R.id.ivPicFood);
        edtTenmon = findViewById(R.id.edtTenmon);
        edtGiatien = findViewById(R.id.edtGiatien);
        edtThongtinTomtat = findViewById(R.id.edtThongtinTomtat);
        edtThongtinChitiet = findViewById(R.id.edtThongtinChitiet);
        toolbar = findViewById(R.id.editFood_toolbar);
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
            case R.id.editSave:
                saveEdit ();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void saveEdit() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Foods/" + id + "/foodName");
        myRef.setValue(edtTenmon.getText().toString().trim());

        DatabaseReference myRef2 = database.getReference("Foods/" + id + "/details");
        myRef2.setValue(edtThongtinChitiet.getText().toString().trim());
        DatabaseReference myRef4 = database.getReference("Foods/" + id + "/infor");
        myRef4.setValue(edtThongtinTomtat.getText().toString().trim());
        DatabaseReference myRef5 = database.getReference("Foods/" + id + "/price");
        myRef5.setValue(Integer.parseInt(edtGiatien.getText().toString().trim()));
        Toast.makeText(EditFood.this, "Lưu thông tin thành công", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(EditFood.this, ListFood.class);
        startActivity(intent);
    }
}
