package com.example.bashabari;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class _11OwnerMenu extends AppCompatActivity {
    ////creating a object to hold the id of the contents of layout 11
    LinearLayout main_menu_layout;
    RelativeLayout home_layout;
    private ImageView menu_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__11_owner_menu);

        ////getting the id of the contents of layout 11
        main_menu_layout = findViewById(R.id.main_menu_layout_11);
        home_layout = findViewById(R.id.home_layout_11);
        menu_btn = findViewById(R.id.menu_btn_11);

        menu_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                main_menu_layout.animate().translationX(0);
                home_layout.animate().translationX(0);
            }
        });

        home_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                main_menu_layout.animate().translationX(-975);
                home_layout.animate().translationX(-975);
            }
        });


    }
}
