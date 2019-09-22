package com.example.bashabari;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.acl.Owner;

public class _3Login extends AppCompatActivity {

    public EditText key,password;
    public TextView register;
    public ImageView next_btn_3;
    private CheckBox login_as_owner_3;
    public DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__03_login);

        key = findViewById(R.id.username__3);
        password = findViewById(R.id.password__3);
        ref = FirebaseDatabase.getInstance().getReference().child("Owner Database");


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

                    Owner_login_meth();
                }
                else
                {
                    Intent intent_03 = new Intent(_3Login.this, _6UserMenu.class);
                    startActivity(intent_03);
                }
            }
        });
    }

    public void Owner_login_meth() {
        final String /*getKey,*/ getpass;


        //getKey = key.getText().toString();
        getpass = password.getText().toString();
        //if (ref.child(getpass) != null)

        {
            ref.child(getpass).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    userInfo userinf = dataSnapshot.getValue(userInfo.class);

                    if (getpass.equals(userinf.getPassword()) /*&& getKey.equals(userinf.getPhone_no())*/) {
                        Toast.makeText(_3Login.this, "Login Successful", Toast.LENGTH_LONG).show();

                        Intent intent_3 = new Intent(_3Login.this, _11OwnerMenu.class);
                        startActivity(intent_3);

                    } else
                        Toast.makeText(_3Login.this, "Invalid Phone No. Password", Toast.LENGTH_LONG).show();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }

            });
    }

    }



}

