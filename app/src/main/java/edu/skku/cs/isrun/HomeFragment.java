package edu.skku.cs.isrun;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        ImageView ch= (ImageView) v.findViewById(R.id.gameCharacter);
        ImageView bg= (ImageView) v.findViewById(R.id.gameBackground);
        ImageView response = (ImageView) v.findViewById(R.id.imageView6);
        response.setVisibility(View.INVISIBLE);
        FloatingActionButton btn_food = v.findViewById(R.id.foodbutton);
        FloatingActionButton btn_play = v.findViewById(R.id.playbutton);





        Bundle bundle = getArguments();
        String bgchange = bundle.getString("background"); // 프래그먼트1에서 받아온 값 넣기


        if (!(bgchange.equals("NONE"))) {
            int resid = getResources().getIdentifier(bgchange, "drawable", this.getActivity().getPackageName());
            bg.setImageResource(resid);
        }
        String chchange = bundle.getString("character"); // 프래그먼트1에서 받아온 값 넣기

        btn_food.setOnClickListener(view-> {
            if (((MainActivity_game)getActivity()).getitems(1,0,0)==1) {
                ch.setVisibility(View.INVISIBLE);
                response.setVisibility(View.VISIBLE);
                int resid = getResources().getIdentifier(chchange + "_f", "drawable", getContext().getPackageName());
                response.setImageResource(resid);


                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ch.setVisibility(View.VISIBLE);
                        response.setVisibility(View.INVISIBLE);
                    }
                }, 1000);
            }
        });

        btn_play.setOnClickListener(view-> {
            ch.setVisibility(View.INVISIBLE);
            response.setVisibility(View.VISIBLE);
            int resid = getResources().getIdentifier(chchange+"_p", "drawable",getContext().getPackageName());
            response.setImageResource(resid);

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ch.setVisibility(View.VISIBLE);
                    response.setVisibility(View.INVISIBLE);
                }
            },1000);
        });


        if (!(chchange.equals("NONE"))) {
            if (chchange.equals("hamster")) {
                try {
                    Glide.with(this).load(R.raw.hamster).into(ch);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else if (chchange.equals("dog")) {
                try {
                    Glide.with(this).load(R.raw.dog).into(ch);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else if (chchange.equals("cat")) {
                try {
                    Glide.with(this).load(R.raw.cat).into(ch);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else if (chchange.equals("parrot")) {
                try {
                    Glide.with(this).load(R.raw.parrot).into(ch);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else if (chchange.equals("bunny")) {
                try {
                    Glide.with(this).load(R.raw.bunny).into(ch);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else if (chchange.equals("lion")) {
                try {
                    Glide.with(this).load(R.raw.lion).into(ch);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else if (chchange.equals("seal")) {
                try {
                    Glide.with(this).load(R.raw.seal).into(ch);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else if (chchange.equals("shiba")) {
                try {
                    Glide.with(this).load(R.raw.shiba).into(ch);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return v;
    }
}