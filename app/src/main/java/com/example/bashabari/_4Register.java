package com.example.bashabari;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class _4Register extends AppCompatActivity {
    ///////////////////////variable declaration
    private ImageView next_btn;
    DatabaseReference databaseReference;
    private EditText Name, Address, Nid_no, Phone_no, Password;

    //////////////////////////on create
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__04_register);

        //getting items from xml file
        databaseReference = FirebaseDatabase.getInstance().getReference("Owner Database");
        next_btn =  findViewById(R.id.next_btn_4);
        Name = findViewById(R.id.reg_name__4);
        Address = findViewById(R.id.reg_address__4);
        Nid_no = findViewById(R.id.reg_NID_NO_4);
        Phone_no = findViewById(R.id.reg_Phn_no_4);
        Password = findViewById(R.id.reg_pass__4);

        //next button click
        next_btn.setOnClickListener(new View.OnClickListener() {
            //Tick Button
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(_4Register.this, _3Login.class);
                startActivity(intent1);

                saveToDatabase();
            }
        });
    }

    public void saveToDatabase(){
        //it will save registration data in database
        String name = Name.getText().toString().trim();
        String address = Address.getText().toString().trim();
        String nid_no = Nid_no.getText().toString().trim();
        String phone_no = Phone_no.getText().toString().trim();
        String password = Password.getText().toString().trim();
        if( !name.isEmpty() && !address.isEmpty() && !nid_no.isEmpty() && !phone_no.isEmpty() && !password.isEmpty() ) {
            userInfo usrinf = new userInfo(name, address, nid_no, phone_no, password);

            String key = phone_no;


            databaseReference.child(key).setValue(usrinf);
        }

    }
}



