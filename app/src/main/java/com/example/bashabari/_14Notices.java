package com.example.bashabari;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class _14Notices extends AppCompatActivity {
    /////variable to store views
    private ImageView back_arrow_btn;
    private ImageView add_notice_btn;
    private EditText edit_nt_field;
    public DatabaseReference noticeRef;

    private RecyclerView recyclerView;
    private noticeViewAdapter adapter;
    private List<noticeInfo> noticeList;


    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    //........................................onCreate method............................................//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__14_notices);

        //..............................recycler view for showing notices
        noticeRecyclerView();
        //..............................getting items from xml file
        back_arrow_btn = findViewById(R.id.arrow_btn_14);
        add_notice_btn = findViewById(R.id.add_notice_btn_14);
        edit_nt_field = findViewById(R.id.edit_notice_box_14);
        //...............................database reference
        noticeRef = FirebaseDatabase.getInstance().getReference().child("Notice Database");

        /////////////////////////////////////////////////////////////////////////////////////////////////////////
        //........................................back button click............................................//
        back_arrow_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent12 = new Intent(_14Notices.this, _11OwnerMenu.class);
                startActivity(intent12);
            }
        });

        /////////////////////////////////////////////////////////////////////////////////////////////////////////
        //..................................."Add Notice" button click........................................//
        add_notice_btn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                final String text_req, phone_no, name, date, solveStat;
                text_req = edit_nt_field.getText().toString().trim();
                name = readFromFile("369nam369.txt").trim();
                phone_no = readFromFile("369pho369.txt").trim();

                //.............................If the field is empty
                if (text_req.isEmpty())
                    edit_nt_field.setError("The request field is empty");
                    //...........................If the field is not empty
                else{
                    date = getTodaysDate();
                    saveToDatabase(date, name, phone_no, text_req);

                }
            }
        });
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    //...................................Notice recycler view..............................................//
    private void noticeRecyclerView() {
        recyclerView = findViewById(R.id.notice_recyclerview_14);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        noticeList = new ArrayList<>();
        adapter = new noticeViewAdapter(this, noticeList);
        recyclerView.setAdapter(adapter);

        Query query = FirebaseDatabase.getInstance().getReference("Notice Database")
                .orderByChild("phone_no")
                .equalTo(readFromFile("369pho369.txt").trim());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                noticeList.clear();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        noticeInfo not = snapshot.getValue(noticeInfo.class);
                        noticeList.add(not);
                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //..............................................Saving  info to database.....................................................
    public void saveToDatabase(final String date, final String name, final String phone_no, final String text_req) {

        noticeRef.child(phone_no).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                noticeInfo usrinf = new noticeInfo(date, name, phone_no, text_req);
                noticeRef.push().setValue(usrinf); //will generate random key as database child

                //toast for showing registration done message
                Toast.makeText(getApplicationContext(), "Notice has been sent", Toast.LENGTH_LONG).show();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //...............................................getting date.............................................................
    @RequiresApi(api = Build.VERSION_CODES.N)
    private String getTodaysDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy 'at' h:mm a");
        Date date = new Date();
        String dateString = dateFormat.format(date);

        return dateString;
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
}
