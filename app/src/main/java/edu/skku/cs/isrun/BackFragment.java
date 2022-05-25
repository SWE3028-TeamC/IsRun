package edu.skku.cs.isrun;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

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

    public String setimage (boolean okay, String imgname, ImageView img ,ImageView big) {
        String ret;
        if (okay) {
            ret=imgname;
            int resid = getResources().getIdentifier(imgname, "drawable", this.getActivity().getPackageName());
            img.setOnClickListener(view -> {
                big.setImageResource(resid);
                change=imgname;
            });

        }
        else {
            ret = imgname+"_b";

            int resid = getResources().getIdentifier(ret, "drawable", this.getActivity().getPackageName());
            img.setImageResource(resid);
        }
        return ret;
    }
    private void mqttgoget (String aa, String topic) {
        final String MQTT_BROKER_IP = "tcp://ec2-52-79-242-94.ap-northeast-2.compute.amazonaws.com:1883";
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
                    client.disconnect();
                }
            });
            client.subscribe(((MainActivity_game)getActivity()).uid+"/#", 2);
            client.publish(topic,new MqttMessage(aa.getBytes(StandardCharsets.UTF_8)));

        }

        catch (MqttException e) {
            e.printStackTrace();
        } //Persistence
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_back, container, false);

        //((MainActivity_game)getActivity()).gett("NONE","NONE");

        ImageView img = v.findViewById(R.id.gameBackground);
        ImageView img2 = v.findViewById(R.id.poster_2);
        ImageView img3 = v.findViewById(R.id.poster_3);
        ImageView img4 = v.findViewById(R.id.poster_4);
        ImageView img5 = v.findViewById(R.id.poster_5);
        ImageView img6 = v.findViewById(R.id.poster_6);
        ImageView img7 = v.findViewById(R.id.poster_7);
        ImageView img8 = v.findViewById(R.id.poster_8);
        ImageView img9 = v.findViewById(R.id.poster_9);
        ImageView img10 = v.findViewById(R.id.poster_10);
        ImageView img11 = v.findViewById(R.id.poster_11);
        ImageView img12 = v.findViewById(R.id.poster_12);
        ImageView img13 = v.findViewById(R.id.poster_13);
        ImageView img14 = v.findViewById(R.id.poster_14);
        ImageView img15 = v.findViewById(R.id.poster_15);


        Bundle bundle = getArguments();
        int[] bgchange = bundle.getIntArray("posters");


        setimage(Arrays.stream(bgchange).anyMatch(a->a==1),"bg1",img2,img);
        setimage(Arrays.stream(bgchange).anyMatch(a->a==2),"bg2",img3,img);
        setimage(Arrays.stream(bgchange).anyMatch(a->a==3),"bg3",img4,img);
        setimage(Arrays.stream(bgchange).anyMatch(a->a==4),"bg4",img5,img);
        setimage(Arrays.stream(bgchange).anyMatch(a->a==5),"bg5",img6,img);
        setimage(Arrays.stream(bgchange).anyMatch(a->a==6),"bg6",img7,img);
        setimage(Arrays.stream(bgchange).anyMatch(a->a==7),"bg7",img8,img);
        setimage(Arrays.stream(bgchange).anyMatch(a->a==8),"bg8",img9,img);
        setimage(Arrays.stream(bgchange).anyMatch(a->a==9),"bg9",img10,img);
        setimage(Arrays.stream(bgchange).anyMatch(a->a==10),"bg10",img11,img);
        setimage(Arrays.stream(bgchange).anyMatch(a->a==11),"bg11",img12,img);
        setimage(Arrays.stream(bgchange).anyMatch(a->a==12),"bg12",img13,img);
        setimage(Arrays.stream(bgchange).anyMatch(a->a==13),"bg13",img14,img);
        setimage(Arrays.stream(bgchange).anyMatch(a->a==14),"bg14",img15,img);

        ImageView img1 = v.findViewById(R.id.poster_1);
        img1.setOnClickListener(view -> {
            img.setImageResource(R.drawable.image_2);
            change = "image_2";
        });

        Button btn = v.findViewById(R.id.set);
        btn.setOnClickListener(view-> {
            ((MainActivity_game)getActivity()).gett(change,"NONE");
            String aa = "{\"UserId\":\""+((MainActivity_game)getActivity()).uid+"\"," +
                    " \"mposteridx\":"+((MainActivity_game)getActivity()).convertPost(change)+"}";
            mqttgoget(aa,"UserData/UpdateUserData");
            System.out.println(change);
        });
        return v;
    }
}