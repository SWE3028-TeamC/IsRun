package edu.skku.cs.isrun;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CharFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CharFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CharFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CharFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CharFragment newInstance(String param1, String param2) {
        CharFragment fragment = new CharFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private GridView plate;
    private GridViewAdapter gridViewAdapter;
    private ArrayList<charpopup> characterlist;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    public void charset(String img, String name, int level, String talk) {
        charpopup chh;
        chh = new charpopup();
        int resid ;
        resid = getResources().getIdentifier(img,"drawable",this.getActivity().getPackageName());

        chh.setImg(img);
        chh.setLevel(level);
        chh.setName(name);
        chh.setResID(resid);
        chh.setTalk(talk);
        characterlist.add(chh);
    }
    public void popUpImg(int resId, String img, Context context, String memo) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.char_popup, null);
        ImageView imageView = view.findViewById(R.id.popup_img);
        TextView textView = view.findViewById(R.id.popup_txt);

        imageView.setImageResource(resId);
        textView.setText(memo);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(view);
        builder.setPositiveButton("X", new DialogInterface.OnClickListener() {
            @Override public void onClick(DialogInterface dialog, int which) {
                if(dialog != null) {
                    dialog.dismiss();
                }
            }
        });
        builder.setNegativeButton("SET", new DialogInterface.OnClickListener() {
            @Override public void onClick(DialogInterface dialog, int which) {
                if(dialog != null) {
                    ((MainActivity_game)getActivity()).gett("NONE",img);
                    dialog.dismiss();
                }
            }
        });

        AlertDialog dialog;
        dialog = builder.create();
        dialog.show();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_char, container, false);
        characterlist = new ArrayList<charpopup>();
        plate = v.findViewById(R.id.grid);

        Bundle bundle = getArguments();
        int[] chchange = bundle.getIntArray("characters");

        for (int i:chchange) {
            System.out.println(i);
        }
        if (Arrays.stream(chchange).anyMatch(a->a==0))
            charset("cat","KITTY",1,"This is a kitty");
        if (Arrays.stream(chchange).anyMatch(a->a==1))
            charset("dog","DOGGY",1, "This is dog");
        if (Arrays.stream(chchange).anyMatch(a->a==2))
            charset("hamster","HAMMY",1,"This is a hamster");
        if (Arrays.stream(chchange).anyMatch(a->a==3))
            charset("parrot","PAROO",1,"This is a parrot");
        if (Arrays.stream(chchange).anyMatch(a->a==4))
            charset("bunny","BUNNY",1,"This is a bunny");
        if (Arrays.stream(chchange).anyMatch(a->a==5))
            charset("lion","LIOON",1,"This is a lion");
        if (Arrays.stream(chchange).anyMatch(a->a==6))
            charset("seal","SEALLY",1,"This is a seal");
        if (Arrays.stream(chchange).anyMatch(a->a==7))
            charset("shiba","SSIBA",1,"This is a shiba");

        gridViewAdapter = new GridViewAdapter(getContext(),characterlist);
        plate.setAdapter(gridViewAdapter);

        plate.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String charimg = (String)gridViewAdapter.getCharimg(position);
                int resid = (Integer)gridViewAdapter.getResID(position);

                System.out.println(charimg+resid);
                popUpImg(resid,charimg,getContext(),gridViewAdapter.getMemo(position));
                //Toast.makeText(getContext(), charname, Toast.LENGTH_SHORT).show();
            }
        });
        return v;
    }
}