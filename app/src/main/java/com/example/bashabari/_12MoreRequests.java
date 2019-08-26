package com.example.bashabari;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class _12MoreRequests extends AppCompatActivity {
    ////creating a object to hold the id of arrow button of layout 12
    private ImageView arrow_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__12_more_requests);

        ////getting the id of arrow button of layout 12
        arrow_btn =  findViewById(R.id.arrow_btn_12);
        arrow_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent12 = new Intent(_12MoreRequests.this, _11OwnerMenu.class);
                startActivity(intent12);
            }
        });
    }
}
