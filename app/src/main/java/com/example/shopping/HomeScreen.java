package com.example.shopping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.InputStream;
import java.util.ArrayList;

public class HomeScreen extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Button hat, shirt, trouser, shoes;

    ArrayList<Product> productlist;
    RecyclerView option;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    androidx.appcompat.widget.Toolbar toolbar;
    String usernameextra;
    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getSupportActionBar().hide();
        setContentView(R.layout.home_screen);
        Bundle extra = getIntent().getExtras();

        /*---------------------------------Hooks--------------------------------------*/
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.menuToolbar);
        /*---------------------------------Tool bar---------------------------------------*/
//        setSupportActionBar(toolbar);
        /*---------------------------------Navigation Drawer Menu--------------------------------------*/
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        /*---------------------------------Hide or Show Item--------------------------------------*/
        /*
        Menu menu = navigationView.getMenu();
        menu.findItem(R.id.profile).setVisible(false); //hide
        */
        /*---------------------------------All Category--------------------------------------*/


        hat = findViewById(R.id.hatCatagory);
        shirt = findViewById(R.id.shirtCatagory);
        trouser = findViewById(R.id.trouserCatagory);
        shoes = findViewById(R.id.shoesCatagory);
        option = findViewById(R.id.recycleOption);

        option.setHasFixedSize(true);
        option.setLayoutManager(new GridLayoutManager(HomeScreen.this, 3));
//        option.setLayoutManager(new LinearLayoutManager(this));
        usernameextra = extra.getString("user");

        getDataAndShowInRecycleView("hat");

        hat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDataAndShowInRecycleView("hat");
            }
        });

        shirt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDataAndShowInRecycleView("shirt");
            }
        });

        trouser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDataAndShowInRecycleView("Pant");
            }
        });

        shoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDataAndShowInRecycleView("shoes");
            }
        });

        navigationView.setNavigationItemSelectedListener(this);

    }

    public void getDataAndShowInRecycleView(String path){
        productlist = new ArrayList<>();
        ProductAdapter myAdapter = new ProductAdapter(productlist, new IClickIntemProductListener() {
            @Override
            public void onClickItemProduct(Product product) {
                goToDetail(product);
            }
        });
        option.setAdapter(myAdapter);
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
        myRef.child(path).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                productlist.clear();
                for (DataSnapshot data : snapshot.getChildren()){
//                          Lấy toàn bộ dữ liệu dưới dạng class Product
                    Product currentProduct = data.getValue(Product.class);
//                          set từng trường dữ liệu trong trường hợp get dữ liệu null về từ firebase database
                    currentProduct.setname(data.child("name").getValue(String.class));
                    currentProduct.setid(data.child("id").getValue(String.class));
                    currentProduct.setprice(data.child("price").getValue(Integer.class));
                    currentProduct.setimage(data.child("image").getValue(String.class));
                    currentProduct.setsize(data.child("size").getValue(String.class));

                    productlist.add(currentProduct);
//                            System.out.println(currentProduct.toString());
                }
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onBackPressed(){
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else{
            //super.onBackPressed();
//            finishAffinity();
//            finish();
            if (doubleBackToExitPressedOnce) {
                finishAffinity();
                finish();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click BACK again to exit app", Toast.LENGTH_SHORT).show();

            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce=false;
                }
            }, 2000);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.profile:
                Intent intent = new Intent(HomeScreen.this, ProfileScreen.class);
                intent.putExtra("username", usernameextra);
                startActivity(intent);
                break;
            case R.id.cart:
                Intent intent2 = new Intent(HomeScreen.this, CartScreen.class);
                intent2.putExtra("username", usernameextra);
                startActivity(intent2);
                break;
            case R.id.logout:
                Intent intent3 = new Intent(HomeScreen.this, MainActivity.class);
                startActivity(intent3);
                break;
            case R.id.order:
                Intent intent4 = new Intent(HomeScreen.this, OrderScreen.class);
                intent4.putExtra("username", usernameextra);
                startActivity(intent4);
                break;

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void goToDetail(Product product){
        Intent intent = new Intent(this, ProductDetail.class);
        intent.putExtra("product", product);
        intent.putExtra("username", usernameextra);
        startActivity(intent);
    }




    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}