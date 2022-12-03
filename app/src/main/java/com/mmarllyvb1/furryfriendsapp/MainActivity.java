package com.mmarllyvb1.furryfriendsapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void Index(View view){
        Intent indexI = new Intent(MainActivity.this, IndexActivity.class);
        startActivity(indexI);
    }

    public void Regist(View view){
        Intent register = new Intent(MainActivity.this, RegisterActivity.class);
        startActivity(register);
    }
}