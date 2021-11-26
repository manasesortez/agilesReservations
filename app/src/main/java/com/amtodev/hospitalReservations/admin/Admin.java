package com.amtodev.hospitalReservations.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.amtodev.hospitalReservations.Login;
import com.amtodev.hospitalReservations.R;
import com.amtodev.hospitalReservations.admin.Doctor.AdminDoctor;
import com.amtodev.hospitalReservations.admin.Hospital.AdminHospital;
import com.amtodev.hospitalReservations.admin.Specialty.AdminSpecialty;
import com.amtodev.hospitalReservations.forget_password;
import com.google.firebase.auth.FirebaseAuth;

public class Admin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        CardView logoutAdmin = findViewById(R.id.cv_luminosidad);
        logoutAdmin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                logoutAdmin(view);
                finish();
            }
        });
    }

    public void logoutAdmin(View view){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), Login.class));
        finish();
    }

    public void openAdminDoctor(View View){
        startActivity(new Intent(this, AdminDoctor.class));
        overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
        finish();
    }

    public void openAdminHospital(View View){
        startActivity(new Intent(this, AdminHospital.class));
        overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
        finish();
    }

    public void openAdminSpecialty(View View){
        startActivity(new Intent(this, AdminSpecialty.class));
        overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
        finish();
    }

}