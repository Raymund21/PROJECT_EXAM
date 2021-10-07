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


public class Splash extends AppCompatActivity implements View.OnKeyListener {

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




        setContentView(R.layout.activity_my__ui);
        INIT_LOGIN();


        new ROLES_ASYNC_LOGIN().execute();

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();


        mDBHelper = new DataBase_Helper(this);

        mDBHelper.OPEN_DATABASE();

        CREATE_TABLE();

        File database = getApplicationContext().getDatabasePath(DataBase_Helper.DBNAME);
        if (false == database.exists()) {
            mDBHelper.getReadableDatabase();

            if (COPY_DATABASE(this)) {
                Toast.makeText(this, "Data Base Copy Succesful", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Data Base Copy Failed", Toast.LENGTH_SHORT).show();
            }
            return;
        }
    }



    public void SEL_VIEW(View view) {
        switch (view.getId()) {
            case R.id.UPLOAD_DATA:
                if(Check_Internet()){
                    new UPLOAD_DATA_ONLINE().execute();
                }
                else{
                    Toast.makeText(Splash.this,"Please connect to the internet.",Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.REG_STUDENT:
                REG_IND = "Y";
                new ROLES_ASYNC().execute();
                break;
            case R.id.REG_RETURN_NEW:
                setContentView(R.layout.activity_my__ui);
                INIT_LOGIN();
                new ROLES_ASYNC_LOGIN().execute();
                break;
            case R.id.REG_RETURN:
                if(REG_IND.equals("Y")){
                    setContentView(R.layout.activity_my__ui);
                    INIT_LOGIN();
                    new ROLES_ASYNC_LOGIN().execute();
                }
                else
                {
                    new MAIN_LIST().execute();
                }
                break;
            case R.id.DELETE_DATA:
                new DELETE_DATA().execute(ID_TEXTBOX.getText().toString());
                break;
            case R.id.LOGIN_MAIN:
                USERNAME = USERNAME_TB.getText().toString();
                new LOGIN().execute(USERNAME_TB.getText().toString(),PASSWORD_TB.getText().toString(),USER_ROLE);
                break;
            case R.id.REGISTER:
                REG_IND = "Y";
                new ROLES_ASYNC().execute();
                break;
            case R.id.SAVE_REGISTER:
                INSERT_DATAS();
                break;
        }

    }



    private void INSERT_DATAS(){
        if(Check_Internet()){
            new INSERT_DATA().execute(
                    FIRSTNAME_TEXTBOX.getText().toString(),
                    LASTNAME_TEXBOX.getText().toString(),
                    MIDDLENAME_TEXBOX.getText().toString(),
                    BIRTHDATE_TEXBOX.getText().toString(),
                    EMAIL_TEXBOX.getText().toString(),
                    CONTACT_NO_TEXBOX.getText().toString(),
                    ADDRESS_TEXBOX.getText().toString(),
                    USERNAME_TEXBOX.getText().toString(),
                    PASSWORD_TEXBOX.getText().toString(),
                    USER_ROLE,
                    ID_TEXTBOX.getText().toString()
            );
        }
        else
        {
            SQLiteDatabase mydatabase = openOrCreateDatabase("MY_UI", MODE_PRIVATE, null);
            String strSQL = "INSERT INTO INFORMATION (FIRSTNAME,LASTNAME,MIDDLENAME,BIRTHDATE,EMAIL,CONTACT_NO,ADDRESS,USERNAME,PASSWORD,UPL_IND,ROLE)" +
                    " VALUES ('"+FIRSTNAME_TEXTBOX.getText().toString()+"'," +
                    "'"+LASTNAME_TEXBOX.getText().toString()+"'," +
                    "'"+MIDDLENAME_TEXBOX.getText().toString()+"'," +
                    "'"+BIRTHDATE_TEXBOX.getText().toString()+"'," +
                    "'"+EMAIL_TEXBOX.getText().toString()+"'," +
                    "'"+CONTACT_NO_TEXBOX.getText().toString()+"'," +
                    "'"+ADDRESS_TEXBOX.getText().toString()+"'," +
                    "'"+USERNAME_TEXBOX.getText().toString()+"'," +
                    "'"+PASSWORD_TEXBOX.getText().toString()+"'," +
                    "'Y'," +
                    "'"+USER_ROLE+"')";
            mydatabase.execSQL(strSQL);
        }
    }

    private void INIT_LOGIN(){
        USERNAME_TB = (EditText) findViewById(R.id.USERNAME_TB);
        PASSWORD_TB = (EditText) findViewById(R.id.PASSWORD_TB);
        USERNAME_TB.setOnKeyListener(this);
        PASSWORD_TB.setOnKeyListener(this);
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        return false;
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        setContentView(R.layout.activity_my__ui);
//        INIT_LOGIN();
//
//    }

    class UPLOAD_DATA_ONLINE extends AsyncTask<String, Void, String>{


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            //super.onPostExecute(s);
            String xxx = s;

            Toast.makeText(Splash.this,xxx,Toast.LENGTH_LONG).show();
        }

        @Override
        protected String doInBackground(String... strings) {
            try{

                Cursor DEL_EXCEPT = mDBHelper.GET_INFORMATION();
                if (DEL_EXCEPT != null) {
                    if (DEL_EXCEPT.moveToFirst()) {
                        do {

                            String link = Connection.API + "insertion.php" ;
                            String data = URLEncoder.encode("FIRSTNAME","UTF-8") + "=" + URLEncoder.encode(DEL_EXCEPT.getString(DEL_EXCEPT.getColumnIndex("FIRSTNAME")) , "UTF-8");
                            data += "&" + URLEncoder.encode("LASTNAME","UTF-8") + "=" + URLEncoder.encode(DEL_EXCEPT.getString(DEL_EXCEPT.getColumnIndex("LASTNAME"))  , "UTF-8");
                            data += "&" + URLEncoder.encode("MIDDLENAME","UTF-8") + "=" + URLEncoder.encode(DEL_EXCEPT.getString(DEL_EXCEPT.getColumnIndex("MIDDLENAME"))  , "UTF-8");
                            data += "&" + URLEncoder.encode("BIRTHDATE","UTF-8") + "=" + URLEncoder.encode(DEL_EXCEPT.getString(DEL_EXCEPT.getColumnIndex("BIRTHDATE"))  , "UTF-8");
                            data += "&" + URLEncoder.encode("EMAIL","UTF-8") + "=" + URLEncoder.encode(DEL_EXCEPT.getString(DEL_EXCEPT.getColumnIndex("EMAIL"))  , "UTF-8");
                            data += "&" + URLEncoder.encode("CONTACT_NO","UTF-8") + "=" + URLEncoder.encode(DEL_EXCEPT.getString(DEL_EXCEPT.getColumnIndex("CONTACT_NO"))  , "UTF-8");
                            data += "&" + URLEncoder.encode("ADDRESS","UTF-8") + "=" + URLEncoder.encode(DEL_EXCEPT.getString(DEL_EXCEPT.getColumnIndex("ADDRESS"))  , "UTF-8");
                            data += "&" + URLEncoder.encode("USERNAME","UTF-8") + "=" + URLEncoder.encode(DEL_EXCEPT.getString(DEL_EXCEPT.getColumnIndex("USERNAME"))  , "UTF-8");
                            data += "&" + URLEncoder.encode("PASSWORD","UTF-8") + "=" + URLEncoder.encode(DEL_EXCEPT.getString(DEL_EXCEPT.getColumnIndex("PASSWORD"))  , "UTF-8");
                            data += "&" + URLEncoder.encode("ROLE","UTF-8") + "=" + URLEncoder.encode(DEL_EXCEPT.getString(DEL_EXCEPT.getColumnIndex("ROLE"))  , "UTF-8");
                            data += "&" + URLEncoder.encode("ID","UTF-8") + "=" + URLEncoder.encode("" , "UTF-8");

                            URL url = new URL(link);

                            HttpURLConnection connection1 =(HttpURLConnection) url.openConnection();
                            //connection.setDoInput(true);
                            connection1.setDoOutput(true);
                            //connection.setRequestMethod("POST");


                            OutputStreamWriter writer = new OutputStreamWriter(connection1.getOutputStream());
                            writer.write(data);
                            writer.flush();

                            BufferedReader reader = new BufferedReader(new InputStreamReader(connection1.getInputStream()));

                            SQLiteDatabase mydatabase = openOrCreateDatabase("MY_UI", MODE_PRIVATE, null);
                            String strSQL = "UPDATE INFORMATION SET UPL_IND = 'N' WHERE USERNAME ='"+ DEL_EXCEPT.getString(DEL_EXCEPT.getColumnIndex("USERNAME"))+"'";
                            mydatabase.execSQL(strSQL);

                        } while (DEL_EXCEPT.moveToNext());
                    }
                }


                return "Async Data Success";
            }
            catch (Exception ex){
                ex.printStackTrace();
                return  "Error";
            }
        }
    }

    class INSERT_DATA extends AsyncTask<String, Void, String>{


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            //super.onPostExecute(s);
            String xxx = s;

            Toast.makeText(Splash.this,xxx,Toast.LENGTH_LONG).show();
        }

        @Override
        protected String doInBackground(String... strings) {
            try{
                String link = Connection.API + "insertion.php" ;
                String data = URLEncoder.encode("FIRSTNAME","UTF-8") + "=" + URLEncoder.encode(strings[0] , "UTF-8");
                data += "&" + URLEncoder.encode("LASTNAME","UTF-8") + "=" + URLEncoder.encode(strings[1]  , "UTF-8");
                data += "&" + URLEncoder.encode("MIDDLENAME","UTF-8") + "=" + URLEncoder.encode(strings[2]  , "UTF-8");
                data += "&" + URLEncoder.encode("BIRTHDATE","UTF-8") + "=" + URLEncoder.encode(strings[3]  , "UTF-8");
                data += "&" + URLEncoder.encode("EMAIL","UTF-8") + "=" + URLEncoder.encode(strings[4]  , "UTF-8");
                data += "&" + URLEncoder.encode("CONTACT_NO","UTF-8") + "=" + URLEncoder.encode(strings[5]  , "UTF-8");
                data += "&" + URLEncoder.encode("ADDRESS","UTF-8") + "=" + URLEncoder.encode(strings[6]  , "UTF-8");
                data += "&" + URLEncoder.encode("USERNAME","UTF-8") + "=" + URLEncoder.encode(strings[7]  , "UTF-8");
                data += "&" + URLEncoder.encode("PASSWORD","UTF-8") + "=" + URLEncoder.encode(strings[8]  , "UTF-8");
                data += "&" + URLEncoder.encode("ROLE","UTF-8") + "=" + URLEncoder.encode(strings[9]  , "UTF-8");
                data += "&" + URLEncoder.encode("ID","UTF-8") + "=" + URLEncoder.encode(strings[10] , "UTF-8");

                URL url = new URL(link);

                HttpURLConnection connection1 =(HttpURLConnection) url.openConnection();
                //connection.setDoInput(true);
                connection1.setDoOutput(true);
                //connection.setRequestMethod("POST");


                OutputStreamWriter writer = new OutputStreamWriter(connection1.getOutputStream());
                writer.write(data);
                writer.flush();

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection1.getInputStream()));

                return reader.readLine();
            }
            catch (Exception ex){
                ex.printStackTrace();
                return  "Error";
            }
        }
    }

    private boolean COPY_DATABASE(Context context) {
        try {
            InputStream inputStream = context.getAssets().open(DataBase_Helper.DBNAME);
            String outfilename = DataBase_Helper.DB_PATH + DataBase_Helper.DBNAME;
            OutputStream outputStream = new FileOutputStream(outfilename);
            byte[] buff = new byte[1024];
            int length = 0;
            while ((length = inputStream.read(buff)) > 0) {
                outputStream.write(buff, 0, length);
            }
            outputStream.flush();
            outputStream.close();
            Log.w("MainActivity", "DB copied");
            return true;
        } catch (Exception e) {

            e.printStackTrace();
            return false;
        }

    }

    public void CREATE_TABLE() {
        SQLiteDatabase mydatabase = openOrCreateDatabase("MY_UI", MODE_PRIVATE, null);
        try {
            mydatabase.execSQL(
                    "create table IF NOT EXISTS INFORMATION" +
                            "(FIRSTNAME VARCHAR," +
                            "LASTNAME VARCHAR," +
                            "MIDDLENAME VARCHAR," +
                            "ID VARCHAR," +
                            "ROLE VARCHAR," +
                            "BIRTHDATE VARCHAR," +
                            "USERNAME VARCHAR," +
                            "PASSWORD VARCHAR," +
                            "EMAIL VARCHAR," +
                            "CONTACT_NO VARCHAR," +
                            "ADDRESS VARCHAR," +
                            "UPL_IND VARCHAR," +
                            "STATUS VARCHAR)");

        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }


    private void INIT_REGISTER_FORM() {
        try {
            ID_TEXTBOX = (EditText) findViewById(R.id.ID_TEXTBOX);
            FIRSTNAME_TEXTBOX = (EditText) findViewById(R.id.FIRSTNAME_TEXTBOX);
            LASTNAME_TEXBOX = (EditText) findViewById(R.id.LASTNAME_TEXBOX);
            MIDDLENAME_TEXBOX = (EditText) findViewById(R.id.MIDDLENAME_TEXBOX);
            BIRTHDATE_TEXBOX = (EditText) findViewById(R.id.BIRTHDATE_TEXBOX);
            EMAIL_TEXBOX = (EditText) findViewById(R.id.EMAIL_TEXBOX);
            CONTACT_NO_TEXBOX = (EditText) findViewById(R.id.CONTACT_NO_TEXBOX);
            ADDRESS_TEXBOX = (EditText) findViewById(R.id.ADDRESS_TEXBOX);
            USERNAME_TEXBOX = (EditText) findViewById(R.id.USERNAME_TEXBOX);
            PASSWORD_TEXBOX = (EditText) findViewById(R.id.PASSWORD_TEXBOX);
            SAVE_REGISTER = (Button) findViewById(R.id.SAVE_REGISTER);
            DELETE_DATA = (Button) findViewById(R.id.DELETE_DATA);
            DELETE_DATA.setVisibility(View.GONE);
            ID_TEXTBOX.setEnabled(false);

        } catch (Exception ex) {
            Toast.makeText(Splash.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    private class ROLES_ASYNC extends  AsyncTask<String,Void,String>
    {

        @Override
        protected String doInBackground(String... voids) {
            String link = Connection.API + "select.php" ;
            URL url = null;
            String xx = null;
            try {
                url = new URL(link);
                HttpURLConnection connection1 =(HttpURLConnection) url.openConnection();

                connection1.setRequestMethod("GET"); //Your method here
                connection1.connect();
                InputStream inputStream = connection1.getInputStream();

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection1.getInputStream()));
                xx = reader.readLine();
                return xx;

            } catch (MalformedURLException e) {
                e.printStackTrace();
                return "Error";
            } catch (IOException e) {
                e.printStackTrace();
                return "Error";
            }
        }

        @Override
        protected void onPostExecute(String s) {

            try
            {

                setContentView(R.layout.register);
                INIT_REGISTER_FORM();

                if (!REG_IND.equals("Y")) {

                    DELETE_DATA.setVisibility(View.VISIBLE);
                    ID_TEXTBOX.setText(student_list_global.getID());
                    FIRSTNAME_TEXTBOX.setText(student_list_global.getFIRSTNAME());
                    LASTNAME_TEXBOX.setText(student_list_global.getLASTNAME());
                    MIDDLENAME_TEXBOX.setText(student_list_global.getMIDDLENAME());
                    BIRTHDATE_TEXBOX.setText(student_list_global.getBIRTHDATE());
                    EMAIL_TEXBOX.setText(student_list_global.getEMAIL());
                    CONTACT_NO_TEXBOX.setText(student_list_global.getCONTACT_NO());
                    ADDRESS_TEXBOX.setText(student_list_global.getADDRESS());
                }
                else
                {
                    DELETE_DATA.setVisibility(View.GONE);
                }
                if(USER_ROLE.equals("1")){
                    DELETE_DATA.setVisibility(View.VISIBLE);
                }else{
                    DELETE_DATA.setVisibility(View.GONE);
                }

                JSONArray array = new JSONArray(s);
                Spinner spinner = (Spinner) findViewById(R.id.spinnerROLE);
                spinner.setEnabled(false);
                final List<ROLES> x = new ArrayList<>();
                //traversing through all the object
                for (int i = 0; i < array.length(); i++) {

                    //getting product object from json array
                    JSONObject product = array.getJSONObject(i);
                    //adding the product to product list

                    x.add(new ROLES(product.getString("ID"),product.getString("ID_DESC")));
                }

                ROLE_ADAPTER adapter = new ROLE_ADAPTER(getApplicationContext(), x);
                spinner.setAdapter(adapter);

                if (!REG_IND.equals("Y")) {
                    spinner.setSelected(false);
                    for (int i = 0; i < x.size(); i++) {
                        if (x.get(i).getROLE_ID().equals(student_list_global.getROLE())) {
                            spinner.setSelection(i,true);
                            ROLE_ID = student_list_global.getROLE();
                            break;
                        }
                    }
                }
                else{
                    spinner.setSelected(false);
                    spinner.setSelection(Integer.parseInt(USER_ROLE) - 1,true);
                }

                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> arg0, View arg1,
                                               int arg2, long arg3) {

                        ROLE_ID = x.get(arg2).getROLE_ID();
                        ROLE_DESC = x.get(arg2).getROLE_DESC();

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                        // TODO Auto-generated method stub

                    }
                });


            }
            catch ( Exception ex)
            {
                Toast.makeText(Splash.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }
    }

    private class MAIN_LIST extends AsyncTask<String,Void,String>{
        @Override
        protected String doInBackground(String... strings) {
            String link = Connection.API + "student_info.php" ;
            URL url = null;
            String xx = null;
            try {
                url = new URL(link);
                HttpURLConnection connection2 =(HttpURLConnection) url.openConnection();

                connection2.setRequestMethod("GET"); //Your method here
                connection2.connect();
                InputStream inputStream = connection2.getInputStream();

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection2.getInputStream()));
                xx = reader.readLine();
                return xx;

            } catch (MalformedURLException e) {
                e.printStackTrace();
                return "Error";
            } catch (IOException e) {
                e.printStackTrace();
                return "Error";
            }
        }

        @Override
        protected void onPostExecute(String s) {

            try
            {

                setContentView(R.layout.main_form);
                mainForm_List = (ListView) findViewById(R.id.Student_list);
                REG_STUDENT = (Button) findViewById(R.id.REG_STUDENT);
                if(USER_ROLE.equals("1")){
                    REG_STUDENT.setVisibility(View.VISIBLE);
                }
                else{
                    REG_STUDENT.setVisibility(View.GONE);
                }

                JSONArray array = new JSONArray(s);
                final List<STUDENT_INFO> student_list = new ArrayList<>();
                //traversing through all the object
                for (int i = 0; i < array.length(); i++) {

                    //getting product object from json array
                    JSONObject product = array.getJSONObject(i);
                    //adding the product to product list
                    if(USER_ROLE.equals("1")){
                        student_list.add(new STUDENT_INFO(
                                product.getString("FIRSTNAME"),
                                product.getString("LASTNAME"),
                                product.getString("MIDDLENAME"),
                                product.getString("ID"),
                                product.getString("ROLE"),
                                product.getString("BIRTHDATE"),
                                product.getString("USERNAME"),
                                product.getString("PASSWORD"),
                                product.getString("EMAIL"),
                                product.getString("CONTACT_NO"),
                                product.getString("ADDRESS")
                        ));
                    }
                    else
                    {
                        if (USERNAME.equals(product.getString("USERNAME"))){
                            student_list.add(new STUDENT_INFO(
                                    product.getString("FIRSTNAME"),
                                    product.getString("LASTNAME"),
                                    product.getString("MIDDLENAME"),
                                    product.getString("ID"),
                                    product.getString("ROLE"),
                                    product.getString("BIRTHDATE"),
                                    product.getString("USERNAME"),
                                    product.getString("PASSWORD"),
                                    product.getString("EMAIL"),
                                    product.getString("CONTACT_NO"),
                                    product.getString("ADDRESS")
                            ));
                        }
                    }


                }

//                //getting product object from json array
//                JSONObject product = array.getJSONObject(array.length() -1);
//                //adding the product to product list
//                x.add(new STUDENT_INFO(
//                        product.getString("FIRSTNAME"),
//                        product.getString("LASTNAME"),
//                        product.getString("MIDDLENAME"),
//                        product.getString("ID"),
//                        product.getString("ROLE"),
//                        product.getString("BIRTHDATE"),
//                        product.getString("USERNAME"),
//                        product.getString("PASSWORD"),
//                        product.getString("EMAIL"),
//                        product.getString("CONTACT_NO"),
//                        product.getString("ADDRESS")
//                ));

                STUDENT_ADAPTER adapter = new STUDENT_ADAPTER(getApplicationContext(),student_list);
                mainForm_List.setAdapter(adapter);
                mainForm_List.setClickable(true);

                mainForm_List.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        REG_IND = "N";
                        student_list_global = student_list.get(i);
                        new ROLES_ASYNC().execute();
                    }
                });
            }
            catch ( Exception ex)
            {
                Toast.makeText(Splash.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class LOGIN extends  AsyncTask<String,Void,String>
    {
        @Override
        protected String doInBackground(String... strings) {
            try{
                String link = Connection.API + "user_login.php" ;
                String data = URLEncoder.encode("USERNAME","UTF-8") + "=" + URLEncoder.encode(strings[0] , "UTF-8");
                data += "&" + URLEncoder.encode("PASSWORD","UTF-8") + "=" + URLEncoder.encode(strings[1]  , "UTF-8");
                data += "&" + URLEncoder.encode("ROLE","UTF-8") + "=" + URLEncoder.encode(strings[2]  , "UTF-8");

                URL url = new URL(link);

                HttpURLConnection connection1 =(HttpURLConnection) url.openConnection();
                //connection.setDoInput(true);
                connection1.setDoOutput(true);
                //connection.setRequestMethod("POST");


                OutputStreamWriter writer = new OutputStreamWriter(connection1.getOutputStream());
                writer.write(data);
                writer.flush();

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection1.getInputStream()));

                return reader.readLine();
            }
            catch (Exception ex){
                ex.printStackTrace();
                return  "Error";
            }
        }

        @Override
        protected void onPostExecute(String s) {

            try
            {
                if (s.equals("1")){
                    new MAIN_LIST().execute();
                }else{
                    Toast.makeText(Splash.this,"No Data Found.", Toast.LENGTH_SHORT).show();
                }
            }
            catch ( Exception ex)
            {
                Toast.makeText(Splash.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }
    }

    class DELETE_DATA extends AsyncTask<String, Void, String>{


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            //super.onPostExecute(s);
            String xxx = s;

            Toast.makeText(Splash.this,xxx,Toast.LENGTH_LONG).show();

            new MAIN_LIST().execute();
        }

        @Override
        protected String doInBackground(String... strings) {
            try{
                String link = Connection.API + "delete_info.php" ;
                String data = URLEncoder.encode("ID","UTF-8") + "=" + URLEncoder.encode(strings[0] , "UTF-8");

                URL url = new URL(link);

                HttpURLConnection connection1 =(HttpURLConnection) url.openConnection();
                //connection.setDoInput(true);
                connection1.setDoOutput(true);
                //connection.setRequestMethod("POST");


                OutputStreamWriter writer = new OutputStreamWriter(connection1.getOutputStream());
                writer.write(data);
                writer.flush();

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection1.getInputStream()));

                return reader.readLine();
            }
            catch (Exception ex){
                ex.printStackTrace();
                return  "Error";
            }
        }
    }

    private class ROLES_ASYNC_LOGIN extends  AsyncTask<String,Void,String>
    {

        @Override
        protected String doInBackground(String... voids) {
            String link = Connection.API + "select.php" ;
            URL url = null;
            URL url1 = null;
            String xx = null;
            try {
                url = new URL(link);
                HttpURLConnection connection1 =(HttpURLConnection) url.openConnection();

                connection1.setRequestMethod("GET"); //Your method here
                connection1.connect();
                InputStream inputStream = connection1.getInputStream();

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection1.getInputStream()));
                xx = reader.readLine();

                url1 = new URL(Connection.API + "get_role_cnt.php");
                HttpURLConnection connection2 =(HttpURLConnection) url1.openConnection();

                connection2.setRequestMethod("GET"); //Your method here
                connection2.connect();

                BufferedReader reader1 = new BufferedReader(new InputStreamReader(connection2.getInputStream()));
                ROLE_COUNT = reader1.readLine();
                return xx;

            } catch (MalformedURLException e) {
                e.printStackTrace();
                return "Error";
            } catch (IOException e) {
                e.printStackTrace();
                return "Error";
            }
        }

        @Override
        protected void onPostExecute(String s) {

            try
            {
                JSONArray array = new JSONArray(s);
                Spinner spinner = (Spinner) findViewById(R.id.spinnerROLE_LOGIN);
                final List<ROLES> x = new ArrayList<>();
                //traversing through all the object
                for (int i = 0; i < array.length(); i++) {

                    //getting product object from json array
                    JSONObject product = array.getJSONObject(i);
                    //adding the product to product list

                    x.add(new ROLES(product.getString("ID"),product.getString("ID_DESC")));
                }

                ROLE_ADAPTER adapter = new ROLE_ADAPTER(getApplicationContext(), x);
                spinner.setAdapter(adapter);
                spinner.setSelected(false);
                spinner.setSelection(1,true);
                USER_ROLE = "2";


                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> arg0, View arg1,
                                               int arg2, long arg3) {

                        ROLE_ID = x.get(arg2).getROLE_ID();
                        ROLE_DESC = x.get(arg2).getROLE_DESC();
                        USER_ROLE = x.get(arg2).getROLE_ID();
                        if (ROLE_ID.equals("1")){

                            if(ROLE_COUNT.equals("0")){
                                REG_IND = "Y";
                                setContentView(R.layout.register);
                                INIT_REGISTER_FORM();
                                new ROLES_ASYNC().execute();

                            }

                        }

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                        // TODO Auto-generated method stub

                    }
                });


            }
            catch ( Exception ex)
            {
                Toast.makeText(Splash.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }
    }

    public boolean Check_Internet() {
        ConnectivityManager ConnectionManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = ConnectionManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected() == true) {

            //Toast.makeText(getApplicationContext(), "Network Available", Toast.LENGTH_SHORT).show();
            return true;

        } else {

            // Toast.makeText(getApplicationContext(), "Network Not Available", Toast.LENGTH_SHORT).show();

            return false;
        }

    }


}
