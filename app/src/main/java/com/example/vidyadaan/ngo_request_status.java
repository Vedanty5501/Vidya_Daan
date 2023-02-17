package com.example.vidyadaan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class ngo_request_status extends AppCompatActivity {
    RecyclerView recyclerView;
    Button deletebtn;
    FloatingActionButton backbtn;
    LinearLayoutManager linearLayoutManager;
    List<requestModel> requestdetails;
    List<requestModel> rd;
    Adapter adapter;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference,reference_r,reference_ngo_request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ngo_request_status);
        deletebtn = findViewById(R.id.deletebutton);
        backbtn = findViewById(R.id.backbtn);


        String requestId = getIntent().getStringExtra("request");
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent77 = new Intent(ngo_request_status.this,ngo_myrequest.class);
                ngo_request_status.this.startActivity(intent77);
            }
        });

        deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseDatabase = FirebaseDatabase.getInstance();
                reference_ngo_request = firebaseDatabase.getReference("Ngo_Requests");
                reference_ngo_request.child(requestId).removeValue();
                Intent intent77 = new Intent(ngo_request_status.this,ngo_myrequest.class);
                ngo_request_status.this.startActivity(intent77);

            }
        });
        getData(requestId);
    }

    private void getData(String requestId) {
        rd = new ArrayList<>();
        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference("Donors");
        reference_r = firebaseDatabase.getReference("Donations");
        Query query_get_request = reference_r.orderByChild("request_id").equalTo(requestId);
        query_get_request.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snap) {
                if(snap.exists()){
                    reference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot data: snap.getChildren()) {
                                String donor_id = data.child("donor_id").getValue().toString();
                                String donor_name = snapshot.child(donor_id).child("name").getValue().toString();
                                String donor_phone = snapshot.child(donor_id).child("phone").getValue().toString();
                                rd.add(new requestModel(donor_name,donor_phone));
                            }
                            Log.v("tag1",""+rd);
                            initData(rd);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
                else {
                    Toast.makeText(ngo_request_status.this, "NO Donations", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void initData(List<requestModel> rd) {
        requestdetails = new ArrayList<>();
        requestdetails = rd;
        Log.v("taggy",""+rd);
        initRecyclerView();
    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.recyclerview);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new Adapter(requestdetails);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

}