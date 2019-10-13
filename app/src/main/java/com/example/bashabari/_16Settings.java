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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class _16Settings extends AppCompatActivity {
    ////creating a object to hold the id of arrow button of layout 16
    private ImageView arrow_btn, tick_button;
    private DatabaseReference ownerRef;
    private EditText reg_name, reg_address, reg_NID_NO, reg_pass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__16_settings);

        ////getting the id of arrow button of layout 16
        tick_button = findViewById(R.id.tick_btn_16);
        arrow_btn =  findViewById(R.id.arrow_btn_16);
        reg_name = findViewById(R.id.reg_name__16);
        reg_address = findViewById(R.id.reg_address__16);
        reg_NID_NO = findViewById(R.id.reg_NID_NO_16);
        reg_pass = findViewById(R.id.reg_pass__16);
        ownerRef = FirebaseDatabase.getInstance().getReference().child("Owner Database");

        //.........loading the owner info
        ownerInfoLoading(readFromFile("369pho369.txt").trim());



        arrow_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent12 = new Intent(_16Settings.this, _11OwnerMenu.class);
                startActivity(intent12);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        tick_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = reg_name.getText().toString().trim();
                final String address = reg_address.getText().toString().trim();
                final String nid_no = reg_NID_NO.getText().toString().trim();
                final String password = reg_pass.getText().toString().trim();
                final String phone_no = readFromFile(readFromFile("369pho369.txt").trim());
                saveToDatabase(name, address, nid_no, phone_no, password);

                }
        });
        /////////////////////////////////////////////////////////////////////////////////////////////////////////
        //........................................Refreshing method............................................//
        final SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.page_layout_16);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(_16Settings.this, _16Settings.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 1000);
            }
        });
    }



    public void hidekeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////
    //...................................owner login  info loading.......................................//
    public void ownerInfoLoading(final String getPhoneKey) {
        try {
            ownerRef.child(getPhoneKey).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    try {

                        ownerInfo userinf = dataSnapshot.getValue(ownerInfo.class);
                        if (getPhoneKey.equals(userinf.getPhone_no())) {
                            reg_name.setText(userinf.getName());
                            reg_address.setText(userinf.getAddress());
                            reg_NID_NO.setText(userinf.getNid_no());
                            reg_pass.setText(userinf.getPassword());
                            //Toast.makeText(_16Settings.this, "Invalid Phone no or Password", Toast.LENGTH_LONG).show();
                        }

                    } catch (Exception e) {

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

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //.......................................Saving info to database.............................................//
    public void saveToDatabase(final String name, final String address, final String nid_no, final String phone_no, final String password) {
        try {
            ownerRef.child(phone_no).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    try {
                        ownerInfo userinf = dataSnapshot.getValue(ownerInfo.class);
                        if (phone_no.equals(userinf.getPhone_no())) {
                            ownerInfo usrinf = new ownerInfo(address, name, nid_no, password, phone_no);
                            String key = phone_no;
                            ownerRef.child(key).setValue(usrinf);
                            //toast for showing registration done message
                            Toast.makeText(getApplicationContext(), "Information Saved", Toast.LENGTH_LONG).show();

                            Intent intent = new Intent(_16Settings.this, _11OwnerMenu.class);
                            startActivity(intent);
                            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        }

                    } catch (Exception e) {

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

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //............................Reading database info from locally saved file..................................//
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

}
