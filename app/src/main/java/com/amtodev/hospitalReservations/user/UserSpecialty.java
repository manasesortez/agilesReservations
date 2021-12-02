package com.amtodev.hospitalReservations.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.amtodev.hospitalReservations.Login;
import com.amtodev.hospitalReservations.R;
import com.google.firebase.auth.FirebaseAuth;

public class UserSpecialty extends AppCompatActivity {
    ImageButton SearchSpecialty;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_specialty);

        ImageView goBackActivity = findViewById(R.id.btnAfter);
        goBackActivity.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                AfterActivity();
            }
        });

        SearchSpecialty = findViewById(R.id.btnSearchUserSpecialty);
        SearchSpecialty.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Toast.makeText(UserSpecialty.this, "Search Item Specialty", Toast.LENGTH_LONG).show();
            }
        });


    }

    public void AfterActivity(){
        startActivity(new Intent(getApplicationContext(), UserMain.class));
        finish();
    }
}