package com.example.admin.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.admin.R;
import com.example.admin.adapter.FoodAdapter;
import com.example.admin.model.Food;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class ListFood extends AppCompatActivity {
    FoodAdapter adapter;
    RecyclerView rvFood;
    List<Food> lstFood;
    Toolbar toolbar;
    List<String> mKeys = new ArrayList<>(); // Luu tru key tren database
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setTheme(androidx.appcompat.R.style.Theme_AppCompat_DayNight_NoActionBar);
        setContentView(R.layout.activity);
        AnhXa ();
        getData();
        rvFood.setLayoutManager(new LinearLayoutManager(this));
        adapter = new FoodAdapter(lstFood, this);
        rvFood.setAdapter(adapter);
        registerForContextMenu(rvFood);
        setToolbar ();
    }

    public void setToolbar () {
        toolbar.setTitle("Danh sách món ăn");
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
    public void AnhXa () {
        toolbar = findViewById(R.id.toolbarAdminAdd);
        rvFood = findViewById(R.id.rvFood);
    }

    public void getData () {
//        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
//        FirebaseDatabase.getInstance("https://foodee-50b4d-default-rtdb.firebaseio.com/").getReference();
        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance("https://foodee-50b4d-default-rtdb.firebaseio.com/");
        StorageReference mStorageRef;
        DatabaseReference _myRef;
        lstFood = new ArrayList<>();
        _myRef = mDatabase.getReference("Foods");
        _myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Food fd = snapshot.getValue(Food.class);
                if (fd != null) {
                    lstFood.add(fd);
                    String key = snapshot.getKey();
                    mKeys.add(key);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Food fd = snapshot.getValue(Food.class);
                if (fd == null || lstFood == null || lstFood.isEmpty())
                    return;
                int index = mKeys.indexOf(snapshot.getKey());
                lstFood.set(index, fd);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                Food fd = snapshot.getValue(Food.class);
                if (fd == null || lstFood == null || lstFood.isEmpty())
                    return;
                int index = mKeys.indexOf(snapshot.getKey());
                if (index != -1) {
                    lstFood.remove(index);
                    mKeys.remove(index);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
