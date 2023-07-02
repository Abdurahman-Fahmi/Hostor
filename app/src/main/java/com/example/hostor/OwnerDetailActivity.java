package com.example.hostor;


import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.clans.fab.FloatingActionButton;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class OwnerDetailActivity extends AppCompatActivity {


    TextView detailOwnerLocation, detailOwnerRoomType, detailOwnerInternet,detailOwnerPark,detailOwnerFood, detailOwnerResidential,detailOwnerPrice;
    ImageView detailImage;
    FloatingActionButton deleteButton, editButton;
    String key = "";
    String imageUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_detail);

        detailImage = findViewById(R.id.detailOwnerImage);
        detailOwnerLocation = findViewById(R.id.detailOwnerLocation);
        detailOwnerRoomType = findViewById(R.id.detailOwnerRoomType);
        detailOwnerInternet = findViewById(R.id.detailOwnerInternet);
        detailOwnerPark = findViewById(R.id.detailOwnerPark);
        detailOwnerFood = findViewById(R.id.detailOwnerFood);
        detailOwnerResidential = findViewById(R.id.detailOwnerResidential);
        detailOwnerPrice = findViewById(R.id.detailOwnerPrice);

        deleteButton = findViewById(R.id.deleteButton);
        editButton = findViewById(R.id.editButton);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            detailOwnerLocation.setText(bundle.getString("Location"));
            detailOwnerRoomType.setText(bundle.getString("RoomType"));
            detailOwnerInternet.setText(bundle.getString("Internet"));
            detailOwnerPark.setText(bundle.getString("Park"));
            detailOwnerFood.setText(bundle.getString("Food"));
            detailOwnerResidential.setText(bundle.getString("Residential"));
            detailOwnerPrice.setText(bundle.getString("Price"));


            key = bundle.getString("Key");
            imageUrl = bundle.getString("Image");
            Glide.with(this).load(bundle.getString("Image")).into(detailImage);
        }
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeletingAlertDialog();
            }
        });
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OwnerDetailActivity.this, UpdateActivity.class)
                        .putExtra("Location", detailOwnerLocation.getText().toString())
                        .putExtra("Price", detailOwnerPrice.getText().toString())
                        //.putExtra("Language", detailLang.getText().toString())
                        .putExtra("Image", imageUrl)
                        .putExtra("Key", key);
                startActivity(intent);
            }
        });
    }

    private void DeletingAlertDialog() {
        AlertDialog dialog = new AlertDialog.Builder(OwnerDetailActivity.this)

                .setTitle("Delete Record")
                .setCancelable(true)
                .setMessage("Deleted Record cannot be recovered.\nConfirm Deleting?")

                .setNegativeButton( "No"  , new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })

                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Hostor Data");
                        FirebaseStorage storage = FirebaseStorage.getInstance();

                        StorageReference storageReference = storage.getReferenceFromUrl(imageUrl);
                        storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                reference.child(key).removeValue();
                                Toast.makeText(OwnerDetailActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), HostelOwnerAccount.class));
                                finish();
                            }
                        });

                    }
                }).create();
        dialog.show();
    }
}