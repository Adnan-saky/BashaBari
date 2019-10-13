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

public class _11OwnerMenu extends AppCompatActivity {
    //.......................creating a object to hold the id of the contents of layout 11
    private LinearLayout main_menu_layout,main_menu_layout_right;
    private RelativeLayout home_layout;
    private ImageView menu_btn, see_more_btn;
    private TextView add_tenant_btn, notices_btn, manage_tenant_btn,send_bills_btn,settings_btn,signout_btn;
    private TextView name_title, address_title;

    private RecyclerView recyclerView;
    private requestViewAdapter adapter;
    private List<requestInfo> requestList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__11_owner_menu);


        //////////////////////////no internet connection function call
        if(!isConnected(_11OwnerMenu.this))
            buildDialog(_11OwnerMenu.this).show();




        //......................................getting the id of the contents of layout 11
        main_menu_layout = findViewById(R.id.main_menu_layout_11);
        main_menu_layout_right = findViewById(R.id.main_menu_layout_11right);
        home_layout = findViewById(R.id.home_layout_11);
        menu_btn = findViewById(R.id.menu_btn_11);
        see_more_btn= findViewById(R.id.see_more_btn_11);
        name_title= findViewById(R.id.nameTitle_11);
        address_title= findViewById(R.id.addressTitle_11);

        //..............................getting id of menu items
        add_tenant_btn = findViewById(R.id.add_tenant_btn_11);
        notices_btn = findViewById(R.id.notices_btn_11);
        manage_tenant_btn = findViewById(R.id.manage_tenant_btn_11);
        ///////////////////////////////////////////////////////////////////////////////////////////send_bills_btn = findViewById(R.id.send_bills_btn_11);
        ///////////////////////////////////////////////////////////////////////////////////////////////settings_btn = findViewById(R.id.settings_btn_11);
        signout_btn = findViewById(R.id.signout_11);

        //..............................recycler view for showing requests
        requestRecyclerView();

        //...........................for showing name and address
        setContentFromDatabase();

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

        add_tenant_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent11 = new Intent(_11OwnerMenu.this, _13RegisterTenants.class);
                startActivity(intent11);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        notices_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent11 = new Intent(_11OwnerMenu.this, _14Notices.class);
                startActivity(intent11);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        manage_tenant_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent11 = new Intent(_11OwnerMenu.this, _17ManageTenants.class);
                startActivity(intent11);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        /*send_bills_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent11 = new Intent(_11OwnerMenu.this, _15SendBills.class);
                startActivity(intent11);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        settings_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent11 = new Intent(_11OwnerMenu.this, _16Settings.class);
                startActivity(intent11);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });*/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        see_more_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent11 = new Intent(_11OwnerMenu.this, _12MoreRequests.class);
                startActivity(intent11);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
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

                Intent intent11 = new Intent(_11OwnerMenu.this, _3Login.class);
                startActivity(intent11);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
        /////////////////////////////////////////////////////////////////////////////////////////////////////////
        //........................................Refreshing method............................................//
        final SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.page_layout_11);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(_11OwnerMenu.this, _11OwnerMenu.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 1000);
            }
        });

    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    //...................................Request recycler view........................................//
    private void requestRecyclerView() {
        recyclerView = findViewById(R.id.request_recyclerview_11);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        requestList = new ArrayList<>();
        adapter = new requestViewAdapter(this, requestList);
        recyclerView.setAdapter(adapter);

        Query query = FirebaseDatabase.getInstance().getReference("Requests Database")
                .orderByChild("owner").limitToLast(1)
                .equalTo(readFromFile("369pho369.txt").trim());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                requestList.clear();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        requestInfo req = snapshot.getValue(requestInfo.class);
                        requestList.add(req);
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
        builder.setMessage("You need to have Mobile Data or wifi to access this. ");

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
