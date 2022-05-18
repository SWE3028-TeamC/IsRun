package edu.skku.cs.isrun;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BackFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BackFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BackFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BackFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BackFragment newInstance(String param1, String param2) {
        BackFragment fragment = new BackFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    public String change = "none";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_back, container, false);
        ImageView img = v.findViewById(R.id.gameBackground);


        ImageView img1 = v.findViewById(R.id.poster_1);
        img1.setOnClickListener(view-> {
            img.setImageResource(R.drawable.image_2);
            change = "image_2";
        });
        ImageView img2 = v.findViewById(R.id.poster_2);
        img2.setOnClickListener(view-> {
            img.setImageResource(R.drawable.bg1);
            change="bg1";
        });
        ImageView img3 = v.findViewById(R.id.poster_3);
        img3.setOnClickListener(view-> {
            img.setImageResource(R.drawable.bg2);
            change="bg2";
        });
        ImageView img4 = v.findViewById(R.id.poster_4);
        img4.setOnClickListener(view-> {
            img.setImageResource(R.drawable.bg3);
            change="bg3";
        });
        ImageView img5 = v.findViewById(R.id.poster_5);
        img5.setOnClickListener(view-> {
            img.setImageResource(R.drawable.bg4);
            change="bg4";
        });
        ImageView img6 = v.findViewById(R.id.poster_6);
        img6.setOnClickListener(view-> {
            img.setImageResource(R.drawable.bg5);
            change="bg5";
        });
        ImageView img7 = v.findViewById(R.id.poster_7);
        img7.setOnClickListener(view-> {
            img.setImageResource(R.drawable.bg6);
            change="bg6";
        });
        ImageView img8 = v.findViewById(R.id.poster_8);
        img8.setOnClickListener(view-> {
            img.setImageResource(R.drawable.bg7);
            change="bg7";
        });
        ImageView img9 = v.findViewById(R.id.poster_9);
        img9.setOnClickListener(view-> {
            img.setImageResource(R.drawable.bg8);
            change="bg8";
        });
        ImageView img10 = v.findViewById(R.id.poster_10);
        img10.setOnClickListener(view-> {
            img.setImageResource(R.drawable.bg9);
            change="bg9";
        });
        ImageView img11 = v.findViewById(R.id.poster_11);
        img11.setOnClickListener(view-> {
            img.setImageResource(R.drawable.bg10);
            change="bg10";
        });
        ImageView img12 = v.findViewById(R.id.poster_12);
        img12.setOnClickListener(view-> {
            img.setImageResource(R.drawable.bg11);
            change="bg11";
        });
        ImageView img13 = v.findViewById(R.id.poster_13);
        img13.setOnClickListener(view-> {
            img.setImageResource(R.drawable.bg12);
            change="bg12";
        });
        ImageView img14 = v.findViewById(R.id.poster_14);
        img14.setOnClickListener(view-> {
            img.setImageResource(R.drawable.bg13);
            change="bg13";
        });
        ImageView img15 = v.findViewById(R.id.poster_15);
        img15.setOnClickListener(view-> {
            img.setImageResource(R.drawable.bg14);
            change="bg14";
        });

        Button btn = v.findViewById(R.id.set);
        btn.setOnClickListener(view-> {
            ((MainActivity)getActivity()).gett(change,"NONE");
        });
        return v;
    }
}