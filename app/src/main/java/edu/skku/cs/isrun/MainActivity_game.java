package edu.skku.cs.isrun;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.nio.charset.StandardCharsets;

public class MainActivity_game extends AppCompatActivity {
    ImageView ch;
    ImageView bg;
    NavigationBarView nv;
    CharFragment charFragment;
    HomeFragment homeFragment;
    BackFragment backFragment;
    StorFragment storFragment;
    String background="image_2";
    String character="dog";
    public String uid;
    public String[] app_character_list = new String[]{"cat","dog","hamster","parrot","bunny","lion","seal","shiba"};

    public int convertChar(String str) {
        switch (str) {
            case "cat":
                return 0;
            case "dog":
                return 1;
            case "hamster":
                return 2;
            case "parrot":
                return 3;
            case "bunny":
                return 4;
            case "lion":
                return 5;
            case "seal":
                return 6;
            case "shiba":
                return 7;
        }
        return -1;
    }

    public int convertPost(String str) {
        switch (str) {
            case "image_2":
                return 0;
            case "bg1":
                return 1;
            case "bg2":
                return 2;
            case "bg3":
                return 3;
            case "bg4":
                return 4;
            case "bg5":
                return 5;
            case "bg6":
                return 6;
            case "bg7":
                return 7;
            case "bg8":
                return 8;
            case "bg9":
                return 9;
            case "bg10":
                return 10;
            case "bg11":
                return 11;
            case "bg12":
                return 12;
            case "bg13":
                return 13;
            case "bg14":
                return 14;
        }
        return -1;
    }

