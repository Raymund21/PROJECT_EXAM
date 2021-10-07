package com.example.devsu.my_ui;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.devsu.my_ui.connection.Connection;
import com.example.devsu.my_ui.connection.DataBase_Helper;
import com.example.devsu.my_ui.connection.STUDENT_ADAPTER;
import com.google.gson.Gson;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class MY_UI extends AppCompatActivity{

    private static DataBase_Helper mDBHelper;

    EditText USERNAME_TB,PASSWORD_TB,ID_TEXTBOX,FIRSTNAME_TEXTBOX,LASTNAME_TEXBOX,MIDDLENAME_TEXBOX,BIRTHDATE_TEXBOX,EMAIL_TEXBOX,CONTACT_NO_TEXBOX,ADDRESS_TEXBOX,USERNAME_TEXBOX,PASSWORD_TEXBOX;
    Button SAVE_REGISTER,DELETE_DATA,REG_STUDENT;
    String USERNAME,ROLE_ID, ROLE_DESC,REG_IND,ROLE_COUNT,USER_ROLE;
    ListView mainForm_List;
    STUDENT_INFO student_list_global;

    private static int Splash = 4000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MY_UI.this,Splash.class);
                startActivity(intent);
                finish();
            }
        },Splash);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.splash);

    }



}
