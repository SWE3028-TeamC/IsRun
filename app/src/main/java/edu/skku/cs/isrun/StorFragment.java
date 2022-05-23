package edu.skku.cs.isrun;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Random;

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
    private int gold;
    InnerData[] inn = new InnerData[10];
    InnerData2[] inn2 = new InnerData2[20];

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
        Random rnd = new Random();

        Bundle bundle = getArguments();
        gold = bundle.getInt("gold"); // 프래그먼트1에서 받아온 값 넣기
        int[] characterlist = bundle.getIntArray("characters");
        int[] posterlist = bundle.getIntArray("posters");
        inn = ((MainActivity_game)getActivity()).userdata_game.getUserChars();

        ImageView char_draw = v.findViewById(R.id.Stor_char);
        Glide.with(this).load(R.raw.char_draw).into(char_draw);
        ImageView back_draw = v.findViewById(R.id.Stor_back);
        Glide.with(this).load(R.raw.back_draw).into(back_draw);

        Button charbtn = v.findViewById(R.id.charbtn);
        Button backbtn = v.findViewById(R.id.backbtn);
        ImageView random = v.findViewById(R.id.random);
        TextView gold_txt = v.findViewById(R.id.Stor_gold);
        gold_txt.setText(""+gold);

        charbtn.setOnClickListener(view-> {
            if (((MainActivity_game)getActivity()).getitems(0,0,100)!=0) {
                gold-=100;
                random.setVisibility(View.VISIBLE);
                random.bringToFront();
                Glide.with(this).load(R.raw.rollcat).into(random);
                backbtn.setVisibility(View.INVISIBLE);
                charbtn.setVisibility(View.INVISIBLE);
                gold_txt.setText(""+gold);
                int rn;
                while (true) {
                    rn = rnd.nextInt(7)+1;
                    int finalRn = rn;
                    if (Arrays.stream(characterlist).anyMatch(a->a== finalRn)) {  }
                    else {
                        characterlist[rn]=rn;
                        System.out.println(rn);
                        break;
                    }
                }
                InnerData in = new InnerData();
                in.setCharidx(rn);
                in.setCharname(((MainActivity_game) getActivity()).app_character_list[rn]);
                inn[rn]=in;

                String aa = "{\"UserId\":\""+((MainActivity_game)getActivity()).uid+"\",\"charidx\":"+rn+",\"charLV\": 0, \"charexp\": 0, \"charlove\": 0, \"charfull\": 0}";
                System.out.println("aa:"+aa);
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
                    //client.subscribe("testid/#", 2);
                    client.publish("UserData/NewUserChar",new MqttMessage(aa.getBytes(StandardCharsets.UTF_8)));
                    //client.publish("UserData/UpdateUserChar",new MqttMessage(bb.getBytes(StandardCharsets.UTF_8)));
                }

                catch (MqttException e) {
                    e.printStackTrace();
                } //Persistence
                //((MainActivity_game)getActivity()).userdata_game.
                ((MainActivity_game)getActivity()).userdata_game.setUserChars(inn);
                ((MainActivity_game)getActivity()).userdata_game.setCharacter_list(characterlist);
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
            if (((MainActivity_game)getActivity()).getitems(0,0,50)!=0) {
                gold-=50;
                random.setVisibility(View.VISIBLE);
                random.bringToFront();
                Glide.with(this).load(R.raw.rollrac).into(random);
                backbtn.setVisibility(View.INVISIBLE);
                charbtn.setVisibility(View.INVISIBLE);
                gold_txt.setText(""+gold);
                int rn;
                while (true) {
                    rn = rnd.nextInt(14)+1;
                    int finalRn = rn;
                    if (Arrays.stream(posterlist).anyMatch(a->a== finalRn)) {  }
                    else {
                        posterlist[rn]=rn;
                        System.out.println(rn);
                        break;
                    }
                }
                InnerData2 in = new InnerData2();
                in.setPosteridx(rn);
                inn2[rn]=in;
                String aa = "{\"UserId\":\""+((MainActivity_game)getActivity()).uid+"\",\"posteridx\":"+rn+"}";
                System.out.println("aa:"+aa);
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
                            //client.subscribe("testid/#", 2);
                            client.publish("UserData/NewUserPoster",new MqttMessage(aa.getBytes(StandardCharsets.UTF_8)));
                            //client.publish("UserData/UpdateUserChar",new MqttMessage(bb.getBytes(StandardCharsets.UTF_8)));
                        }

                        catch (MqttException e) {
                            e.printStackTrace();
                        } //Persistence
                        //((MainActivity_game)getActivity()).userdata_game.
                        ((MainActivity_game)getActivity()).userdata_game.setUserPosters(inn2);
                        ((MainActivity_game)getActivity()).userdata_game.setPoster_list(posterlist);
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

/*        final String MQTT_BROKER_IP = "tcp://ec2-3-36-128-151.ap-northeast-2.compute.amazonaws.com:1883";
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
            String aa = "{\"UserId\":\"testid\"}";
            String bb = "{\"UserId\": \"testid\", \"charidx\": 1, \"charLV\": 2, \"charname\": \"kit\", \"charexp\": 10, \"charlove\": 1, \"charfull\": 1}";
            client.subscribe("testid/#", 2);
            client.publish("UserData/GetUserChars",new MqttMessage(aa.getBytes(StandardCharsets.UTF_8)));
            //client.publish("UserData/UpdateUserChar",new MqttMessage(bb.getBytes(StandardCharsets.UTF_8)));
        }

        catch (MqttException e) {
            e.printStackTrace();
        } //Persistence
*/
        return v;
    }
}