package edu.skku.cs.isrun;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

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

        ImageView ch= (ImageView) v.findViewById(R.id.gameCharacter);;
        ImageView bg= (ImageView) v.findViewById(R.id.gameBackground);;

        try {
            Glide.with(this).load(R.raw.hamster).into(ch);
            bg.setImageResource(R.drawable.bg1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Bundle bundle = getArguments();
        if (bundle != null)
        {
            String bgchange = bundle.getString("background"); // 프래그먼트1에서 받아온 값 넣기
            if (!(bgchange.equals("NONE"))) {
                int resid = getResources().getIdentifier(bgchange, "drawable", this.getActivity().getPackageName());
                bg.setImageResource(resid);
            }
            String chchange = bundle.getString("character"); // 프래그먼트1에서 받아온 값 넣기
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
            }
        }

        return v;
    }
}