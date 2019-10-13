package com.example.bashabari;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class _13RegisterTenants extends AppCompatActivity {
    ////creating a object to hold the id of arrow button of layout 13
    private ImageView arrow_btn;
    private ImageView reg_btn;
    DatabaseReference TenantReference;
    private EditText Name, Address, Nid_no, Phone_no, Password;

    //////////////////////////on create
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__13_register_tenants);


        //getting items from xml file
        TenantReference = FirebaseDatabase.getInstance().getReference("Tenant Database");

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
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        reg_btn =  findViewById(R.id.reg_btn);

        reg_btn.setOnClickListener(new View.OnClickListener() {
            //////////////////////////////////////////////////////////////////////////////////////////////
            //////////////////////////////////////////////////////////////////////////////////////////////
            //Tick Button
            @Override
            public void onClick(View view) {
                final String name = Name.getText().toString().trim();
                final String address = Address.getText().toString().trim();
                final String nid_no = Nid_no.getText().toString().trim();
                final String phone_no = Phone_no.getText().toString().trim();
                final String password = Password.getText().toString().trim();



                //if fields are not empty:
                if( !name.isEmpty() && !address.isEmpty() && !nid_no.isEmpty() && !phone_no.isEmpty() && !password.isEmpty() ) {

                    if(password.length()<6)
                        Password.setError("Input minimum 6 character password");
                    else if(phone_no.length() != 11)
                        Phone_no.setError("Invalid phone number");
                    else
                        saveToDatabase(name, address, nid_no, phone_no, password);
                    /*
                    Intent intent1 = new Intent(_13RegisterTenants.this, _11OwnerMenu.class);
                    startActivity(intent1);
                    */
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

        /////////////////////////////////////////////////////////////////////////////////////////////////////////
        //........................................Refreshing method............................................//
        final SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.page_layout_13);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(_13RegisterTenants.this, _13RegisterTenants.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 1000);
            }
        });
    }

    public void saveToDatabase(final String name, final String address, final String nid_no, final String phone_no, final String password){
        //this will read owner's number from the file
        final String owner_no;
        owner_no = readFromFile("369pho369.txt").trim();

        try {
            TenantReference.child(phone_no).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    try {
                        tenantInfo userinf = dataSnapshot.getValue(tenantInfo.class);
                        if (phone_no.equals(userinf.getPhone_no())) {
                            Phone_no.setError("Phone number already exists");
                        }

                    } catch (Exception e) {
                        tenantInfo usrinf = new tenantInfo(address, name, nid_no, owner_no, password, phone_no);
                        String key = phone_no;
                        TenantReference.child(key).setValue(usrinf);
                        //toast for showing registration done message
                        Toast.makeText(getApplicationContext(), "Registration Done", Toast.LENGTH_LONG).show();

                        Intent intent1 = new Intent(_13RegisterTenants.this, _11OwnerMenu.class);
                        startActivity(intent1);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
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

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private String readFromFile(String File_Name){
        //This is a file reading method, which will return the string from "File_Name" file
        String st = null;
        FileInputStream fis0 = null;
        try {
            fis0 =openFileInput(File_Name);
            InputStreamReader isr = new InputStreamReader(fis0);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;

            while( (text = br.readLine()) != null ){
                sb.append(text).append("\n");
            }

            st = sb.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(fis0 != null) {
                try {
                    fis0.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return st;
        //returns string
    }


    public void hidekeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);
    }

}
