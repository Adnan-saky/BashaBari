package com.example.bashabari;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class _1Welcome extends AppCompatActivity {

    private ImageView arrow_btn_1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__01_welcome);

        arrow_btn_1 =  findViewById(R.id.arrow_btn_1);
        arrow_btn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(_1Welcome.this, _2Welcome.class);
                startActivity(intent1);
            }
        });


    }
}
