package com.example.hostor;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DetailActivity extends AppCompatActivity {


    TextView Detail_Location, Detail_RoomType, Detail_Internet,Detail_Park,Detail_Food, Detail_Residential, Detail_Price;
    ImageView detailImage;
    String key = "";
    String imageUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        detailImage = findViewById(R.id.detailImage);

        Detail_Location = findViewById(R.id.Detail_Location);
        Detail_RoomType = findViewById(R.id.Detail_RoomType);
        Detail_Internet = findViewById(R.id.Detail_Internet);
        Detail_Park = findViewById(R.id.Detail_Park);
        Detail_Food = findViewById(R.id.Detail_Food);
        Detail_Residential = findViewById(R.id.Detail_Residential);
        Detail_Price = findViewById(R.id.Detail_Price);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            Detail_Location.setText(bundle.getString("Location"));
            Detail_RoomType.setText(bundle.getString("RoomType"));
            Detail_Internet.setText(bundle.getString("Internet"));
            Detail_Park.setText(bundle.getString("Park"));
            Detail_Food.setText(bundle.getString("Food"));
            Detail_Residential.setText(bundle.getString("Residential"));
            Detail_Price.setText(bundle.getString("Price"));

            key = bundle.getString("Key");
            imageUrl = bundle.getString("Image");
            Glide.with(this).load(bundle.getString("Image")).into(detailImage);
        }
    }
}