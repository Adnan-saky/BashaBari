package com.example.bashabari;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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

        //////////////////////////no internet connection function call
        if(!isConnected(_6UserMenu.this))
            buildDialog(_6UserMenu.this).show();




        //.....................getting the id of the contents of layout 11
        main_menu_layout = findViewById(R.id.main_menu_layout_6);
        home_layout = findViewById(R.id.home_layout_6);
        main_menu_layout_right = findViewById(R.id.main_menu_layout_6right);
        menu_btn = findViewById(R.id.menu_btn_6);
        signout_btn = findViewById(R.id.signout_6);
        /////////////////////////////////////////////////////////////////////////////////////////bill_6 = findViewById(R.id.bills_6);
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
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        request_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_6 = new Intent(_6UserMenu.this, _8Requests.class);
                startActivity(intent_6);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
/*...............................................................................................................................
        bill_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_06 = new Intent(_6UserMenu.this, _9Bills.class);
                startActivity(intent_06);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
............................................................................................................*/
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
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
        /////////////////////////////////////////////////////////////////////////////////////////////////////////
        //........................................Refreshing method............................................//
        final SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.page_layout_6);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(_6UserMenu.this, _6UserMenu.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 1000);
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


    ///////////////// no internet  connection

    public boolean isConnected(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();

        if (netinfo != null && netinfo.isConnectedOrConnecting()) {
            android.net.NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if((mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting())) return true;
            else
                return false;
        } else
            return false;
    }

    public AlertDialog.Builder buildDialog(Context c) {

        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle("No Internet Connection");
        builder.setMessage("You need to have Mobile Data or wifi to access this.");

       /* builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                finish();
            }
        });
*/
        return builder;
    }


}
