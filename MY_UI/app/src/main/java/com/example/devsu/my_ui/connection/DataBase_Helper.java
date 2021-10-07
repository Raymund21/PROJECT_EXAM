package com.example.devsu.my_ui.connection;

/**
 * Created by devsu on 10/6/2021.
 */

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;



/**
 * Created by hp prodesk on 09/12/2017.
 */

public class DataBase_Helper extends SQLiteOpenHelper{


    public  static final  String DBNAME ="MY_UI";
    public  static  String DB_PATH ="";
    public  static  String outFileName ="";




    private Context mContext;
    private static SQLiteDatabase mDataBase;

    public DataBase_Helper(Context context)
    {
        super(context,DBNAME,null,1);
        this.mContext = context;
        DB_PATH= "/data/data/"+ context.getPackageName()+"/databases/";

    }


    public void OPEN_DATABASE()
    {
        try
        {
            String Db_path = mContext.getDatabasePath(DBNAME).getPath();
            DB_PATH = Db_path;
            if(mDataBase != null && mDataBase.isOpen())
            {
                return ;
            }
            mDataBase = SQLiteDatabase.openDatabase(Db_path,null, SQLiteDatabase.OPEN_READWRITE);

        }
        catch (Exception ex)
        {
            Log.e(TAG, "ERROR: " + ex.getMessage());
        }




    }


    public  void  Close_Database()
    {
        if(mDataBase !=null){mDataBase.close();}
    }


    public Cursor GET_INFORMATION()
    {
        Cursor res =  mDataBase.rawQuery( "select * from INFORMATION", null );
        return res;
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
