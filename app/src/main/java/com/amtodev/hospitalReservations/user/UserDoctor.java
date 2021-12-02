package com.amtodev.hospitalReservations.user;

import static com.airbnb.lottie.L.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.amtodev.hospitalReservations.R;
import com.amtodev.hospitalReservations.user.Adapter.DataHospital;
import com.amtodev.hospitalReservations.user.Adapter.DataSpecialty;

public class UserDoctor extends AppCompatActivity {

    private static final String TAG = "UserSpecialty";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_doctor);

        ImageView goBackActivity = findViewById(R.id.backSpecialty);
        goBackActivity.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                AfterActivity();
            }
        });

        if(getIntent().hasExtra("especialidad_id")) {
            DataSpecialty specialty = getIntent().getParcelableExtra("especialidad_id");
            int specialidad_id = specialty.getEspecialidad_id();

            Toast.makeText(UserDoctor.this, "especialidad_id: " + specialty.toString(), Toast.LENGTH_LONG).show();
        }

    }

    public void AfterActivity(){
        startActivity(new Intent(getApplicationContext(), UserMain.class));
        finish();
    }
}