package com.example.vidyadaan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
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
import com.google.firebase.database.ValueEventListener;

public class register_ngo extends AppCompatActivity {
    
    TextView logintxt;
    EditText inputemail,inputpassword,confirmpassword,inputname,inputphone,inputaddress,inputpin;
    Button signupbtn;
    String emailpatt="^[a-zA-Z0-9+_.-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;

    FirebaseAuth mAuth;
    FirebaseUser mUser;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference_ngo,reference_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_ngo);
        
        logintxt=findViewById(R.id.logintext);
        inputemail=findViewById(R.id.inputemail);
        inputpassword=findViewById(R.id.inputpassword);
        confirmpassword=findViewById(R.id.confirmpassword);
        inputname=findViewById(R.id.inputname);
        inputphone=findViewById(R.id.inputphone);
        inputaddress=findViewById(R.id.inputaddress);
        inputpin=findViewById(R.id.inputpin);
        signupbtn=findViewById(R.id.signupButton);
        progressDialog=new ProgressDialog(this);
        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();

        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
                performAuth();
            }
        });

        logintxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(register_ngo.this,login_ngo.class);
                register_ngo.this.startActivity(intent1);
            }
        });
    }

    private void performAuth() {
        String name = inputname.getText().toString();
        String email = inputemail.getText().toString();
        String phone = inputphone.getText().toString();
        String address = inputaddress.getText().toString();
        String apin = inputpin.getText().toString();
        String pass  = inputpassword.getText().toString();
        String conpass = confirmpassword.getText().toString();

        if (name.isEmpty()){
            inputname.setError("Enter Institute Name");
        }
        else if (!email.matches(emailpatt)){
            inputemail.setError("Enter Correct Email");
        }
        else if (phone.isEmpty() || phone.length()!=10){
            inputphone.setError("Enter correct Phone Number");
        }
        else if (pass.isEmpty() || pass.length()<8){
            inputpassword.setError("Password not Valid");
        }
        else if (!pass.equals(conpass)){
            confirmpassword.setError("Password does not Match");
        }
        else if (address.isEmpty()){
            inputaddress.setError("Address Required");
        }
        else if (apin.isEmpty()){
            inputpin.setError("Address PinCode Required");
        }
        else{
            progressDialog.setMessage("Please wait while Registring.....");
            progressDialog.setTitle("Registration");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            firebaseDatabase = FirebaseDatabase.getInstance();
            reference_ngo = firebaseDatabase.getReference("NGOs");
            reference_id  = firebaseDatabase.getReference("UID");

           reference_id.addListenerForSingleValueEvent(new ValueEventListener() {
               @Override
               public void onDataChange(@NonNull DataSnapshot snapshot) {
                   String id = snapshot.child("Ngo_ID").getValue().toString();
                   Toast.makeText(register_ngo.this,"got id",Toast.LENGTH_SHORT).show();
                   database__ngo database = new database__ngo(name,id,email,phone,pass,address,apin);
                   Toast.makeText(register_ngo.this,"Registration Done",Toast.LENGTH_SHORT).show();
                   reference_ngo.child(id).setValue(database);
                   id=(Integer.parseInt(id)+1)+"";
                   reference_id.child("Ngo_ID").setValue(id);
                   progressDialog.dismiss();
                   SendUsertoNextActivity(id);
                   Toast.makeText(register_ngo.this,"Registration Done",Toast.LENGTH_SHORT).show();
                   finish();

               }

               @Override
               public void onCancelled(@NonNull DatabaseError error) {

               }
           });
        }
    }

    private void SendUsertoNextActivity(String id) {
        Intent intent = new Intent(register_ngo.this,UID_display.class);
        id=(Integer.parseInt(id)-1)+"";
        intent.putExtra("id",id);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}