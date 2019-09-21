package com.example.bashabari;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class _13RegisterTenants extends AppCompatActivity {
    ////creating a object to hold the id of arrow button of layout 13
    private ImageView arrow_btn;
    private ImageView reg_btn;

    DatabaseReference databaseReference;
    private EditText Name, Username, Nid_no, Phone_no, Password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__13_register_tenants);


        //getting items from xml file
        databaseReference = FirebaseDatabase.getInstance().getReference("Tenant Database");

        Name = findViewById(R.id.reg_name__13);
        Username = findViewById(R.id.reg_username__13);
        Nid_no = findViewById(R.id.reg_NID_NO_13);
        Phone_no = findViewById(R.id.reg_Phn_no_13);
        Password = findViewById(R.id.reg_pass__13);


        ////getting the id of arrow button of layout 12
        arrow_btn =  findViewById(R.id.arrow_btn_13);
        arrow_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent12 = new Intent(_13RegisterTenants.this, _11OwnerMenu.class);
                startActivity(intent12);
            }
        });
        reg_btn =  findViewById(R.id.reg_btn);
        reg_btn.setOnClickListener(new View.OnClickListener() {
            //Tick Button
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(_13RegisterTenants.this, _11OwnerMenu.class);
                startActivity(intent1);

                saveToDatabase();
            }
        });
    }

    public void saveToDatabase(){
        //it will save registration data in database
        String name = Name.getText().toString().trim();
        String username = Username.getText().toString().trim();
        String nid_no = Nid_no.getText().toString().trim();
        String phone_no = Phone_no.getText().toString().trim();
        String password = Password.getText().toString().trim();
        if( !name.isEmpty() && !username.isEmpty() && !nid_no.isEmpty() && !phone_no.isEmpty() && !password.isEmpty() ) {
            tenants_info tenants = new tenants_info(name, username, nid_no, phone_no, password);

            String key = phone_no;


            databaseReference.child(key).setValue(tenants);
        }

    }
}
