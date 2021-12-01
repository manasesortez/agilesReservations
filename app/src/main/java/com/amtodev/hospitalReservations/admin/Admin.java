package com.amtodev.hospitalReservations.admin;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.amtodev.hospitalReservations.Login;
import com.amtodev.hospitalReservations.R;
import com.amtodev.hospitalReservations.admin.Doctor.AdminDoctor;
import com.amtodev.hospitalReservations.admin.Doctor.UpdateDoctor;
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
                openDialog(view);
            }
        });
    }

    public void logoutAdmin(){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), Login.class));
        finish();
    }

    public void openDialog(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(Admin.this);
        builder.setTitle("Confirm");
        builder.setMessage("Are you Sure to Logout");
        builder.setPositiveButton("Yes, Logout", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                logoutAdmin();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(Admin.this, "No Logout", Toast.LENGTH_SHORT).show();
            }
        });
        builder.create();
        builder.show();
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