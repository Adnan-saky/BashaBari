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

public class _8Requests extends AppCompatActivity {
    /////variable to store views
    private ImageView back_arrow_btn;
    private ImageView add_rq_btn;
    private EditText edit_rq_field;
    public DatabaseReference requestRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__08_requests);

        //..............................getting items from xml file
        back_arrow_btn = findViewById(R.id.back_arrow_btn_8);
        add_rq_btn = findViewById(R.id.add_rq_btn_8);
        edit_rq_field = findViewById(R.id.edit_rq_box_8);
        //...............................database reference
        requestRef = FirebaseDatabase.getInstance().getReference().child("Requests Database");

        /////////////////////////////////////////////////////////////////////////////////////////////////////////
        //........................................back button click............................................//
        back_arrow_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent8 = new Intent(_8Requests.this, _6UserMenu.class);
                startActivity(intent8);
            }
        });

        /////////////////////////////////////////////////////////////////////////////////////////////////////////
        //..................................."Add Request" button click........................................//
        add_rq_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String text_req,owner_no, phone_no, name, date, solveStat;
                text_req = edit_rq_field.getText().toString().trim();
                name = readFromFile("369nam369.txt").trim();
                phone_no = readFromFile("369pho369.txt").trim();
                owner_no = readFromFile("369t_ow369.txt").trim();

                //.............................If the field is empty
                if (text_req.isEmpty())
                    edit_rq_field.setError("The request field is empty");
                //...........................If the field is not empty
                else{
                    date = getTodaysDate();
                    solveStat = "No";
                    saveToDatabase(date, name, owner_no, phone_no, solveStat, text_req);

                }
            }
        });
    }

    public void saveToDatabase(final String date, final String name, final String owner_no, final String phone_no, final String solveStat, final String text_req) {

                requestRef.child(phone_no).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        requestInfo usrinf = new requestInfo(date, name, owner_no, phone_no, solveStat, text_req);
                        requestRef.push().setValue(usrinf); //will generate random key as database child

                        //toast for showing registration done message
                        Toast.makeText(getApplicationContext(), "Request has been sent", Toast.LENGTH_LONG).show();
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
        }

    private String getTodaysDate() {
        String date=null;
        //function for retrieving date yet not written
        return date;
    }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//...............................................Read From File.............................................................
    private String readFromFile(String File_Name) {
        //This is a file reading method, which will return the string from "File_Name" file
        String st = null;
        FileInputStream fis0 = null;
        try {
            fis0 = openFileInput(File_Name);
            InputStreamReader isr = new InputStreamReader(fis0);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;

            while ((text = br.readLine()) != null) {
                sb.append(text).append("\n");
            }

            st = sb.toString();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fis0 != null) {
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
