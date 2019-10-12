package com.example.bashabari;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class _6UserMenu extends AppCompatActivity {
    //..................creating a object to hold the id of the contents of layout 6
    LinearLayout main_menu_layout, main_menu_layout_right;
    RelativeLayout home_layout;
    private ImageView menu_btn;
    private ImageView see_more_6;
    private TextView bill_6;
    private TextView request_6, signout_btn;
    private TextView name_title, address_title;

    private RecyclerView recyclerView;
    private noticeViewAdapter adapter;
    private List<noticeInfo> noticeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__06_user_menu);

        //.....................getting the id of the contents of layout 11
        main_menu_layout = findViewById(R.id.main_menu_layout_6);
        home_layout = findViewById(R.id.home_layout_6);
        main_menu_layout_right = findViewById(R.id.main_menu_layout_6right);
        menu_btn = findViewById(R.id.menu_btn_6);
        signout_btn = findViewById(R.id.signout_6);
        bill_6 = findViewById(R.id.bills_6);
        request_6 = findViewById(R.id.request_6);
        see_more_6 = findViewById(R.id.see_more_6);
        name_title= findViewById(R.id.nameTitle_6);
        address_title= findViewById(R.id.addressTitle_6);

        //.............this method is for showing name and address
        setContentFromDatabase();

        //..............................recycler view for showing notices
        noticeRecyclerView();
        //////////////////////////////////////////////////////////////////////////////////////////////////////////
        //......................................menu button click...............................................//
        menu_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                main_menu_layout.setVisibility(View.VISIBLE);
                home_layout.setVisibility(view.GONE);
            }
        });

        main_menu_layout_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(main_menu_layout.getVisibility() == View.VISIBLE){
                    main_menu_layout.setVisibility(View.GONE);
                    home_layout.setVisibility(view.VISIBLE);
                }
            }
        });

        see_more_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_6 = new Intent(_6UserMenu.this, _7MoreNotices.class);
                startActivity(intent_6);
            }
        });

        request_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_6 = new Intent(_6UserMenu.this, _8Requests.class);
                startActivity(intent_6);
            }
        });

        bill_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_06 = new Intent(_6UserMenu.this, _9Bills.class);
                startActivity(intent_06);
            }
        });

        signout_btn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                FileOutputStream fos0 = null;
                try {
                    // edits Stat File and sets the text to logged_out
                    fos0 = openFileOutput("369sta369.txt", MODE_PRIVATE);
                    fos0.write("logged_out".getBytes());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Intent intent_06 = new Intent(_6UserMenu.this, _3Login.class);
                startActivity(intent_06);
            }
        });

    }


    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    //...................................Notice recycler view..............................................//
    private void noticeRecyclerView() {
        recyclerView = findViewById(R.id.notice_recyclerview_6);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        noticeList = new ArrayList<>();
        adapter = new noticeViewAdapter(this, noticeList);
        recyclerView.setAdapter(adapter);

        Query query = FirebaseDatabase.getInstance().getReference("Notice Database")
                .orderByChild("phone_no").limitToLast(1)
                .equalTo(readFromFile("369t_ow369.txt").trim());
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

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //.....................................this method is for showing name and address............................................//
    private void setContentFromDatabase() {
        //This will put the name of the logged in user. it was firstly saved into the file, then here it is read from the saved file.
        name_title.setText(readFromFile("369nam369.txt"));

        //This will put the address of the logged in user. it was firstly saved into the file, then here it is read from the saved file.
        address_title.setText(readFromFile("369add369.txt"));
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
