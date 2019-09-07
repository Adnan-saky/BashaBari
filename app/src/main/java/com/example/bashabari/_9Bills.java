package com.example.bashabari;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class _9Bills extends AppCompatActivity {
    ////creating a object to hold the id of arrow button of layout 9
    private ImageView arrow_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__09_bills);

        ////getting the id of arrow button of layout 9
        arrow_btn =  findViewById(R.id.arrow_btn_9);
        arrow_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent9 = new Intent(_9Bills.this, _6UserMenu.class);
                startActivity(intent9);
            }
        });


    }
}
