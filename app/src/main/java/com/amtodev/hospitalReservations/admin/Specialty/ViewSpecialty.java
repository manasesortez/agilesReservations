package com.amtodev.hospitalReservations.admin.Specialty;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.amtodev.hospitalReservations.R;
import com.amtodev.hospitalReservations.admin.Admin;

public class ViewSpecialty extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_specialty);
    }

    public void openAdminViewSpecialty(View view) {
        startActivity(new Intent(this, Admin.class));
        overridePendingTransition(R.anim.slide_in_left,android.R.anim.slide_out_right);
        finish();
    }
}