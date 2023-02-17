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
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class ngo_myrequest extends AppCompatActivity {
    TextView prof_text,item_avail;
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
        setContentView(R.layout.activity_ngo_myrequest);
        prof_text = findViewById(R.id.prof_text);
        item_avail = findViewById(R.id.item_avai);

        item_avail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ngo_myrequest.this,ngo_item_available.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        prof_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ngo_myrequest.this,profile_ngo.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        getData();
    }

    private void getData() {
        rd = new ArrayList<>();
        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference("Ngo_Requests");
        reference_ngo = firebaseDatabase.getReference("Ngo");
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
            Query query_get_request = reference.orderByChild("id").equalTo(user);
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
                    else {
                        Toast.makeText(ngo_myrequest.this, "NO Requests posted", Toast.LENGTH_SHORT).show();
                    }
                }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
            });
        }
        catch (Exception e){
            Toast.makeText(ngo_myrequest.this, "NO LOGIN DETECTED", Toast.LENGTH_SHORT).show();
            Intent intent43 = new Intent(ngo_myrequest.this,Choose.class);
            ngo_myrequest.this.startActivity(intent43);
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