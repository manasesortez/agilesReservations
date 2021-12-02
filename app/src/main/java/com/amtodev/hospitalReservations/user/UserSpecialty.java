package com.amtodev.hospitalReservations.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.amtodev.hospitalReservations.R;
import com.amtodev.hospitalReservations.user.Adapter.DataHospital;

public class UserSpecialty extends AppCompatActivity {
    private static final String TAG = "UserSpecialty";
    ImageButton SearchSpecialty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_specialty);

        if(getIntent().hasExtra("selected_note")){
            DataHospital hospital = getIntent().getParcelableExtra("selected_note");
            Log.d(TAG, "onCreate: " + hospital.toString());
        }

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