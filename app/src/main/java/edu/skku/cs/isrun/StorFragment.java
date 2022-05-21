package edu.skku.cs.isrun;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StorFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public StorFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StorFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StorFragment newInstance(String param1, String param2) {
        StorFragment fragment = new StorFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_stor, container, false);

        ImageView char_draw = v.findViewById(R.id.Stor_char);
        Glide.with(this).load(R.raw.char_draw).into(char_draw);
        ImageView back_draw = v.findViewById(R.id.Stor_back);
        Glide.with(this).load(R.raw.back_draw).into(back_draw);

        Button charbtn = v.findViewById(R.id.charbtn);
        Button backbtn = v.findViewById(R.id.backbtn);
        ImageView random = v.findViewById(R.id.random);

        charbtn.setOnClickListener(view-> {
            if (((MainActivity_game)getActivity()).getitems(0,0,100)==1) {
                random.setVisibility(View.VISIBLE);
                random.bringToFront();
                Glide.with(this).load(R.raw.rollcat).into(random);
                backbtn.setVisibility(View.INVISIBLE);
                charbtn.setVisibility(View.INVISIBLE);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        random.setVisibility(View.INVISIBLE);
                        backbtn.setVisibility(View.VISIBLE);
                        charbtn.setVisibility(View.VISIBLE);
                    }
                }, 3000);
            }
        });

        backbtn.setOnClickListener(view-> {
            if (((MainActivity_game)getActivity()).getitems(0,0,50)==1) {
                random.setVisibility(View.VISIBLE);
                random.bringToFront();
                Glide.with(this).load(R.raw.rollrac).into(random);
                backbtn.setVisibility(View.INVISIBLE);
                charbtn.setVisibility(View.INVISIBLE);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        random.setVisibility(View.INVISIBLE);
                        backbtn.setVisibility(View.VISIBLE);
                        charbtn.setVisibility(View.VISIBLE);
                    }
                }, 3000);
            }
        });


        return v;
    }
}