package com.mmarllyvb1.furryfriendsapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void volver (View view){
        Intent back = new Intent(RegisterActivity.this, MainActivity.class);
        startActivity(back);
    }
}