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
    private ArrayList<Integer> chs;
    private Context mContext;



    GridViewAdapter (Context mContext, ArrayList<Integer> chs) {
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

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view==null) {
            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.character_individual,viewGroup,false);
        }
        ImageView iv1 = view.findViewById(R.id.imageView);
        TextView tx1 = view.findViewById(R.id.textView);
        if (i==0) {
            iv1.setImageResource(R.drawable.cat);
            tx1.setText("KITTY");
        }
        else if (i==1) {
            iv1.setImageResource(R.drawable.dog);
            tx1.setText("DOGGY");
        }
        else if (i==2) {
            iv1.setImageResource(R.drawable.hamster);
            tx1.setText("HAMMY");
        }
        return view;
    }
}
