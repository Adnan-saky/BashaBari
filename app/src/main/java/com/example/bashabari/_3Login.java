package com.example.bashabari;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

public class _3Login extends AppCompatActivity {

    private TextView register;
    private ImageView next_btn_3;
    private CheckBox login_as_owner_3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__03_login);

        register =  findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3 = new Intent(_3Login.this, _4Register.class);
                startActivity(intent3);
            }
        });



        next_btn_3 =  findViewById(R.id.next_btn_3);
        next_btn_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login_as_owner_3 =  findViewById(R.id.login_as_owner_3);
                if (login_as_owner_3.isChecked()) {
                    Intent intent_3 = new Intent(_3Login.this, _11OwnerMenu.class);
                    startActivity(intent_3);
                }
                else
                {
                    Intent intent_03 = new Intent(_3Login.this, _6UserMenu.class);
                    startActivity(intent_03);
                }
            }
        });
    }
}
