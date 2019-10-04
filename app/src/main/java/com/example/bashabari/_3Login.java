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
    //variable to store views
    public EditText phoneField, passwordField;
    public TextView registerButton;
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
        phoneField = findViewById(R.id.phone_no_3);
        passwordField = findViewById(R.id.password__3);
        registerButton = findViewById(R.id.register);
        next_btn_3 = findViewById(R.id.next_btn_3);
        login_as_owner_3 = findViewById(R.id.login_as_owner_3);

        //database reference
        ownerRef = FirebaseDatabase.getInstance().getReference().child("Owner Database");
        tenantRef = FirebaseDatabase.getInstance().getReference().child("Tenant Database");

        ///////////////////////////////////////////////////////////////////
        //////////////////////////////////////////////registerButton button click
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (login_as_owner_3.isChecked()) {
                    Intent intent3 = new Intent(_3Login.this, _4Register.class);
                    startActivity(intent3);
                }
                else
                    Toast.makeText(_3Login.this, "You can only register as an owner", Toast.LENGTH_LONG).show();

            }
        });

        /////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////tick button click
        next_btn_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //getting items from phone and password field
                final String getPhoneKey, getPass;
                getPhoneKey = phoneField.getText().toString().trim();
                getPass = passwordField.getText().toString().trim();

                //for owner
                if (login_as_owner_3.isChecked()) {
                    if (getPhoneKey.isEmpty())
                        phoneField.setError("Input your Phone number.");
                    else if (getPass.isEmpty())
                        passwordField.setError("Input your Password");
                    else
                        ownerLogin(getPhoneKey, getPass);
                }

                //for tenant
                else {
                    if (getPhoneKey.isEmpty())
                        phoneField.setError("Input your Phone number.");
                    else if (getPass.isEmpty())
                        passwordField.setError("Input your Password");
                    else
                        tenantLogin(getPhoneKey, getPass);
                }
            }
        });
    }

    /////////////////////////////////////////////////////////////////////
    /////////////////////////////owner login/////////////////////////////
    public void ownerLogin(final String getPhoneKey, final String getPass) {
        try {
            ownerRef.child(getPhoneKey).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    try {
                        userInfo userinf = dataSnapshot.getValue(userInfo.class);
                        if (getPass.equals(userinf.getPassword()) && getPhoneKey.equals(userinf.getPhone_no())) {
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

    /////////////////////////////////////////////////////////////////////////
    ///////////////////////////tenant login//////////////////////////////////
    public void tenantLogin(final String getPhoneKey, final String getPass) {
        try {
            tenantRef.child(getPhoneKey).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    try {
                        userInfo userinf = dataSnapshot.getValue(userInfo.class);
                        if (getPass.equals(userinf.getPassword()) && getPhoneKey.equals(userinf.getPhone_no())) {
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

