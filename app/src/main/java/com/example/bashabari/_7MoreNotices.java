package com.example.bashabari;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class _7MoreNotices extends AppCompatActivity {

    private ImageView back_arrow_btn_7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__07_more_notices);

        back_arrow_btn_7 = findViewById(R.id.back_arrow_btn_7);

        back_arrow_btn_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent7 = new Intent(_7MoreNotices.this, _6UserMenu.class);
                startActivity(intent7);
            }
        });
    }
}
