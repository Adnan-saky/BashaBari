package com.example.bashabari;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class _13RegisterTenants extends AppCompatActivity {
    ////creating a object to hold the id of arrow button of layout 13
    private ImageView arrow_btn;
    private ImageView reg_btn;

    DatabaseReference databaseReference;
    private EditText Name, Address, Nid_no, Phone_no, Password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__13_register_tenants);


        //getting items from xml file
        databaseReference = FirebaseDatabase.getInstance().getReference("Tenant Database");

        Name = findViewById(R.id.reg_name__13);
        Address = findViewById(R.id.reg_address__13);
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
            //////////////////////////////////////////////////////////////////////////////////////////////
            //////////////////////////////////////////////////////////////////////////////////////////////
            //Tick Button
            @Override
            public void onClick(View view) {
                String name = Name.getText().toString().trim();
                String address = Address.getText().toString().trim();
                String nid_no = Nid_no.getText().toString().trim();
                String phone_no = Phone_no.getText().toString().trim();
                String password = Password.getText().toString().trim();



                //if fields are not empty:
                if( !name.isEmpty() && !address.isEmpty() && !nid_no.isEmpty() && !phone_no.isEmpty() && !password.isEmpty() ) {
                    Intent intent1 = new Intent(_13RegisterTenants.this, _11OwnerMenu.class);
                    startActivity(intent1);

                    saveToDatabase(name, address, nid_no, phone_no, password);
                }

                //else fields are empty
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
                        Phone_no.setError("Input your address");
                }



            }
        });
    }

    public void saveToDatabase(String name, String address, String nid_no, String phone_no, String password){

    }
}
