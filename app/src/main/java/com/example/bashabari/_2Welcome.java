package com.example.bashabari;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class _2Welcome extends AppCompatActivity {

    private ImageView tick_btn_2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__02_welcome);


        tick_btn_2 =  findViewById(R.id.tick_btn_2);
        tick_btn_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(_2Welcome.this, _3Login.class);
                startActivity(intent2);
            }
        });

    }
}
