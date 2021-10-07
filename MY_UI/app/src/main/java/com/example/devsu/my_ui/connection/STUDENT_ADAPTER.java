package com.example.devsu.my_ui.connection;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.devsu.my_ui.R;
import com.example.devsu.my_ui.STUDENT_INFO;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by MJ on 10/7/2021.
 */

public class STUDENT_ADAPTER extends BaseAdapter {


    private Context Context;
    private List<STUDENT_INFO> Student_List;
    private int lastPosition = -1;
    ImageView IMG;
    Bitmap decodedByte;



    public STUDENT_ADAPTER(Context Context,List<STUDENT_INFO> Student_List) {
        this.Context = Context;
        this.Student_List = Student_List;
    }



    @Override
    public int getCount() {
        return Student_List.size();
    }

    @Override
    public Object getItem(int i) {
        return Student_List.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(Context, R.layout.student_layout,null);
        ((TextView) v.findViewById(R.id.txtFNAME)).setText("Role: " + (Student_List.get(i).getROLE().equals("1") ? "Admin" : "Student"));
        ((TextView) v.findViewById(R.id.txtLNAME)).setText("ID: " + Student_List.get(i).getID());
        ((TextView) v.findViewById(R.id.txtMNAME)).setText(Student_List.get(i).getLASTNAME() + ", "+ Student_List.get(i).getFIRSTNAME() + " " + Student_List.get(i).getMIDDLENAME());

        return v;
    }
}
