package com.example.vidyadaan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.FileInputStream;

public class profile_ngo extends AppCompatActivity {

    TextView outname,outaddress,outemail,outphone,outid;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    FloatingActionButton newpostbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_ngo);

        outname = findViewById(R.id.outname);
        outid = findViewById(R.id.outid);
        outaddress = findViewById(R.id.outaddress);
        outemail = findViewById(R.id.outemail);
        outphone = findViewById(R.id.outphone);
        newpostbtn = findViewById(R.id.newpostbtn);

        getdata();

        newpostbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(profile_ngo.this,new_request.class);
                profile_ngo.this.startActivity(intent);
            }
        });
    }

    private void getdata() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference("NGOs");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    String filename = "Userdetail.txt";
                    FileInputStream fin = openFileInput(filename);
                    int a,n=0;
                    StringBuilder temp = new StringBuilder();
                    while ((a = fin.read()) != -1) {
                        if(n<2){
                            n++;
                            continue;
                        }
                        temp.append((char) a);
                    }
                    String user = temp.toString();
                    try{
                        String id = snapshot.child(user).child("id").getValue().toString();
                        String name = snapshot.child(user).child("name").getValue().toString();
                        String address = snapshot.child(user).child("address").getValue().toString();
                        String email = snapshot.child(user).child("email").getValue().toString();
                        String phone = snapshot.child(user).child("phone").getValue().toString();
                        String pin = snapshot.child(user).child("pin").getValue().toString();
                        outname.setText(name);
                        outid.setText(id);
                        outaddress.setText(address+", "+pin);
                        outemail.setText(email);
                        outphone.setText(phone);
                    }
                    catch(Exception e){
                        Toast.makeText(profile_ngo.this, ""+e, Toast.LENGTH_SHORT).show();
                    }

                }
                catch(Exception e){
                    Toast.makeText(profile_ngo.this, "File Error", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}