package com.example.vidyadaan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class view_request extends AppCompatActivity {
    Button donatebtn;
    FloatingActionButton backbtn;
    TextView outname,outrequesttype,outdescription,outaddress,outphone,outdonation;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference,reference_ngo,reference_donation,reference_id,reference_donor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_request);
        backbtn = findViewById(R.id.backbtn);
        donatebtn = findViewById(R.id.donatebutton);
        outname = findViewById(R.id.outname);
        outrequesttype = findViewById(R.id.outrequesttype);
        outdescription = findViewById(R.id.outdescription);
        outaddress = findViewById(R.id.outaddress);
        outphone = findViewById(R.id.outphone);
        outdonation = findViewById(R.id.outdonation);


        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference("Ngo_Requests");
        reference_ngo = firebaseDatabase.getReference("NGOs");
        reference_donation = firebaseDatabase.getReference("Donations");
        reference_id = firebaseDatabase.getReference("UID");
        reference_donor = firebaseDatabase.getReference("Donors");



        String requestId = getIntent().getStringExtra("request");
        setData(requestId);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent77 = new Intent(view_request.this,Home.class);
                view_request.this.startActivity(intent77);
            }
        });

        donatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

                    reference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String ngo_id = snapshot.child(requestId).child("id").getValue().toString();

                            reference_id.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    String id = snapshot.child("Donation_ID").getValue().toString();
                                    Donation_database db = new Donation_database(requestId,user,ngo_id,id);
                                    reference_donation.child(id).setValue(db);
                                    id=(Integer.parseInt(id)+1)+"";
                                    reference_id.child("Donation_ID").setValue(id);

                                    reference_donor.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            String reward = snapshot.child(user).child("reward").getValue().toString();
                                            reward=(Integer.parseInt(reward)+10)+"";
                                            reference_donor.child(user).child("reward").setValue(reward);

                                            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                    String don_req = snapshot.child(requestId).child("donation_requests").getValue().toString();
                                                    don_req=(Integer.parseInt(don_req)+1)+"";
                                                    reference.child(requestId).child("donation_requests").setValue(don_req);

                                                    Intent intent77 = new Intent(view_request.this,Home.class);
                                                    view_request.this.startActivity(intent77);
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
                catch (Exception e){
                    Toast.makeText(view_request.this, "No Login Detected", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setData(String requestId) {

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.v("taggy",""+snapshot);
                String ngo_id = snapshot.child(requestId).child("id").getValue().toString();
                String request_type = snapshot.child(requestId).child("type").getValue().toString();
                String desc = snapshot.child(requestId).child("description").getValue().toString();
                String Donations_done = snapshot.child(requestId).child("donation_requests").getValue().toString();

                reference_ngo.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snap) {
                        String ngo_name = snap.child(ngo_id).child("name").getValue().toString();
                        String ngo_address = snap.child(ngo_id).child("address").getValue().toString()+", "+snap.child(ngo_id).child("pin").getValue().toString();
                        String ngo_phone = snap.child(ngo_id).child("phone").getValue().toString();


                        outname.setText(ngo_name);
                        outrequesttype.setText(request_type);
                        outdescription.setText(desc);
                        outaddress.setText(ngo_address);
                        outphone.setText(ngo_phone);
                        outdonation.setText(Donations_done);
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
}