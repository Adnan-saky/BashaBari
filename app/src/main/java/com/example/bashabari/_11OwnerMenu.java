package com.example.bashabari;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class _11OwnerMenu extends AppCompatActivity {
    ////creating a object to hold the id of the contents of layout 11
    private LinearLayout main_menu_layout,main_menu_layout_right;
    private RelativeLayout home_layout;
    private ImageView menu_btn, see_more_btn;
    private TextView add_tenant_btn, notices_btn, manage_tenant_btn,send_bills_btn,settings_btn,signout_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__11_owner_menu);

        ////getting the id of the contents of layout 11
        main_menu_layout = findViewById(R.id.main_menu_layout_11);
        main_menu_layout_right = findViewById(R.id.main_menu_layout_11right);
        home_layout = findViewById(R.id.home_layout_11);
        menu_btn = findViewById(R.id.menu_btn_11);
        see_more_btn= findViewById(R.id.see_more_btn_11);

        ///getting id of menu items
        add_tenant_btn = findViewById(R.id.add_tenant_btn_11);
        notices_btn = findViewById(R.id.notices_btn_11);
        manage_tenant_btn = findViewById(R.id.manage_tenant_btn_11);
        send_bills_btn = findViewById(R.id.send_bills_btn_11);
        settings_btn = findViewById(R.id.settings_btn_11);
        signout_btn = findViewById(R.id.signout_11);


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

        add_tenant_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent11 = new Intent(_11OwnerMenu.this, _13RegisterTenants.class);
                startActivity(intent11);
            }
        });

        notices_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent11 = new Intent(_11OwnerMenu.this, _14Notices.class);
                startActivity(intent11);
            }
        });

        manage_tenant_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent11 = new Intent(_11OwnerMenu.this, _17ManageTenants.class);
                startActivity(intent11);
            }
        });

        send_bills_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent11 = new Intent(_11OwnerMenu.this, _15SendBills.class);
                startActivity(intent11);
            }
        });

        settings_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent11 = new Intent(_11OwnerMenu.this, _16Settings.class);
                startActivity(intent11);
            }
        });

        see_more_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent11 = new Intent(_11OwnerMenu.this, _12MoreRequests.class);
                startActivity(intent11);
            }
        });

        signout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent11 = new Intent(_11OwnerMenu.this, _3Login.class);
                startActivity(intent11);
            }
        });

    }
}
