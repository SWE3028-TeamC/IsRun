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


import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.nio.charset.StandardCharsets;
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
    public void charset(String img, int idx, String talk) {
        if (((MainActivity_game)getActivity()).userdata_game.getUserChars()[idx]==null) {
            return;
        }
        charpopup chh;
        chh = new charpopup();
        int resid ;
        resid = getResources().getIdentifier(img,"drawable",this.getActivity().getPackageName());
        String name;
        if (((MainActivity_game)getActivity()).userdata_game.getUserChars()[idx].getCharname()==null) {
            name=((MainActivity_game)getActivity()).app_character_list[idx];
        }
        else {
            name=((MainActivity_game)getActivity()).userdata_game.getUserChars()[idx].getCharname();
        }
        int LV = ((MainActivity_game)getActivity()).userdata_game.getUserChars()[idx].getCharLV();
        String info = "Level: "+LV;

        chh.setImg(img);
        chh.setLevel(LV);
        chh.setName(name);
        chh.setResID(resid);
        chh.setTalk(talk);
        chh.setInfo(info);
        characterlist.add(chh);
    }
    public void popUpImg(int resId, String img, Context context, String memo, String info) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.char_popup, null);
        ImageView imageView = view.findViewById(R.id.popup_img);
        TextView textView = view.findViewById(R.id.popup_txt);
        TextView textView2 = view.findViewById(R.id.popup_memo);

        imageView.setImageResource(resId);
        textView.setText(memo);
        textView2.setText(info);

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
                    String aa = "{\"UserId\":\""+((MainActivity_game)getActivity()).uid+"\"," +
                            " \"mcharidx\":"+((MainActivity_game)getActivity()).convertChar(img)+"}";
                    mqttgoget(aa,"UserData/UpdateUserData");
                    dialog.dismiss();
                }
            }
        });

        AlertDialog dialog;
        dialog = builder.create();
        dialog.show();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_char, container, false);
        characterlist = new ArrayList<charpopup>();
        plate = v.findViewById(R.id.grid);

        Bundle bundle = getArguments();
        int[] chchange = bundle.getIntArray("characters");

        /*for (int i:chchange) {
            System.out.println(i);
        }

         */
        if (Arrays.stream(chchange).anyMatch(a->a==0))
            charset("cat",0,"Cat");
        if (Arrays.stream(chchange).anyMatch(a->a==1))
            charset("dog",1, "Dog");
        if (Arrays.stream(chchange).anyMatch(a->a==2))
            charset("hamster",2,"Hamster");
        if (Arrays.stream(chchange).anyMatch(a->a==3))
            charset("parrot",3,"Parrot");
        if (Arrays.stream(chchange).anyMatch(a->a==4))
            charset("bunny",4,"Bunny");
        if (Arrays.stream(chchange).anyMatch(a->a==5))
            charset("lion",5,"Lion");
        if (Arrays.stream(chchange).anyMatch(a->a==6))
            charset("seal",6,"Seal");
        if (Arrays.stream(chchange).anyMatch(a->a==7))
            charset("shiba",7,"Shiba");

        gridViewAdapter = new GridViewAdapter(getContext(),characterlist);
        plate.setAdapter(gridViewAdapter);

        plate.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String charimg = (String)gridViewAdapter.getCharimg(position);
                int resid = (Integer)gridViewAdapter.getResID(position);

                System.out.println(charimg+resid);
                popUpImg(resid,charimg,getContext(),gridViewAdapter.getMemo(position),gridViewAdapter.getInfo(position));
                //Toast.makeText(getContext(), charname, Toast.LENGTH_SHORT).show();
            }
        });
        return v;
    }
}