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

import java.io.FileOutputStream;

public class Login_donor extends AppCompatActivity {

    EditText username,password;
    TextView signuptxt;
    Button loginButton;
    String emailpatt="^[a-zA-Z0-9+_.-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;

    FirebaseAuth mAuth;
    FirebaseUser mUser;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_donor);


        username=findViewById(R.id.username);
        password=findViewById(R.id.password);
        signuptxt=findViewById(R.id.signuptext);
        loginButton=findViewById(R.id.loginButton);
        progressDialog=new ProgressDialog(this);
        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();

        signuptxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Login_donor.this,Register_donor.class);
                startActivity(intent1);
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

        if (!email.matches(emailpatt)){
            username.setError("Enter Correct Email");
        }
        else if (pass.isEmpty() || pass.length()<8){
            password.setError("Password not Valid");
        }
        else {
            progressDialog.setMessage("Please wait while Loging In.....");
            progressDialog.setTitle("Registration");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        firebaseDatabase = FirebaseDatabase.getInstance();
                        reference = firebaseDatabase.getReference("Donors");
                        Query check_id = reference.orderByChild("email").equalTo(email);
                        check_id.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if(snapshot.exists()){
                                    progressDialog.dismiss();
                                    for(DataSnapshot sp:snapshot.getChildren()){
                                        String id = sp.child("id").getValue(String.class);
                                        Writefile(id);
                                    }
                                    SendUsertoNextActivity();
                                    Toast.makeText(Login_donor.this, "Login Successful", Toast.LENGTH_SHORT).show();

                                }
                                else{
                                    progressDialog.dismiss();
                                    Toast.makeText(Login_donor.this, "No User", Toast.LENGTH_SHORT).show();
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }
                    else{
                        progressDialog.dismiss();
                        Toast.makeText(Login_donor.this, ""+task.getException(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void Writefile(String email) {
        try {
            String filename = "Userdetail.txt";
            FileOutputStream fos = openFileOutput(filename, Context.MODE_PRIVATE);
            email = "D-"+email;
            Toast.makeText(this, "ID written "+email, Toast.LENGTH_SHORT).show();
            fos.write(email.getBytes());
            fos.flush();
            fos.close();
        }
        catch(Exception e){
            Toast.makeText(this, "File Not Found "+e, Toast.LENGTH_LONG).show();
        }
    }

    private void SendUsertoNextActivity() {
        Intent intent = new Intent(Login_donor.this,profile_donor.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}