package com.example.bashabari;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

public class _0loading extends AppCompatActivity {

    private static int loadingtime=5000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__0loading);


        Toast.makeText(getApplicationContext(), "Please wait for a moment..", Toast.LENGTH_LONG).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent loading = new Intent(_0loading.this,_1Welcome.class);
                startActivity(loading);
                finish();
            }
        },loadingtime);
    }
}
