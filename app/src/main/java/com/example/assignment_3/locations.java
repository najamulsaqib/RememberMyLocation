package com.example.assignment_3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class locations extends AppCompatActivity {

    dbHelper db;
    String email_key;
    FloatingActionButton floatingActionButton;
    ArrayList<String> loc, desc;
    ArrayList<byte[]> img;
    RecyclerView recyclerView;
    CustomAdapter customAdapter;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locations);
        getSupportActionBar().hide();

//        textView = findViewById(R.id.textView);
        floatingActionButton = findViewById(R.id.floatingActionButton);
        recyclerView = findViewById(R.id.recyclerView);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            email_key = extras.getString("email");
            System.err.println(email_key);
        }

        db = new dbHelper(this);
        loc = new ArrayList<>();
        desc = new ArrayList<>();
        img = new ArrayList<byte[]>();

        StoreDataInArray();

        customAdapter = new CustomAdapter(locations.this, loc, desc, img);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(locations.this));

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(locations.this, remember_location.class);
                i.putExtra("email", email_key);
                startActivity(i);
                finish();
            }
        });

    }
    void StoreDataInArray(){
        Cursor cursor = db.readData(email_key);
        if(cursor.getCount() == 0){
            Toast.makeText(this, "Add few images", Toast.LENGTH_SHORT).show();
        }
        else{
            while (cursor.moveToNext()){
                loc.add(cursor.getString(2));
                desc.add(cursor.getString(3));
                img.add(cursor.getBlob(4));
            }
        }
    }
}