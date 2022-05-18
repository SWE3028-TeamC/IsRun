package edu.skku.cs.isrun;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;

public class GridViewAdapter extends BaseAdapter {
    private ArrayList<charpopup> chs;
    private Context mContext;

    private String charname;
    private String charimg;
    private int resID;
    private String memo;



    GridViewAdapter (Context mContext, ArrayList<charpopup> chs) {
        this.mContext = mContext;
        this.chs = chs;
    }

    @Override
    public int getCount() {
        return chs.size();
    }

    @Override
    public Object getItem(int i) {
        return chs.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public String getCharname(int i) {
        return chs.get(i).getName();
    }
    public String getCharimg(int i) {
        return chs.get(i).getImg();
    }
    public int getResID(int i) {
        return chs.get(i).getResID();
    }
    public String getMemo(int i) {
        return chs.get(i).getTalk();
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view==null) {
            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.character_individual,viewGroup,false);
        }
        ImageView iv1 = view.findViewById(R.id.imageView);
        TextView tx1 = view.findViewById(R.id.textView);

        charname = chs.get(i).getName();
        tx1.setText(charname);
        resID = chs.get(i).getResID();
        iv1.setImageResource(resID);
        charimg = chs.get(i).getImg();

        /*if (i==0) {
            iv1.setImageResource(R.drawable.cat);
            charimg = "cat";
        }
        else if (i==1) {
            iv1.setImageResource(R.drawable.dog);
            charimg = "dog";
        }
        else if (i==2) {
            iv1.setImageResource(R.drawable.hamster);
            charimg = "hamster";
        }*/
        return view;
    }
}
