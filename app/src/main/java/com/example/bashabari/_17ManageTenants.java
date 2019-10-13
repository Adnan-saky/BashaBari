package com.example.bashabari;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

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
import java.util.List;

public class _17ManageTenants extends AppCompatActivity {
    ////creating a object to hold the id of arrow button of layout 13
    private ImageView arrow_btn;
    public DatabaseReference tenantRef;

    private RecyclerView recyclerView;
    private mngTenantViewAdapter adapter;
    private List<tenantInfo> tenantList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__17_manage_tenants);
        //...............................database reference
        tenantRef = FirebaseDatabase.getInstance().getReference().child("Tenant Database");

        //..............................recycler view for showing notices
        tenantRecyclerView();

        ////getting the id of arrow button of layout 12
        arrow_btn =  findViewById(R.id.arrow_btn_17);
        arrow_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent12 = new Intent(_17ManageTenants.this, _11OwnerMenu.class);
                startActivity(intent12);
            }
        });

        /////////////////////////////////////////////////////////////////////////////////////////////////////////
        //........................................Refreshing method............................................//
        final SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.page_layout_17);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(_17ManageTenants.this, _17ManageTenants.class);
                        startActivity(intent);
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 1000);
            }
        });
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    //...................................Notice recycler view..............................................//
    private void tenantRecyclerView() {
        recyclerView = findViewById(R.id.tenant_recyclerview_17);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        tenantList = new ArrayList<>();
        adapter = new mngTenantViewAdapter(this, tenantList);
        recyclerView.setAdapter(adapter);

        Query query = FirebaseDatabase.getInstance().getReference("Tenant Database")
                .orderByChild("owner")
                .equalTo(readFromFile("369pho369.txt").trim());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tenantList.clear();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        tenantInfo ten = snapshot.getValue(tenantInfo.class);
                        tenantList.add(ten);
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
