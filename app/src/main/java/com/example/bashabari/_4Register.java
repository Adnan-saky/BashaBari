package com.example.bashabari;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class _4Register extends AppCompatActivity {
    //...............................variable declaration for views
    private ImageView next_btn;
    private TextView back_login_btn;
    DatabaseReference ownerReference;
    private EditText Name, Address, Nid_no, Phone_no, Password;

    //..................................on create
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__04_register);


        //.........................getting items from xml file
        ownerReference = FirebaseDatabase.getInstance().getReference("Owner Database");

        next_btn = findViewById(R.id.next_btn_4);
        back_login_btn = findViewById(R.id.back_login_4);
        Name = findViewById(R.id.reg_name__4);
        Address = findViewById(R.id.reg_address__4);
        Nid_no = findViewById(R.id.reg_NID_NO_4);
        Phone_no = findViewById(R.id.reg_Phn_no_4);
        Password = findViewById(R.id.reg_pass__4);

        /////////////////////////////////////////////////////////////////////////////////////////////////////
        //........................................back button click........................................//
        back_login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(_4Register.this, _3Login.class);
                startActivity(intent2);
            }
        });

        /////////////////////////////////////////////////////////////////////////////////////////////////////
        //.........................................next button click.......................................//
        next_btn.setOnClickListener(new View.OnClickListener() {
            //Tick Button click
            @Override
            public void onClick(View view) {
                final String name = Name.getText().toString().trim();
                final String address = Address.getText().toString().trim();
                final String nid_no = Nid_no.getText().toString().trim();
                final String phone_no = Phone_no.getText().toString().trim();
                final String password = Password.getText().toString().trim();

                //................data is inputted
                if( !name.isEmpty() && !address.isEmpty() && !nid_no.isEmpty() && !phone_no.isEmpty() && !password.isEmpty() ) {


                            if(password.length()<6)
                                Password.setError("Input minimum 6 character password");
                            else if(phone_no.length() != 11)
                                Phone_no.setError("Invalid phone number");
                            else
                                saveToDatabase(name, address, nid_no, phone_no, password);
                }

                //..................else fields are empty
                else{
                    Toast.makeText(getApplicationContext(),"Empty Input Field",Toast.LENGTH_SHORT).show();

                    if(name.isEmpty())
                        Name.setError("Input your name.");
                    else if(address.isEmpty())
                        Address.setError("Input your address");
                    else if(nid_no.isEmpty())
                        Nid_no.setError("Input your National ID Number");
                    else if(password.isEmpty())
                        Password.setError("Input a passwordField");
                    else if(phone_no.isEmpty())
                        Phone_no.setError("Input your phone number");
                }

            }
        });
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //.......................................Saving info to database.............................................//
    public void saveToDatabase(final String name, final String address, final String nid_no, final String phone_no, final String password) {
        try {
            ownerReference.child(phone_no).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    try {
                        ownerInfo userinf = dataSnapshot.getValue(ownerInfo.class);
                        if (phone_no.equals(userinf.getPhone_no())) {
                            Phone_no.setError("Phone number already exists");
                        }

                    } catch (Exception e) {
                        ownerInfo usrinf = new ownerInfo(address, name, nid_no, password, phone_no);
                        String key = phone_no;
                        ownerReference.child(key).setValue(usrinf);
                        //toast for showing registration done message
                        Toast.makeText(getApplicationContext(), "Registration Done", Toast.LENGTH_LONG).show();

                        Intent intent1 = new Intent(_4Register.this, _3Login.class);
                        startActivity(intent1);
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

    public void hidekeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);
    }


}



