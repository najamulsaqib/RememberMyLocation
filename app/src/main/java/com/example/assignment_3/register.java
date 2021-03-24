package com.example.assignment_3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.assignment_3.R;

public class register extends AppCompatActivity {

    EditText name, num, email ,pass;
    Button reg;
    TextView btn1;
    dbHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();
        //
        name = findViewById(R.id.name);
        num = findViewById(R.id.num);
        email = findViewById(R.id.eamil);
        pass = findViewById(R.id.pass);
        //
        reg = findViewById(R.id.reg);
        btn1 = findViewById(R.id.intent_signin);

        db = new dbHelper(this);

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Name = name.getText().toString();
                String Num = num.getText().toString();
                String Mail= email.getText().toString();
                String Pass= pass.getText().toString();

//                if(Name.equalsIgnoreCase(""));{
//                    name.setError("Name is blank");
//                }
//                if(Num.equalsIgnoreCase(""));{
//                    num.setError("Num is blank");
//                }
//                if(Mail.equalsIgnoreCase(""));{
//                    email.setError("Email is blank");
//                }
//                if(Pass.equalsIgnoreCase(""));{
//                    pass.setError("Password is blank");
//                }
                if(Pass.equalsIgnoreCase("") && Name.equalsIgnoreCase("") && Num.equalsIgnoreCase("") && Mail.equalsIgnoreCase("")){}else{
                    Boolean rsl = db.userRegister(Name, Mail, Pass, Num);
                    if(rsl){
                        Intent i = new Intent(register.this, signin.class);
                        Toast.makeText(register.this, "SignUP Successful", Toast.LENGTH_SHORT). show();
                        startActivity(i);
                        finish();
                    }
                    else {
                        Toast.makeText(register.this, "Unknown Error!", Toast.LENGTH_SHORT). show();
                    }
                }
            }
        });
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent R = new Intent(register.this, signin.class);
                startActivity(R);
                finish();
            }
        });
    }
}