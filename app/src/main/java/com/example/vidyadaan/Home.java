package com.example.vidyadaan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseApiNotAvailableException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity {

    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    List<requestModel> requestdetails;
    List<requestModel> rd;
    Adapter adapter;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference,reference_donor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getData();

    }

    private void getData() {
        rd = new ArrayList<>();
        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference("Ngo_Requests");
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
            Query query_get_pin = reference_donor.orderByChild("id").equalTo(user);
            query_get_pin.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()){
                        String got_pin = snapshot.child(user).child("pin").getValue().toString();
                        Query query_get_request = reference.orderByChild("pin").equalTo(got_pin);
                        query_get_request.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snap) {
                                if(snap.exists()){
                                    for (DataSnapshot data: snap.getChildren()) {
                                        String rid = data.child("request_id").getValue().toString();
                                        String rdesc = data.child("description").getValue().toString();
                                        rd.add(new requestModel(rid,rdesc));
                                    }
                                    Log.v("tag1",""+rd);
                                    initData(rd);
                                }
                                else{
                                    Toast.makeText(Home.this, "NO Requests in your city", Toast.LENGTH_SHORT).show();
                                }
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
        catch (Exception e){
            Toast.makeText(Home.this, "NO LOGIN DETECTED", Toast.LENGTH_SHORT).show();
            Intent intent43 = new Intent(Home.this,Choose.class);
            Home.this.startActivity(intent43);
        }

        Log.v("taggy",""+requestdetails);

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