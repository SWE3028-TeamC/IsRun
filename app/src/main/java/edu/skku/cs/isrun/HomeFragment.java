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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.nio.charset.StandardCharsets;

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
    int lev;
    int ful;
    int lov;
    int exp;


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
    private void mqttgoget (String aa, String topic) {
        final String MQTT_BROKER_IP = "tcp://ec2-3-36-128-151.ap-northeast-2.compute.amazonaws.com:1883";
        try
        {
            MqttClient client = new MqttClient(
                    MQTT_BROKER_IP, //URI
                    MqttClient.generateClientId(), //ClientId
                    new MemoryPersistence());
            client.connect();
            client.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable cause) { //Called when the client lost the connection to the broker
                }
                @Override
                public void deliveryComplete(IMqttDeliveryToken arg0) {                }
                @Override
                public void messageArrived(String arg0, MqttMessage arg1) throws Exception {
                    System.out.println(arg0 + ": " + arg1.toString());
                    //Toast.makeText(MainActivity_game.this, arg1.toString(), Toast.LENGTH_SHORT).show();
                }
            });
            client.subscribe(((MainActivity_game)getActivity()).uid+"/#", 2);
            client.publish(topic,new MqttMessage(aa.getBytes(StandardCharsets.UTF_8)));

        }

        catch (MqttException e) {
            e.printStackTrace();
        } //Persistence
    }
    public int[] maxlov = new int[]{5,10,10,20,20};
    public int[] maxful = new int[]{5,10,10,20,20};
    public int[] maxexp = new int[]{5,10,20,30,40};

    public int charstat(int level,int mcharidx, int exp, int lov, int ful) {
        int lev = level;
        if (exp==maxexp[level]) {
            lev++;
            exp=0;
        }
        String aa = "{\"UserId\":\""+((MainActivity_game)getActivity()).uid+"\",\"charidx\":"+mcharidx+",\"charLV\": "+lev+",\"charexp\": "+exp+", \"charlove\": "+lov+", \"charfull\": "+ful+"}";
        mqttgoget(aa,"UserData/UpdateUserChar");

        return lev;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        UserGameData userdata = ((MainActivity_game)getActivity()).userdata_game;

        lev = userdata.getUserChars()[userdata.getMcharidx()].getCharLV();
        lov = userdata.getUserChars()[userdata.getMcharidx()].getCharlove();
        exp = userdata.getUserChars()[userdata.getMcharidx()].getCharexp();
        ful = userdata.getUserChars()[userdata.getMcharidx()].getCharfull();
        int idx = userdata.getMcharidx();

        ImageView ch= (ImageView) v.findViewById(R.id.gameCharacter);
        ImageView bg= (ImageView) v.findViewById(R.id.gameBackground);
        ImageView response = (ImageView) v.findViewById(R.id.imageView6);
        response.setVisibility(View.INVISIBLE);
        FloatingActionButton btn_food = v.findViewById(R.id.foodbutton);
        FloatingActionButton btn_play = v.findViewById(R.id.playbutton);
        TextView level = (TextView)v.findViewById(R.id.level);
        level.setText("Level "+lev);
        ProgressBar expbar = (ProgressBar) v.findViewById(R.id.progressStat1);
        ProgressBar lovbar = (ProgressBar) v.findViewById(R.id.progressStat2);
        ProgressBar fulbar = (ProgressBar) v.findViewById(R.id.progressStat3);
        expbar.setMax(maxexp[lev]);
        lovbar.setMax(maxlov[lev]);
        fulbar.setMax(maxful[lev]);
        expbar.setProgress(exp,true);
        lovbar.setProgress(lov,true);
        fulbar.setProgress(ful,true);


        Bundle bundle = getArguments();
        String bgchange = bundle.getString("background"); // 프래그먼트1에서 받아온 값 넣기

        if (bgchange.equals("bg0")) {
            bg.setImageResource(R.drawable.image_2);
        }
        else if (!(bgchange.equals("NONE"))) {
            int resid = getResources().getIdentifier(bgchange, "drawable", this.getActivity().getPackageName());
            bg.setImageResource(resid);
        }
        String chchange = bundle.getString("character"); // 프래그먼트1에서 받아온 값 넣기


        btn_food.setOnClickListener(view-> {
            if (ful==maxful[lev] || lev>3) {
                System.out.println("full");
            }
            else if (((MainActivity_game)getActivity()).getitems(1,0,0)==1) {
                ch.setVisibility(View.INVISIBLE);
                response.setVisibility(View.VISIBLE);
                int resid = getResources().getIdentifier(chchange + "_f", "drawable", getContext().getPackageName());
                response.setImageResource(resid);
                ful+=1;
                exp+=1;
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ch.setVisibility(View.VISIBLE);
                        response.setVisibility(View.INVISIBLE);
                    }
                }, 1000);
                int newlevel = charstat(lev,idx, exp,lov,ful);
                if (newlevel != lev) {
                    lev = newlevel;
                    exp=0;
                    level.setText("Level "+lev);
                    expbar.setMax(maxexp[lev]);
                    lovbar.setMax(maxlov[lev]);
                    fulbar.setMax(maxful[lev]);
                }
                expbar.setProgress(exp,true);

                fulbar.setProgress(ful,true);
            }
        });

        btn_play.setOnClickListener(view-> {
            if (lov>=maxlov[lev]) {
                System.out.println("lovfull");
            }
            ch.setVisibility(View.INVISIBLE);
            response.setVisibility(View.VISIBLE);
            int resid = getResources().getIdentifier(chchange+"_p", "drawable",getContext().getPackageName());
            response.setImageResource(resid);
            lov++;
            charstat(lev,idx, exp,lov,ful);
            lovbar.setProgress(lov,true);
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