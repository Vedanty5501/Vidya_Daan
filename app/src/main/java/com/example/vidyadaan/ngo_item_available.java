package com.example.vidyadaan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ngo_item_available extends AppCompatActivity {

    TextView requestId,prof_text;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    List<requestModel> requestdetails;
    List<requestModel> rd;
    Adapter adapter;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference,reference_ngo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ngo_item_available);
        requestId = findViewById(R.id.reqId);
        prof_text = findViewById(R.id.prof_text);

        requestId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ngo_item_available.this,ngo_myrequest.class);
                ngo_item_available.this.startActivity(intent);
            }
        });
        prof_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ngo_item_available.this,profile_ngo.class);
                ngo_item_available.this.startActivity(intent);
            }
        });

        getData();
    }

    private void getData() {
        rd = new ArrayList<>();
        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference("Donor_Post");
        reference_ngo = firebaseDatabase.getReference("Donors");
    }
}