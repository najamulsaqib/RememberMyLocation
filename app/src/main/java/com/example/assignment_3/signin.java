package com.example.assignment_3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class signin extends AppCompatActivity {

    EditText email,pass;
    Button SignIn;
    TextView btn;
    dbHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        getSupportActionBar().hide();

        email = findViewById(R.id.eamil);
        pass = findViewById(R.id.pass);

        SignIn = findViewById(R.id.signin);
        btn = findViewById(R.id.intent_signup);
        db = new dbHelper(this);

        SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail , pas;
                mail= email.getText().toString();
                pas = pass.getText().toString();

//                if (mail.equalsIgnoreCase("")){
//                    email.setError("Please Enter Email");
//                }
//                if(pas.equalsIgnoreCase("")){
//                    pass.setError("Please Enter Password");
//                }
                if(mail.equalsIgnoreCase("") && pas.equalsIgnoreCase("")){} else{
                    Cursor result = db.UserLogin(mail, pas);
                    if(result.getCount() == 0){
                        email.setError("Please Enter Email");
                        pass.setError("Please Enter Password");
                        //Toast.makeText(signin.this, "", Toast.LENGTH_LONG).show();
                    }
                    else{
                        StringBuffer buffer = new StringBuffer();
                        while (result.moveToNext()){
                            buffer.append(result.getString(0));
                            String email_key = buffer.toString();
                            Intent i = new Intent(signin.this, locations.class);
                            i.putExtra("email", email_key);
                            startActivity(i);
                            finish();
                        }
                    }

                }
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(signin.this, register.class);
                startActivity(i);
                finish();
            }
        });
    }
}