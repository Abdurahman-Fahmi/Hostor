package com.example.hostor;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.navigation.NavigationView;



public class AboutUs extends AppCompatActivity {

    private DrawerLayout drawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);


        Toolbar toolbar = findViewById(R.id.toolbar); //Ignore red line errors
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this::onNavigationItemSelected);

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

    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.nav_home) {
            startActivity(new Intent(AboutUs.this, MainActivity.class));
        } else if (itemId == R.id.nav_account) {
            startActivity(new Intent(AboutUs.this, LoginActivity.class));
        } else if (itemId == R.id.nav_policy) {
            startActivity(new Intent(AboutUs.this, Policy.class));
        } else if (itemId == R.id.nav_about) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else if (itemId == R.id.nav_logout) {
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
            startActivity(new Intent(AboutUs.this, MainActivity.class));
            finish();
        }
    }


    private void showAlertDialog() {
        AlertDialog dialog = new AlertDialog.Builder(AboutUs.this)
                .setTitle("Exit Application")
                .setCancelable(true)
                .setMessage("Are you sure you want to Exit?")
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
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


