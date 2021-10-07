package com.example.devsu.my_ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by MJ on 10/7/2021.
 */

public class ROLE_ADAPTER extends BaseAdapter {


    private Context Context;
    private List<ROLES> ROLE_LIST;
    private int lastPosition = -1;

    ImageView IMG;
    Bitmap decodedByte;



    public ROLE_ADAPTER(Context Context, List<ROLES> ROLE_LIST) {
        this.Context = Context;
        this.ROLE_LIST = ROLE_LIST;
    }



    @Override
    public int getCount() {
        return ROLE_LIST.size();
    }

    @Override
    public Object getItem(int i) {
        return ROLE_LIST.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(Context, R.layout.spinnerlayout,null);
        TextView sample =(TextView)v.findViewById(R.id.SP_TV);

        sample.setText(ROLE_LIST.get(i).getROLE_DESC());
        v.setTag(ROLE_LIST.get(i));

        return v;
    }
}
