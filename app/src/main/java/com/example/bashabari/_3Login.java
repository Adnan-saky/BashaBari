package com.example.bashabari;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class _3Login extends AppCompatActivity {
    //file name declaration
    public static final String Stat_File = "369sta369.txt";
    public static final String Address_File = "369add369.txt";
    public static final String Name_File = "369nam369.txt";
    public static final String Nid_File = "369nid369.txt";
    public static final String Password_File = "369pas369.txt";
    public static final String Phone_File = "369pho369.txt";

    /////variable to store views
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
                            //passes userdata which we got from database
                            saveLoginInfoToFile(userinf.getAddress(), userinf.getName(), userinf.getNid_no(), userinf.getPassword(), userinf.getPhone_no());

                            Intent intent_3 = new Intent(_3Login.this, _11OwnerMenu.class);
                            startActivity(intent_3);

                            //loading condition for owner(written in the file)
                            FileOutputStream fos0 = null;
                            try {
                                fos0 = openFileOutput(Stat_File, MODE_PRIVATE);
                                fos0.write("logged_in_owner".getBytes());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
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
                            //passes userdata which we got from database
                            saveLoginInfoToFile(userinf.getAddress(), userinf.getName(), userinf.getNid_no(), userinf.getPassword(), userinf.getPhone_no());

                            Intent intent_3 = new Intent(_3Login.this, _6UserMenu.class);
                            startActivity(intent_3);

                            //loading condition for tenant (written in the file)
                            FileOutputStream fos0 = null;
                            try {
                                fos0 = openFileOutput(Stat_File, MODE_PRIVATE);
                                fos0.write("logged_in_tenant".getBytes());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
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


    public void saveLoginInfoToFile(String address, String name, String nid_no, String password, String phone_no){
        // will save data into the files
        FileOutputStream fos1 = null;
        try {
            fos1 = openFileOutput(Address_File, MODE_PRIVATE);
            fos1.write(address.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }

        FileOutputStream fos2 = null;
        try {
            fos2 = openFileOutput(Name_File, MODE_PRIVATE);
            fos2.write(name.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }

        FileOutputStream fos3 = null;
        try {
            fos3 = openFileOutput(Nid_File, MODE_PRIVATE);
            fos3.write(nid_no.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }

        FileOutputStream fos4 = null;
        try {
            fos4 = openFileOutput(Password_File, MODE_PRIVATE);
            fos4.write(password.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }

        FileOutputStream fos5 = null;
        try {
            fos5 = openFileOutput(Phone_File, MODE_PRIVATE);
            fos5.write(phone_no.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}

