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

public class _3Login extends AppCompatActivity {

    public EditText key, password;
    public TextView register;
    public ImageView next_btn_3;
    private CheckBox login_as_owner_3;
    public DatabaseReference ownerRef;
    public DatabaseReference tenantRef;

    //////////////////////////on create
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__03_login);

        //getting items from xml file
        key = findViewById(R.id.phone_no_3);
        password = findViewById(R.id.password__3);
        register = findViewById(R.id.register);
        next_btn_3 = findViewById(R.id.next_btn_3);
        login_as_owner_3 = findViewById(R.id.login_as_owner_3);

        //database reference
        ownerRef = FirebaseDatabase.getInstance().getReference().child("Owner Database");
        tenantRef = FirebaseDatabase.getInstance().getReference().child("Tenant Database");

        ///////////////////////////////////////////////////////////////////
        //////////////////////////////////////////////register button click
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3 = new Intent(_3Login.this, _4Register.class);
                startActivity(intent3);
            }
        });

        /////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////tick button click
        next_btn_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (login_as_owner_3.isChecked()) {
                    //for owner
                    final String getKey, getpass;
                    getKey = key.getText().toString().trim();
                    getpass = password.getText().toString().trim();

                    if (getKey.isEmpty())
                        key.setError("Input your Phone number.");
                    else if (getpass.isEmpty())
                        password.setError("Input your Password");
                    else
                        ownerLogin(getKey, getpass);
                }

                else {
                    //for tenant
                    final String getKey, getpass;
                    getKey = key.getText().toString().trim();
                    getpass = password.getText().toString().trim();

                    if (getKey.isEmpty())
                        key.setError("Input your Phone number.");
                    else if (getpass.isEmpty())
                        password.setError("Input your Password");
                    else
                        tenantLogin(getKey, getpass);
                }
            }
        });
    }


    /////////////////////////////owner login/////////////////////////////////
    public void ownerLogin(final String getKey, final String getpass) {
        try {
            ownerRef.child(getKey).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    try {
                        userInfo userinf = dataSnapshot.getValue(userInfo.class);
                        if (getpass.equals(userinf.getPassword()) && getKey.equals(userinf.getPhone_no())) {
                            Toast.makeText(_3Login.this, "Login Successful", Toast.LENGTH_LONG).show();

                            Intent intent_3 = new Intent(_3Login.this, _11OwnerMenu.class);
                            startActivity(intent_3);
                        }
                    } catch (Exception e) {
                        Toast.makeText(_3Login.this, "Invalid Phone no or Password", Toast.LENGTH_LONG).show();
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    ///////////////////////////tenant login//////////////////////////////////
    public void tenantLogin(final String getKey, final String getpass) {
        try {
            tenantRef.child(getKey).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    try {
                        userInfo userinf = dataSnapshot.getValue(userInfo.class);
                        if (getpass.equals(userinf.getPassword()) && getKey.equals(userinf.getPhone_no())) {
                            Toast.makeText(_3Login.this, "Login Successful", Toast.LENGTH_LONG).show();

                            Intent intent_3 = new Intent(_3Login.this, _6UserMenu.class);
                            startActivity(intent_3);
                        }
                    } catch (Exception e) {
                        Toast.makeText(_3Login.this, "Invalid Phone no or Password", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

