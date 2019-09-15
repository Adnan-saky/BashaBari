package com.example.bashabari;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class _6UserMenu extends AppCompatActivity {
    ////creating a object to hold the id of the contents of layout 6
    LinearLayout main_menu_layout, main_menu_layout_right;
    RelativeLayout home_layout;
    private ImageView menu_btn;
    private ImageView see_more_6;
    private TextView bill_6;
    private TextView request_6, signout_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__06_user_menu);

        ////getting the id of the contents of layout 11
        main_menu_layout = findViewById(R.id.main_menu_layout_6);
        home_layout = findViewById(R.id.home_layout_6);
        main_menu_layout_right = findViewById(R.id.main_menu_layout_6right);
        menu_btn = findViewById(R.id.menu_btn_6);
        signout_btn = findViewById(R.id.signout_6);
        bill_6 = findViewById(R.id.bills_6);
        request_6 = findViewById(R.id.request_6);
        see_more_6 = findViewById(R.id.see_more_6);

        menu_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                main_menu_layout.setVisibility(View.VISIBLE);
                home_layout.setVisibility(view.GONE);
            }
        });

        main_menu_layout_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(main_menu_layout.getVisibility() == View.VISIBLE){
                    main_menu_layout.setVisibility(View.GONE);
                    home_layout.setVisibility(view.VISIBLE);
                }
            }
        });

        see_more_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_6 = new Intent(_6UserMenu.this, _7MoreNotices.class);
                startActivity(intent_6);
            }
        });

        request_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_6 = new Intent(_6UserMenu.this, _8Requests.class);
                startActivity(intent_6);
            }
        });

        bill_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_06 = new Intent(_6UserMenu.this, _9Bills.class);
                startActivity(intent_06);
            }
        });

        signout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_06 = new Intent(_6UserMenu.this, _3Login.class);
                startActivity(intent_06);
            }
        });

    }
}
