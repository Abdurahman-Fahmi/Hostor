package com.example.hostor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;

import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;


import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private DatabaseReference databaseReference;
    private ValueEventListener eventListener;
    private RecyclerView recyclerView;
    private List<DataClass> dataList;
    private UserAdapter adapter;
    SearchView searchView;
    private ImageButton filter;

    Button btnApplyFilter;

    RadioGroup RG_RoomType,RG_Internet,RG_Park,RG_Food,RG_Residential;

//    RadioButton RB_Single,RB_Double,RB_Dormitory;
//    RadioButton RB_WiFi,RB_NoWiFi;
//    RadioButton RB_withParking,RB_NoPark;
//    RadioButton RB_Served,RB_NoServing;
//    RadioButton RB_Citizen,RB_Foreign,RB_Any;

    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerViewMain);
        searchView = findViewById(R.id.search);
        searchView.clearFocus();
        filter=findViewById(R.id.filter);


        GridLayoutManager gridLayoutManager = new GridLayoutManager(MainActivity.this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        RecyclerView recyclerView = findViewById(R.id.recyclerViewMain);
        int numberOfColumns = 2; // Specify the desired number of columns
        RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(numberOfColumns, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(MainActivity.this);
        builder.setCancelable(true);
        builder.setView(R.layout.progress_layout);
        androidx.appcompat.app.AlertDialog dialog = builder.create();
        dialog.show();

        dataList = new ArrayList<>();

        adapter = new UserAdapter(MainActivity.this, dataList);
        recyclerView.setAdapter(adapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("Hostor Data");
        dialog.show();
        eventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dataList.clear();
                for (DataSnapshot itemSnapshot: snapshot.getChildren()){
                    DataClass dataClass = itemSnapshot.getValue(DataClass.class);

                    dataClass.setKey(itemSnapshot.getKey());

                    dataList.add(dataClass);
                }
                adapter.notifyDataSetChanged();
                dialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                dialog.dismiss();
            }
        });

        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewDialogAdd viewDialogAdd = new ViewDialogAdd();
                viewDialogAdd.showDialog(MainActivity.this);            }
        });

        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Save65464564564d", Toast.LENGTH_SHORT).show();

            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchList(newText);
                return true;
            }
        });





        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav,
                R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        View headerView = navigationView.getHeaderView(0);
        TextView textViewUserName = headerView.findViewById(R.id.userName);
        TextView textViewUserEmail = headerView.findViewById(R.id.userEmail);
        ImageView imageViewProfile = headerView.findViewById(R.id.userImg);

        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(this);

        if(signInAccount != null) {
            textViewUserName.setText(signInAccount.getDisplayName());
            textViewUserEmail.setText(signInAccount.getEmail());
            String userPhotoUrl = String.valueOf(signInAccount.getPhotoUrl());

            Glide.with(this)
                    .load(userPhotoUrl)
                    .into(imageViewProfile);
        }
    }


    public class ViewDialogAdd {
        public void showDialog(Context context) {
            final Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(true);
            dialog.setContentView(R.layout.filter_dialog);
            RG_RoomType = dialog.findViewById(R.id.RG_RoomType);


            RG_RoomType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    RadioButton radioButton = dialog.findViewById(checkedId);
                    if (radioButton != null) {
                        String selectedOption = radioButton.getText().toString();
                        Toast.makeText(MainActivity.this, "Selected: " + selectedOption, Toast.LENGTH_SHORT).show();
                    }
                }
            });


//            EditText textName = dialog.findViewById(R.id.textName);
//            EditText textEmail = dialog.findViewById(R.id.textEmail);
//            EditText textCountry = dialog.findViewById(R.id.textCountry);


            btnApplyFilter = dialog.findViewById(R.id.btnApplyFilter);


            btnApplyFilter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });


            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();

        }
    }

    public void searchList(String text){
        ArrayList<DataClass> searchList = new ArrayList<>();
        for (DataClass dataClass: dataList){
            if (dataClass.getLocation() .toLowerCase().contains(text.toLowerCase())
            || (dataClass.getRoomType() .toLowerCase().contains(text.toLowerCase()))
            || (dataClass.getInternet() .toLowerCase().contains(text.toLowerCase()))
            || (dataClass.getPark() .toLowerCase().contains(text.toLowerCase()))
            || (dataClass.getFood() .toLowerCase().contains(text.toLowerCase()))
            || (dataClass.getResidential() .toLowerCase().contains(text.toLowerCase()))
            || (dataClass.getPrice() .toLowerCase().contains(text.toLowerCase()))
            )
            {
                searchList.add(dataClass);
            }
        }
        adapter.searchDataList(searchList);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.nav_home)
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else if (itemId == R.id.nav_account)
        {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }
        else if (itemId == R.id.nav_policy)
        {
            startActivity(new Intent(MainActivity.this, Policy.class));
        }
        else if (itemId == R.id.nav_about)
        {
            startActivity(new Intent(MainActivity.this, AboutUs.class));
        }
        else if (itemId == R.id.nav_logout)
        {
            showAlertDialog();
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            Activity currentActivity = this;
            // Navigate to the home screen or show the phone's menu
            currentActivity.moveTaskToBack(true);        }
    }


    private void showAlertDialog() {
        AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)

                .setTitle("Exit Application")
                .setCancelable(true)
                .setMessage("Are you sure you want to Exit?")

                .setNegativeButton( "No"  , new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })

                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finishAffinity();

                    }
                }).create();
        dialog.show();
    }


}