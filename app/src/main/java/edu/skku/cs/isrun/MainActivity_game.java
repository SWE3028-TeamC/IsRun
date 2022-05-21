package edu.skku.cs.isrun;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
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

    private String[] app_character_list = new String[]{"cat","dog","hamster","parrot","bunny","lion","seal","shiba"};

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
                return 3;
            case "bg3":
                return 4;
            case "bg4":
                return 5;
            case "bg5":
                return 6;
            case "bg6":
                return 7;
            case "bg7":
                return 8;
            case "bg8":
                return 9;
            case "bg9":
                return 10;
            case "bg10":
                return 11;
            case "bg11":
                return 12;
            case "bg12":
                return 13;
            case "bg13":
                return 14;
            case "bg14":
                return 15;
        }
        return -1;
    }

    // 서버
    // userdata받아오기.. food gold character landmark poster 등등
    //UserData_game userdata_game = new UserData_game();
    UserGameData userdata_game = new UserGameData();
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
            if (userdata_game.getGold()>gold) {
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        String uid = "testid";

        // userchars test
        // userdata(setCharacter_list) + appchars 전체캐릭터 + 전체 레벨데이터
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
                    System.out.println(arg0 + ": " + arg1.toString());
                    Gson gson = new GsonBuilder().create();
                    userdata_game = gson.fromJson(response, UserGameData.class);
                    System.out.println(userdata_game.getMcharidx());
                    System.out.println(userdata_game.getMposteridx());
                    System.out.println(userdata_game.getFood());
                    System.out.println(userdata_game.getGold());
                    System.out.println(userdata_game.getUserid());
                    client.disconnect();
                }
            });
            String aa = "{\"UserId\":\""+uid+"\"}";

            client.subscribe(uid+"/#", 2);
            client.publish("UserData/GetUserData",new MqttMessage(aa.getBytes(StandardCharsets.UTF_8)));

        }
        catch (MqttException e) {
            e.printStackTrace();
        } //Persistence
        setContentView(R.layout.activity_main);

        // 임시로 0설정, 콜에 mposteridx 추가되면 빼면됨
        userdata_game.setMposteridx(0);

        // User initial data setup
        // 앱 실행시에 서버에서 받아오면 userdata_game에 모두 저장
        // 앱 실행중 값 업데이트시에 서버로 바로 보내기??? <-미구현



        /*int[] temp = {0,1,4,5,6,7};
        userdata_game.setCharacter_list(temp);
        userdata_game.setFood(5);
        userdata_game.setGold(1000);
        userdata_game.setPoster_list(new int[]{0,3,4,5,8,9,10,11,12,13,14});
        userdata_game.setMainchar("dog");
        userdata_game.setMainposter("image_2");
        */

        ActionBar actb = getSupportActionBar();
        actb.hide();
        nv=findViewById(R.id.game_nav_view);
        charFragment =  new CharFragment();
        homeFragment =  new HomeFragment();
        backFragment =  new BackFragment();
        storFragment =  new StorFragment();



        Bundle bundle = new Bundle();
        bundle.putString("background", "bg"+userdata_game.getMposteridx());
        bundle.putString("character", app_character_list[userdata_game.getMcharidx()]);
        homeFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();
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