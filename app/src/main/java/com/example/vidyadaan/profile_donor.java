package com.example.vidyadaan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class profile_donor extends AppCompatActivity {

    TextView outname,outdob,outemail,outphone,outreward,outid,need;
    Button logout;
    FloatingActionButton donor_post;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_donor);

//        Bundle bundle = getIntent().getExtras();
//        String message = bundle.getString("message");

        outname = findViewById(R.id.outname);
        outid = findViewById(R.id.outid);
        outdob = findViewById(R.id.outdob);
        outemail = findViewById(R.id.outemail);
        outphone = findViewById(R.id.outphone);
        outreward = findViewById(R.id.outreward);
        need = findViewById(R.id.need);
        logout = findViewById(R.id.logoutbtn);
        donor_post = findViewById(R.id.new_post_btn);

        donor_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent92= new Intent(profile_donor.this,donor_post.class);
                profile_donor.this.startActivity(intent92);
            }
        });


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    String filename = "Userdetail.txt";
                    FileOutputStream fos = openFileOutput(filename, Context.MODE_PRIVATE);
                    String email = "";
                    fos.write(email.getBytes());
                    fos.flush();
                    fos.close();
                    Intent intent = new Intent(profile_donor.this,Choose.class);
                    profile_donor.this.startActivity(intent);
                }
                catch(Exception e){
                    Toast.makeText(profile_donor.this, "No Login Detected", Toast.LENGTH_SHORT).show();
                }
            }
        });

        need.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent43 = new Intent(profile_donor.this,Home.class);
                profile_donor.this.startActivity(intent43);
            }
        });

        getdata();
    }

    private void getdata() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference("Donors");

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
                        String dob = snapshot.child(user).child("dob").getValue().toString();
                        String email = snapshot.child(user).child("email").getValue().toString();
                        String phone = snapshot.child(user).child("phone").getValue().toString();
                        String reward = snapshot.child(user).child("reward").getValue().toString();
                        outname.setText(name);
                        outid.setText(id);
                        outdob.setText(dob);
                        outemail.setText(email);
                        outphone.setText(phone);
                        outreward.setText(reward);
                    }
                    catch(Exception e){
                        System.out.println(""+e);
                        Toast.makeText(profile_donor.this, ""+e, Toast.LENGTH_SHORT).show();
                    }

                }
                catch(Exception e){
                    Toast.makeText(profile_donor.this, "File Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}