package com.example.bashabari;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
    ///////////////////////variable declaration
    private ImageView next_btn;
    private TextView back_login_btn;
    DatabaseReference databaseReference;
    private EditText Name, Address, Nid_no, Phone_no, Password;

    //////////////////////////on create
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__04_register);

        //getting items from xml file
        databaseReference = FirebaseDatabase.getInstance().getReference("Owner Database");
        next_btn = findViewById(R.id.next_btn_4);
        back_login_btn = findViewById(R.id.back_login_4);
        Name = findViewById(R.id.reg_name__4);
        Address = findViewById(R.id.reg_address__4);
        Nid_no = findViewById(R.id.reg_NID_NO_4);
        Phone_no = findViewById(R.id.reg_Phn_no_4);
        Password = findViewById(R.id.reg_pass__4);

        /////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////back button click
        back_login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(_4Register.this, _3Login.class);
                startActivity(intent2);
            }
        });

        /////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////next button click
        next_btn.setOnClickListener(new View.OnClickListener() {
            //Tick Button click
            @Override
            public void onClick(View view) {
                final String name = Name.getText().toString().trim();
                final String address = Address.getText().toString().trim();
                final String nid_no = Nid_no.getText().toString().trim();
                final String phone_no = Phone_no.getText().toString().trim();
                final String password = Password.getText().toString().trim();

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//<<<<<<< HEAD
                        userInfo usrinf = dataSnapshot.getValue(userInfo.class);
                        if(phone_no.equals(usrinf.getPhone_no())){
                            Toast.makeText(getApplicationContext(),"Phone Already number exists",Toast.LENGTH_LONG).show();
//=======
                        try {
                            userInfo usrinf = dataSnapshot.getValue(userInfo.class);
                            if (!phone_no.equals(usrinf.getPhone_no())) {
                                //if fields are not empty:
                                if (!name.isEmpty() && !address.isEmpty() && !nid_no.isEmpty() && !phone_no.isEmpty() && !password.isEmpty()) {

                                    saveToDatabase(address, name, nid_no, password, phone_no);

                                    Intent intent1 = new Intent(_4Register.this, _3Login.class);
                                    startActivity(intent1);
                                }

                                //else fields are empty
                                else {
                                    Toast.makeText(getApplicationContext(), "Empty Input Field", Toast.LENGTH_SHORT).show();

                                    if (name.isEmpty())
                                        Name.setError("Input your name.");
                                    else if (address.isEmpty())
                                        Address.setError("Input your address");
                                    else if (nid_no.isEmpty())
                                        Nid_no.setError("Input your National ID Number");
                                    else if (password.isEmpty())
                                        Password.setError("Input a password");
                                    else if (phone_no.isEmpty())
                                        Phone_no.setError("Input your address");
                                }
                            }
                            else{
                                Toast.makeText(getApplicationContext(), "Phone Already number exists", Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
//>>>>>>> 117173720d04c30e61b5d38de1534bfc8b2d593f
                        }
                    }
                    @Override
//<<<<<<< HEAD
                    public void onCancelled(@Nullable DatabaseError databaseError) {

//=======
                    public void onCancelled(@NonNull DatabaseError databaseError) {
//>>>>>>> 117173720d04c30e61b5d38de1534bfc8b2d593f
                    }
                });


            }
        });
    }

    public void saveToDatabase(String name, String address, String nid_no, String phone_no, String password) {
        //it will save registration data in database
        userInfo usrinf = new userInfo(address, name, nid_no, password, phone_no);

        String key = phone_no;
        databaseReference.child(key).setValue(usrinf);

        //toast for showing registration done message
        Toast.makeText(getApplicationContext(), "Registration Done", Toast.LENGTH_LONG).show();

    }
}



