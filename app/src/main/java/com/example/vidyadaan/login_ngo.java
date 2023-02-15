package com.example.vidyadaan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class login_ngo extends AppCompatActivity {

    EditText username,password;
    TextView signuptext;
    Button loginButton;
    String emailpatt="^[a-zA-Z0-9+_.-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_ngo);

        username=findViewById(R.id.username);
        signuptext=findViewById(R.id.signuptext);
        password=findViewById(R.id.password);
        loginButton=findViewById(R.id.loginButton);
        progressDialog=new ProgressDialog(this);

        signuptext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent new_intent = new Intent(login_ngo.this,register_ngo.class);
                startActivity(new_intent);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performlogin();

            }
        });
    }

    private void performlogin() {
        String email = username.getText().toString();
        String pass  = password.getText().toString();

        if (pass.isEmpty() || pass.length()<8){
            password.setError("Password not Valid");
        }
        else {
            progressDialog.setMessage("Please wait while Loging In.....");
            progressDialog.setTitle("Log In");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            firebaseDatabase = FirebaseDatabase.getInstance();
            reference = firebaseDatabase.getReference("NGOs");

            Query check_id = reference.orderByChild("id").equalTo(email);

            check_id.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()){
                        String pass_check = snapshot.child(email).child("pass").getValue().toString();
                        if (pass.equals(pass_check)){
                            progressDialog.dismiss();
                            Toast.makeText(login_ngo.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            createfile(email);
                            SendUsertoNextActivity();
                        }
                        else{
                            progressDialog.dismiss();
                            password.setError("Incorrect Password");
                        }
                    }
                    else{
                        username.setError("User not Found");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    private void createfile(String email){
        try {
            String filename = "Userdetail.txt";
            FileOutputStream fos = openFileOutput(filename, Context.MODE_PRIVATE);
            email = "N-"+email;
            fos.write(email.getBytes());
            fos.flush();
            fos.close();
        }
        catch(Exception e){
            Toast.makeText(this, "File Not Found "+e, Toast.LENGTH_LONG).show();
        }

    }

    private void SendUsertoNextActivity() {
        Intent intent = new Intent(login_ngo.this,profile_ngo.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}