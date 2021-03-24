package com.example.assignment_3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class dbHelper extends SQLiteOpenHelper {

    ByteArrayOutputStream objBAI;
    byte[] imageInByte;
    public dbHelper(Context context) {
        super(context, "remember.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create Table image (id INTEGER primary key AUTOINCREMENT, email Text, location Text, description Text, image BLOB)");
        db.execSQL("Create Table user (email Text primary key, name Text, password Text, number Text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public Boolean userRegister(String name, String email, String password, String number){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("email", email);
        cv.put("name", name);
        cv.put("password", password);
        cv.put("number", number);
        long result = db.insert("user", null, cv);
        if(result == -1){
            return false;
        }
        else{
            return true;
        }
    }

    public Cursor UserLogin(String email, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from user where email = ? and password = ?", new String[]{email, password});
        return cursor;
    }

    public Boolean storeImage(String location, String description, String email, Bitmap image){
        SQLiteDatabase db = this.getWritableDatabase();
        try {

            objBAI = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.JPEG, 10, objBAI);
            imageInByte = objBAI.toByteArray();
            ContentValues cv = new ContentValues();
            cv.put("email", email);
            cv.put("location", location);
            cv.put("description", description);
            cv.put("image", imageInByte);
            long chk = db.insert("image", null, cv);
            if(chk == -1){
                return false;
            }
            else{
                return true;
            }

        }
        catch (Exception e){
        }
        return false;
    }
    public Cursor readData(String email){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from image where email = ?", new String[]{email});
        return cursor;
    }
}
