package com.example.khalil.pixidustwebapi.Activities;

import android.Manifest;
import android.app.Activity;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.khalil.pixidustwebapi.Entities.LoginUser;
import com.example.khalil.pixidustwebapi.R;
import com.example.khalil.pixidustwebapi.Utils.ApiConnection;
import com.example.khalil.pixidustwebapi.Utils.CheckInternetConnection;
import com.example.khalil.pixidustwebapi.Utils.FeedReaderDbConn;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    LoginUser loginUser = new LoginUser();
    public static TextView txtInvalidUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
               /*FileSaving Permition*/
        int permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;
        if ((permission != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_READ_CONTACTS);
        }
        int permission1 = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int MY_PERMISSIONS_REQUEST_READ_CONTACTS1 = 1;
        if ((permission1 != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_READ_CONTACTS1);
        }
       /*FileSaving Permition*/
    }

    public void Login(View v) throws JSONException {
        TextView txtloginemail = (TextView) findViewById(R.id.loginemail);
        TextView txtloginpassword = (TextView) findViewById(R.id.loginpassword);
        txtInvalidUser = (TextView) findViewById(R.id.txtinvalidUser);
        String loginEmail = txtloginemail.getText().toString();
        String loginPassword = txtloginpassword.getText().toString();
        if ((!loginEmail.isEmpty() && loginEmail != null) && (!loginPassword.isEmpty() && loginPassword != null)) {
            txtInvalidUser.setVisibility(View.INVISIBLE);
            if (CheckInternetConnection.IsNetworkAvailable(this)) {
                JSONObject object = new JSONObject();
                try {
                    object.put("UserName", loginEmail);
                    object.put("Password", loginPassword);
                    object.put("FK_UserType", 2);
                } catch (Exception ex) {
                }
                ApiConnection apiConnection = new ApiConnection("http://www.pixiedustdatasystem.com/MobileWebApi/api/Login/LoginUser", object.toString(), this, "Login", null, "");
                apiConnection.execute();
            } else {
                ProgressDialog progDialog = ProgressDialog.show(this, "Signing in",
                        "Please wait while we are signing you in..");
                FeedReaderDbConn con = new FeedReaderDbConn(this);
                con.open();
                String[] arr = con.getSingleUserData(loginEmail, loginPassword);
                con.close();
                if (arr == null) {
                    Toast.makeText(this, "Connect to internet", Toast.LENGTH_SHORT).show();
                    progDialog.hide();
                } else {
                    Intent intent = new Intent(this, Projects.class);
                    int ID = Integer.parseInt(arr[0]);
                    intent.putExtra("UserID", "" + ID + "");
                    this.startActivity(intent);
                    progDialog.hide();
                }
            }
        }
        else{
            txtInvalidUser.setText("Invalid Entries");
            txtInvalidUser.setVisibility(View.VISIBLE);
        }
    }
    @Override
    public void onBackPressed()
    {
        finish();
        moveTaskToBack(true);
        System.exit(0);
    }
}

