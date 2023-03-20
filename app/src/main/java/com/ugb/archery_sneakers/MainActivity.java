package com.ugb.archery_sneakers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void adidas(View View) {
        Intent miIntent = new Intent(MainActivity.this, lista_adidas.class);
        startActivity(miIntent);
    }
}