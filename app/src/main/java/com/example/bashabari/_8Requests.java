package com.example.bashabari;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class _8Requests extends AppCompatActivity {

    private ImageView back_arrow_btn_8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__08_requests);


        back_arrow_btn_8 = findViewById(R.id.back_arrow_btn_8);

        back_arrow_btn_8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent8 = new Intent(_8Requests.this, _6UserMenu.class);
                startActivity(intent8);
            }
        });
    }
}
