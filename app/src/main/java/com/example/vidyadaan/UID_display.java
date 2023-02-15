
package com.example.vidyadaan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class UID_display extends AppCompatActivity {

    TextView uid;
    Button cont;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uid_display);
        Bundle bundle = getIntent().getExtras();
        String message = bundle.getString("id");

        uid = findViewById(R.id.uid);
        cont = findViewById(R.id.ConButton);

        uid.setText(message);

        cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UID_display.this,login_ngo.class);
                UID_display.this.startActivity(intent);
            }
        });
    }
}