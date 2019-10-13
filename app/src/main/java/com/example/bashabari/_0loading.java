package com.example.bashabari;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class _0loading extends AppCompatActivity {

    private static int loadingtime=5000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__0loading);

        //Toast.makeText(getApplicationContext(), "please wait for a moment..", Toast.LENGTH_LONG).show();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                startingAppActivity();
                finish();
            }
        },loadingtime);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void startingAppActivity() {
        try {
            //will throw exception if first time installed
            String cond = readFromFile("369sta369.txt").trim();

            if (cond.equals("logged_out".trim())) {
                Intent loading = new Intent(_0loading.this, _3Login.class);
                startActivity(loading);
            }
            else if (cond.equals("logged_in_owner".trim())) {
                Intent loading = new Intent(_0loading.this, _11OwnerMenu.class);
                startActivity(loading);
            }
            else if (cond.equals("logged_in_tenant".trim())) {
                Intent loading = new Intent(_0loading.this, _6UserMenu.class);
                startActivity(loading);
            }

        }catch (Exception e){
            //First time login. so the state file is not created which throws an exception
            Intent loading = new Intent(_0loading.this, _1Welcome.class);
            startActivity(loading);
        }

    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
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
    }
}
