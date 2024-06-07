package com.example.foodee.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;
import com.example.foodee.model.Cart;
import com.example.foodee.model.Food;
import com.example.foodee.R;
import com.example.foodee.adapter.FoodAdapter;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView rvFood;
    List<Food> lstFood;
    FoodAdapter adapter;
    SearchView searchview;
    DrawerLayout drawerLayout;
    androidx.appcompat.widget.Toolbar toolbar;
    List<String> mKeys = new ArrayList<>(); // Luu tru key tren database
    public static boolean loginStatus = false;
    ViewFlipper viewFlipper;
    public static List<Cart> arrayCart;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_Foodee);
        setContentView(R.layout.activity_main);
        if (arrayCart == null) {
            arrayCart = new ArrayList<>();
        }
        rvFood = this.findViewById(R.id.rvFood);
        getData();
        rvFood.setLayoutManager(new LinearLayoutManager(this));
        adapter = new FoodAdapter(lstFood, this);
        rvFood.setAdapter(adapter);
        registerForContextMenu(rvFood);

        setDrawer ();
        setToolbar();
        actionViewFlipper();
    }

    private void actionViewFlipper() {
        viewFlipper = (ViewFlipper) findViewById(R.id.viewFlipper);
        int banner [] = {R.drawable.banner2, R.drawable.banner5, R.drawable.banner4, R.drawable.banner1};
        for (int i = 0; i < banner.length; i++) {
            ImageView imageView = new ImageView(getApplicationContext());
            imageView.setImageResource(banner[i]);
            viewFlipper.addView(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        }
        viewFlipper.setFlipInterval(5000);
        viewFlipper.setAutoStart(true);
        Animation animationSlideIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right);
        Animation animationSlideOut = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_right);
        viewFlipper.setInAnimation(animationSlideIn);
        viewFlipper.setInAnimation(animationSlideOut);
    }

    public String getFileExtension (Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    public void getData () {
        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
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
    public void setDrawer () {
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        item.setChecked(true);
                        drawerLayout.closeDrawers();
                        switch (item.getItemId()) {
                            case R.id.nav_camera:
                                if (loginStatus == false) {
                                    login();
                                }
                                else {
                                    onClickCamera();
                                }
                                return true;
                            case R.id.nav_rate:
                                onRating ();
                                return true;
                            case R.id.nav_view:
                                return true;
                            case R.id.nav_dangnhap:
                                login ();
                                return true;
                            case R.id.nav_gallery:
                                if (loginStatus == false) {
                                    login();
                                }
                                else {
                                    getGallery ();
                                }
                                return true;
                            case R.id.nav_logout:
                                logout();
                                return true;
                        }
                        return true;
                    }
                }
        );
    }

    private static final int CAMERA_PIC_REQUEST = 1337;
    public void onClickCamera () {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
    }

    private static final int PICK_IMAGE_REQUEST = 1;
    private void getGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        FirebaseStorage mStorageRef = FirebaseStorage.getInstance();
        StorageReference _Ref = mStorageRef.getReference();
        if (requestCode == CAMERA_PIC_REQUEST) {
            Bitmap image = (Bitmap) data.getExtras().get("data");
            ImageView imageview = (ImageView) findViewById(R.id.ivProfile); //sets imageview as the bitmap
            imageview.setVisibility(View.VISIBLE);
            imageview.setImageBitmap(image);
//            Uri imUri = data.getData();
//            _Ref.child("ImageProfile/Image.png").putFile(imUri);
        }
        else if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imUri = data.getData();
            ImageView imageview = (ImageView) findViewById(R.id.ivProfile); //sets imageview as the bitmap
            imageview.setVisibility(View.VISIBLE);
            Picasso.with(this).load(imUri).into(imageview);
            _Ref.child("ImageProfile/Image.png").putFile(imUri);
        }
    }
    public void onRating () {
        Intent intent = new Intent(Intent.ACTION_VIEW,  Uri.parse("https://play.google.com/store/apps/details?id=com.foody.vn.activity"));
        startActivity(intent);
    }

    public void logout () {
        loginStatus = false;
        Toast.makeText(MainActivity.this, "Đăng xuất thành công", Toast.LENGTH_SHORT).show();
    }
    public void login () {
        if (MainActivity.loginStatus == true) {
            Toast.makeText(MainActivity.this, "Bạn đã đăng nhập rồi", Toast.LENGTH_SHORT).show();
            return;
        }
        TextView tvProfile = findViewById(R.id.tvProfile);
        tvProfile.setText("Xin chào luuduchai");
        Intent intent = new Intent(MainActivity.this, Login.class);
        startActivity(intent);
    }
    public void setToolbar () {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Đức Hải Food");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        drawerLayout= findViewById(R.id.drawer_layout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_menu_24);
        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {
            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {
            }

            @Override
            public void onDrawerStateChanged(int newState) {
            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(adapter!=null)
            adapter.release();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.optionmenu, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchview = (SearchView) menu.findItem(R.id.Search).getActionView();
        searchview.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchview.setMaxWidth(Integer.MAX_VALUE);
        searchview.setQueryHint("Nhập từ khóa để tìm kiếm");
        searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                adapter.getFilter().filter(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.Search:
                return true;
            case R.id.Contact:
                return true;
            case R.id.Telephone:
                onclickCall();
                return true;
            case R.id.Help:
                onclickHelp();
            case R.id.Facebook:
                onClickFacebook();
                return true;
            case R.id.Email:
                onClickEmail();
                return true;
            case R.id.Cart:
                Intent intent = new Intent(getApplicationContext(), com.example.foodee.activity.Cart.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void onclickHelp() {
        Uri uri = Uri.parse("https://www.foody.com.cy/page/payments_faq");
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, uri);
        if (browserIntent.resolveActivity(getPackageManager()) != null) {  startActivity(browserIntent);
        }
    }

    public void onClickEmail () {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("plain/text");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[] {
                "foodee@food.vn"
        });
        intent.putExtra(Intent.EXTRA_SUBJECT, "Phản hồi tới Foodee");
        if (intent.resolveActivity(getPackageManager()) != null)
            startActivity(Intent.createChooser(intent,"To complete action choose: "));
    }

    public void onClickFacebook () {
        Intent i = new Intent(Intent.ACTION_VIEW,
                Uri.parse("https://www.facebook.com/FoodyVietnam"));
        startActivity(i);
    }

    private void onclickCall() {
        String phoneNumberUri = "tel: 19001234";
        Intent i =  new Intent(Intent.ACTION_DIAL,  Uri.parse(phoneNumberUri));
        this.startActivity(i);
    }
}