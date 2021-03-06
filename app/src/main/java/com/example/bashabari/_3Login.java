package com.example.bashabari;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.FileOutputStream;

public class _3Login extends AppCompatActivity {
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    //.................................file name declaration.........................................//
    public static final String Stat_File = "369sta369.txt";
    public static final String Address_File = "369add369.txt";
    public static final String Name_File = "369nam369.txt";
    public static final String Nid_File = "369nid369.txt";
    public static final String Password_File = "369pas369.txt";
    public static final String Phone_File = "369pho369.txt";
    public static final String T_Owner_File = "369t_ow369.txt";
    //all files are saved in Root Storage/data/user/0/com.example.bashabari/files
    ///////////////////////////////////////////////////////////////////////////////////////////////////


    //...............................variable to store views
    public EditText phoneField, passwordField;
    public TextView registerButton;
    public ImageView next_btn_3;
    private CheckBox login_as_owner_3;
    private DatabaseReference ownerRef;
    private DatabaseReference tenantRef;

    //.................................on create
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__03_login);


        //////////////////////////no internet connection function call
        if(!isConnected(_3Login.this))
            buildDialog(_3Login.this).show();



        //getting items from xml file
        phoneField = findViewById(R.id.phone_no_3);
        passwordField = findViewById(R.id.password__3);
        registerButton = findViewById(R.id.register);
        next_btn_3 = findViewById(R.id.next_btn_3);
        login_as_owner_3 = findViewById(R.id.login_as_owner_3);

        //database reference
        ownerRef = FirebaseDatabase.getInstance().getReference().child("Owner Database");
        tenantRef = FirebaseDatabase.getInstance().getReference().child("Tenant Database");

        ////////////////////////////////////////////////////////////////////////////////////////////////////////
        //...................................registerButton button click......................................//
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (login_as_owner_3.isChecked()) {
                    Intent intent3 = new Intent(_3Login.this, _4Register.class);
                    startActivity(intent3);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }
                else
                    Toast.makeText(_3Login.this, "You can only register as an owner", Toast.LENGTH_LONG).show();

            }
        });

        //////////////////////////////////////////////////////////////////////////////////////////////////////////
        //.................................................tick button click....................................//
        next_btn_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //getting items from phone and password field
                final String getPhoneKey, getPass;
                getPhoneKey = phoneField.getText().toString().trim();
                getPass = passwordField.getText().toString().trim();

                //..........................for owner
                if (login_as_owner_3.isChecked()) {
                    if (getPhoneKey.isEmpty())
                        phoneField.setError("Input your Phone number.");
                    else if (getPass.isEmpty())
                        passwordField.setError("Input your Password");
                    else
                        ownerLogin(getPhoneKey, getPass);
                }

                //..........................for tenant
                else {
                    if (getPhoneKey.isEmpty())
                        phoneField.setError("Input your Phone number.");
                    else if (getPass.isEmpty())
                        passwordField.setError("Input your Password");
                    else
                        tenantLogin(getPhoneKey, getPass);
                }
            }
        });
        /////////////////////////////////////////////////////////////////////////////////////////////////////////
        //........................................Refreshing method............................................//
        try {
            final SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.page_layout_3);
            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    swipeRefreshLayout.setRefreshing(true);
                    (new Handler()).postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(_3Login.this, _3Login.class);
                            startActivity(intent);
                            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    }, 1000);
                }
            });

        }
        catch (Exception e)
        {

        }

    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////
    //..........................................owner login..............................................//
    public void ownerLogin(final String getPhoneKey, final String getPass) {
        try {
            ownerRef.child(getPhoneKey).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    try {
                        ownerInfo userinf = dataSnapshot.getValue(ownerInfo.class);
                        if (getPass.equals(userinf.getPassword()) && getPhoneKey.equals(userinf.getPhone_no())) {
                            Toast.makeText(_3Login.this, "Login Successful", Toast.LENGTH_LONG).show();
                            //passes userdata which we got from database
                            saveLoginInfoToFile(userinf.getAddress(), userinf.getName(), userinf.getNid_no(), userinf.getPassword(), userinf.getPhone_no());

                            Intent intent_3 = new Intent(_3Login.this, _11OwnerMenu.class);
                            startActivity(intent_3);
                            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                            //loading condition for owner(written in the file)
                            FileOutputStream fos0 = null;
                            try {
                                fos0 = openFileOutput(Stat_File, MODE_PRIVATE);
                                fos0.write("logged_in_owner".getBytes());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        else
                            Toast.makeText(_3Login.this, "Invalid Phone no or Password", Toast.LENGTH_LONG).show();

                    } catch (Exception e) {
                        Toast.makeText(_3Login.this, "Invalid Phone no or Password", Toast.LENGTH_LONG).show();
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
    //............................................tenant login...................................................//
    public void tenantLogin(final String getPhoneKey, final String getPass) {
        try {
            tenantRef.child(getPhoneKey).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    try {
                        tenantInfo userinf = dataSnapshot.getValue(tenantInfo.class);
                        if (getPass.equals(userinf.getPassword()) && getPhoneKey.equals(userinf.getPhone_no())) {
                            Toast.makeText(_3Login.this, "Login Successful", Toast.LENGTH_LONG).show();
                            //passes userdata which we got from database
                            saveLoginInfoToFile(userinf.getAddress(), userinf.getName(), userinf.getNid_no(), userinf.getPassword(), userinf.getPhone_no());

                            //........it will save the tenant's owner number to the file
                            FileOutputStream fos1 = null;
                            try {
                                fos1 = openFileOutput(T_Owner_File, MODE_PRIVATE);
                                fos1.write(userinf.getOwner().getBytes());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                            Intent intent_3 = new Intent(_3Login.this, _6UserMenu.class);
                            startActivity(intent_3);
                            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                            //loading condition for tenant (written in the file)
                            FileOutputStream fos0 = null;
                            try {
                                fos0 = openFileOutput(Stat_File, MODE_PRIVATE);
                                fos0.write("logged_in_tenant".getBytes());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        else
                            Toast.makeText(_3Login.this, "Invalid Phone no or Password", Toast.LENGTH_LONG).show();

                    } catch (Exception e) {
                        Toast.makeText(_3Login.this, "Invalid Phone no or Password", Toast.LENGTH_LONG).show();
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
    //.............................Saving the database info to local file........................................//
    public void saveLoginInfoToFile(String address, String name, String nid_no, String password, String phone_no){
        // will save data into the files
        FileOutputStream fos1 = null;
        try {
            fos1 = openFileOutput(Address_File, MODE_PRIVATE);
            fos1.write(address.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }

        FileOutputStream fos2 = null;
        try {
            fos2 = openFileOutput(Name_File, MODE_PRIVATE);
            fos2.write(name.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }

        FileOutputStream fos3 = null;
        try {
            fos3 = openFileOutput(Nid_File, MODE_PRIVATE);
            fos3.write(nid_no.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }

        FileOutputStream fos4 = null;
        try {
            fos4 = openFileOutput(Password_File, MODE_PRIVATE);
            fos4.write(password.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }

        FileOutputStream fos5 = null;
        try {
            fos5 = openFileOutput(Phone_File, MODE_PRIVATE);
            fos5.write(phone_no.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void hidekeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);
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

        //builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

         //   @Override
          //  public void onClick(DialogInterface dialog, int which) {

            //    finish();
            //}
       // });

        return builder;
    }



}