    // 서버
    // userdata받아오기.. food gold character landmark poster 등등
    //UserData_game userdata_game = new UserData_game();
    public UserGameData userdata_game = new UserGameData();
    UserGameData temp = new UserGameData();
    String response;
    public void gett (String back, String charr) {
        if (!(back.equals("NONE"))) {
            userdata_game.setMposteridx(convertPost(back));
        }
        if (!(charr.equals("NONE"))) {
            userdata_game.setMcharidx(convertChar(charr));
        }
    }
    public int getitems (int feed, int play, int gold) {
        if (feed==1) {
            System.out.println(userdata_game.getFood());
            if (userdata_game.getFood()<1) {
                Toast.makeText(this, "Not enough food!", Toast.LENGTH_SHORT).show();
                return 0;
            }
            else {
                userdata_game.Feed();
                return 1;
            }
        }
        else if (play==1) {
            return 0;
        }
        if (gold>0) {
            if (userdata_game.getGold()>=gold) {
                int temp = userdata_game.getGold()-gold;
                System.out.println(temp);
                userdata_game.setGold(temp);
                return userdata_game.getGold();
            }
            else {
                Toast.makeText(this, "Not enough gold!", Toast.LENGTH_SHORT).show();
                return 0;
            }
        }
        return 0;
    }
    public void mqttgoget (String aa, String topic) {
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
                public void deliveryComplete(IMqttDeliveryToken arg0) {
                }
                @Override
                public void messageArrived(String arg0, MqttMessage arg1) throws Exception {
                    response = arg1.toString();
                    //System.out.println(arg0 + ": " + arg1.toString());
                    System.out.println(response);
                    Gson gson = new GsonBuilder().create();

                    if (arg0.equals(uid+"/GetUserData")) {
                        userdata_game = gson.fromJson(response, UserGameData.class);
                        System.out.println(userdata_game.getMcharidx());
                        System.out.println(userdata_game.getMposteridx());
                        System.out.println(userdata_game.getFood());
                        System.out.println(userdata_game.getGold());
                        System.out.println(userdata_game.getUserid());
                    }
                    else if (arg0.equals(uid+"/GetUserChars")) {

                        temp = gson.fromJson(response, UserGameData.class);
                        InnerData[] a = new InnerData[10];
                        InnerData t = new InnerData();
                        int c=0;

                        int[] b = new int[10];
                        c=0;
                        for (InnerData i : temp.getUserChars()) {
                            b[i.getCharidx()]=i.getCharidx();
                            c++;
                        }
                        userdata_game.setCharacter_list(b);

                        for (int i=0;i<10;i++) {
                            System.out.println(userdata_game.getCharacter_list()[i]);
                        }
                        c=0;
                        int k=1;
                        for (int kk:userdata_game.getCharacter_list()) {
                            System.out.println(kk+":"+c);
                            if (kk==0)
                                a[c]=t;
                            else{
                                a[c] = temp.getUserChars()[k];
                                k++;
                            }
                            c++;
                        }
                        a[0] = temp.getUserChars()[0];
                        userdata_game.setUserChars(a);
                        for (int i=0;i<10;i++) {
                            System.out.println(userdata_game.getUserChars()[i].getCharidx());
                        }
                    }
                    else if (arg0.equals(uid+"/GetUserPosters")) {

                        temp = gson.fromJson(response, UserGameData.class);
                        InnerData2[] a = new InnerData2[20];
                        int c=0;
                        for (InnerData2 i : temp.getUserPosters()) {
                            a[c]=i;
                            c++;
                        }
                        userdata_game.setUserPosters(a);

                        int[] b = new int[20];
                        c=0;
                        for (InnerData2 i : temp.getUserPosters()) {
                            b[c]=i.getPosteridx();
                            System.out.println(b[c]);
                            c++;
                        }
                        userdata_game.setPoster_list(b);
                    }
                    client.disconnect();
                }
            });
            client.subscribe(uid+"/#", 2);
            client.publish(topic,new MqttMessage(aa.getBytes(StandardCharsets.UTF_8)));
        }
        catch (MqttException e) {
            e.printStackTrace();
        } //Persistence
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_game);


        ImageView loading = findViewById(R.id.imageView3);
        Glide.with(this).load(R.raw.rollcat).into(loading);
        loading.bringToFront();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                loading.setVisibility(View.INVISIBLE);
                Bundle bundle = new Bundle();
                bundle.putString("background", "bg"+userdata_game.getMposteridx());
                bundle.putString("character", app_character_list[userdata_game.getMcharidx()]);
                homeFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();
            }
        }, 2000);

        uid = "testid";
        Thread t = new Thread() {
            public void run () {
                try {
                    String aa = "{\"UserId\":\""+uid+"\"}";
                    mqttgoget(aa,"UserData/GetUserData");
                    mqttgoget(aa,"UserData/GetUserChars");
                    mqttgoget(aa,"UserData/GetUserPosters");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        };

        t.setDaemon(true);
        t.start();

        ActionBar actb = getSupportActionBar();
        actb.hide();
        nv=findViewById(R.id.game_nav_view);
        charFragment =  new CharFragment();
        homeFragment =  new HomeFragment();
        backFragment =  new BackFragment();
        storFragment =  new StorFragment();

        /*for (int ii=0;ii<10;ii++) {
            System.out.print(userdata_game.getUserChars()[ii].getCharidx()+" ");
            System.out.print(userdata_game.getUserChars()[ii].getCharname()+" ");
            System.out.print(userdata_game.getUserChars()[ii].getCharLV()+" ");
            System.out.print(userdata_game.getUserChars()[ii].getCharexp()+" ");
            System.out.print(userdata_game.getUserChars()[ii].getCharlove()+" ");
            System.out.println(userdata_game.getUserChars()[ii].getCharfull()+" ");
        }

         */

        nv.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()) {
                    case R.id.gaming_character:
                        Bundle bundle1 = new Bundle();
                        bundle1.putIntArray("characters",userdata_game.getCharacter_list());
                        charFragment.setArguments(bundle1);
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, charFragment).commit();
                        return true;
                    case R.id.gaming_background:
                        Bundle bundle2 = new Bundle();
                        bundle2.putIntArray("posters",userdata_game.getPoster_list());
                        backFragment.setArguments(bundle2);
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, backFragment).commit();
                        return true;
                    case R.id.gaming_store:
                        Bundle bundle3 = new Bundle();
                        bundle3.putInt("gold",userdata_game.getGold());
                        bundle3.putIntArray("characters",userdata_game.getCharacter_list());
                        bundle3.putIntArray("posters",userdata_game.getPoster_list());
                        storFragment.setArguments(bundle3);
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, storFragment).commit();
                        return true;

                    case R.id.gaming_home:
                            Bundle bundle4 = new Bundle();
                            bundle4.putString("background", "bg"+userdata_game.getMposteridx());
                            bundle4.putString("character", app_character_list[userdata_game.getMcharidx()]);
                            homeFragment.setArguments(bundle4);

                        getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();
                        return true;
                }
                return false;
            }
        });


    }
}