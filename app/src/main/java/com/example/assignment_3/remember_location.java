package com.example.assignment_3;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class remember_location extends AppCompatActivity {

    EditText location , description;
    Button add;

    ImageView image_view;
    Button choose_image;

    Bitmap image_to_store;
    Uri image_path;
    dbHelper db;

    public static final int IMAGE_PICK_CODE = 1000;
    public static final int PERMISSION_CODE = 1001;
    String email_key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.remember_location);
        getSupportActionBar().hide();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            email_key = extras.getString("email");
        }
        location = findViewById(R.id.location);
        description = findViewById(R.id.description);
        db = new dbHelper(this);

        add = findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String loc = location.getText().toString();
                String des = description.getText().toString();
                boolean chk = db.storeImage(loc, des,email_key, image_to_store);
                if(chk){
                    Toast.makeText(remember_location.this, "Image Stored", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(remember_location.this, locations.class);
                    i.putExtra("email", email_key);
                    startActivity(i);
                    finish();
                }
                else {
                    Toast.makeText(remember_location.this, "Unknown Error", Toast.LENGTH_SHORT).show();
                }
            }
        });


        image_view = findViewById(R.id.image);
        choose_image = findViewById(R.id.choose_image);

        choose_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                        String permissions = (Manifest.permission.READ_EXTERNAL_STORAGE);
                        requestPermissions(new String[]{permissions}, PERMISSION_CODE);
                    }
                    else{
                        pickImageFromGallery();
                    }
                }else{
                    pickImageFromGallery();
                }
            }
        });
    }

    private void pickImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_CODE:{
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    pickImageFromGallery();
                }
                else{
                    Toast.makeText(this,"Permission denied...!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);
            if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE) {
                image_path = data.getData();
                image_view.setImageURI(image_path);
                image_to_store = MediaStore.Images.Media.getBitmap(getContentResolver(), image_path);

            }
        } catch (IOException e) {
            Toast.makeText(remember_location.this, e.toString(), Toast.LENGTH_LONG).show();
        }
    }
}
