package com.example.vidyadaan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

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

public class ngo_item_available extends AppCompatActivity {

    TextView requestId,prof_text;
    FloatingActionButton backbtn;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    List<requestModel> requestdetails;
    List<requestModel> rd;
    Adapter_new adapter;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference,reference_ngo,reference_donor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ngo_item_available);
        requestId = findViewById(R.id.reqId);
        prof_text = findViewById(R.id.prof_text);
        backbtn = findViewById(R.id.backbtn);

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
        reference_ngo = firebaseDatabase.getReference("NGOs");
        reference_donor = firebaseDatabase.getReference("Donors");
        try {
            String filename = "Userdetail.txt";
            FileInputStream fin = openFileInput(filename);
            int a, n = 0;
            StringBuilder temp = new StringBuilder();
            while ((a = fin.read()) != -1) {
                if (n < 2) {
                    n++;
                    continue;
                }
                temp.append((char) a);
            }
            String user = temp.toString();
            reference_ngo.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String got_pin=snapshot.child(user).child("pin").getValue().toString();
                    Query query_1 = reference.orderByChild("pin").equalTo(got_pin);
                    query_1.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snap) {
                            if(snap.exists()){
                               reference_donor.addListenerForSingleValueEvent(new ValueEventListener() {
                                   @Override
                                   public void onDataChange(@NonNull DataSnapshot s) {
                                       for (DataSnapshot data: snap.getChildren()) {
                                           String rid = data.child("description").getValue().toString();
                                           String d_id = data.child("id").getValue().toString();
                                           String contact = s.child(d_id).child("phone").getValue().toString();
                                           rd.add(new requestModel(rid,contact));
                                       }
                                       Log.v("tag1",""+rd);
                                       initData(rd);
                                   }

                                   @Override
                                   public void onCancelled(@NonNull DatabaseError error) {

                                   }
                               });

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        catch(Exception e){

        }
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
        adapter = new Adapter_new(requestdetails);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}