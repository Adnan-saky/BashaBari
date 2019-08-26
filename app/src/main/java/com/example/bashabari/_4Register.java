package com.example.bashabari;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class _4Register extends AppCompatActivity {


    private ImageView next_btn_4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__04_register);

        next_btn_4 =  findViewById(R.id.next_btn_4);
        next_btn_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(_4Register.this, _3Login.class);
                startActivity(intent1);
            }
        });
    }
}
