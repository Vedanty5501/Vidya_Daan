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

public class Register_donor extends AppCompatActivity {

    TextView login;
    EditText inputemail,inputpassword,confirmpassword,inputname,inputdob,inputphone;
    Button signupbtn;
    String emailpatt="^[a-zA-Z0-9+_.-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;

    FirebaseAuth mAuth;
    FirebaseUser mUser;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference,reference_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_donor);
        Intent intent = getIntent();

        login=findViewById(R.id.logintext);
        inputemail=findViewById(R.id.inputemail);
        inputpassword=findViewById(R.id.inputpassword);
        confirmpassword=findViewById(R.id.confirmpassword);
        inputname=findViewById(R.id.inputname);
        inputphone=findViewById(R.id.inputphone);
        inputdob=findViewById(R.id.inputdob);
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

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(Register_donor.this,Login_donor.class);
                startActivity(intent1);
            }
        });
    }

    private void performAuth() {
        String name = inputname.getText().toString();
        String dob = inputdob.getText().toString();
        String email = inputemail.getText().toString();
        String phone = inputphone.getText().toString();
        String pass  = inputpassword.getText().toString();
        String conpass = confirmpassword.getText().toString();

        if (name.isEmpty()){
            inputname.setError("Enter your Name");
        }
        else if (dob.isEmpty()){
            inputdob.setError("Enter your date of birth");
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
        else{
            progressDialog.setMessage("Please wait while Registring.....");
            progressDialog.setTitle("Registration");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        firebaseDatabase = FirebaseDatabase.getInstance();
                        reference = firebaseDatabase.getReference("Donors");
                        reference_id = firebaseDatabase.getReference("UID");
                        reference_id.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                String id_1 = snapshot.child("Donor_ID").getValue().toString();
                                String reward = "0";
                                Database database = new Database(name,id_1,dob,email,phone,pass,reward);
                                reference.child(id_1).setValue(database);
                                id_1 = (Integer.parseInt(id_1)+1)+"";
                                reference_id.child("Donor_ID").setValue(id_1);
                                progressDialog.dismiss();
                                SendUsertoNextActivity();
                                Toast.makeText(Register_donor.this,"Registration Done",Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }
                    else{
                        progressDialog.dismiss();
                        Toast.makeText(Register_donor.this, ""+task.getException(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }


    private void SendUsertoNextActivity() {
        Intent intent = new Intent(Register_donor.this,Login_donor.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}