package com.example.vidyadaan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Choose extends AppCompatActivity {

    Button Donor,Ngo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);

        Donor = findViewById(R.id.Donor);
        Ngo = findViewById(R.id.NGO);

        Donor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(Choose.this, Login_donor.class);
                Choose.this.startActivity(myIntent);
            }
        });

        Ngo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(Choose.this, login_ngo.class);
                Choose.this.startActivity(myIntent);
            }
        });
    }
}