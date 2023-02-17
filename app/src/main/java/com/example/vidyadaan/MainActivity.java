package com.example.vidyadaan;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.CheckedOutputStream;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try
                {
                    String filename = "Userdetail.txt";
                    FileInputStream fin = openFileInput(filename);
                    int a;
                    StringBuilder temp = new StringBuilder();
                    while ((a = fin.read()) != -1)
                    {
                        temp.append((char)a);
                    }
                    String user = temp.toString();
                    if(user.isEmpty()){
                        Intent intent2 = new Intent(MainActivity.this, Choose.class);
                        MainActivity.this.startActivity(intent2);
                    }
                    if(user.charAt(0)=='D'){
                        Intent intent = new Intent(MainActivity.this, profile_donor.class);
                        MainActivity.this.startActivity(intent);
                        fin.close();
                    }
                    else{
                        Intent intent1 = new Intent(MainActivity.this, profile_ngo.class);
                        MainActivity.this.startActivity(intent1);
                        fin.close();
                    }
                }
                catch (Exception e){
                    Intent intent2 = new Intent(MainActivity.this, Choose.class);
                    MainActivity.this.startActivity(intent2);

                }
            }
        },3000);
    }
}