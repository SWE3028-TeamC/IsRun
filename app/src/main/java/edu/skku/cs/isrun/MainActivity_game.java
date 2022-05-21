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
    String character="cat";
    private MqttAndroidClient mqttAndroidClient;


    // 서버
    // userdata받아오기.. food gold character landmark poster 등등
    UserData_game userdata_game = new UserData_game();

    public void gett (String back, String charr) {
        if (!(back.equals("NONE"))) {
            userdata_game.setMainposter(back);
        }
        if (!(charr.equals("NONE"))) {
            userdata_game.setMainchar(charr);
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
                return 1;
            }
            else {
                Toast.makeText(this, "Not enough gold!", Toast.LENGTH_SHORT).show();
                return 1;
            }
        }
        return 0;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        // User initial data setup
        // 앱 실행시에 서버에서 받아오면 userdata_game에 모두 저장
        // 앱 실행중 값 업데이트시에 서버로 바로 보내기??? <-미구현


        userdata_game.setUserid("aaaa11");

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
                    System.out.println(arg0 + ": " + arg1.toString());
                    Toast.makeText(MainActivity_game.this, arg1.toString(), Toast.LENGTH_SHORT).show();
                }
            });
            String aa = "{\"UserId\":\"qwer1\"}";

            client.subscribe("qwer1/#", 2);
            client.publish("UserData/GetUserChars",new MqttMessage(aa.getBytes(StandardCharsets.UTF_8)));

        }

        catch (MqttException e) {
            e.printStackTrace();
        } //Persistence



        int[] temp = {0,1,4,5,6,7};
        userdata_game.setCharacter_list(temp);
        userdata_game.setFood(5);
        userdata_game.setGold(1000);
        userdata_game.setPoster_list(new int[]{0,3,4,5,8,9,10,11,12,13,14});
        userdata_game.setMainchar("dog");
        userdata_game.setMainposter("image_2");








        ActionBar actb = getSupportActionBar();
        actb.hide();
        nv=findViewById(R.id.game_nav_view);
        charFragment =  new CharFragment();
        homeFragment =  new HomeFragment();
        backFragment =  new BackFragment();
        storFragment =  new StorFragment();


        Bundle bundle = new Bundle();
        bundle.putString("background", userdata_game.getMainposter());
        bundle.putString("character", userdata_game.getMainchar());
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
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, storFragment).commit();
                        return true;

                    case R.id.gaming_home:
                            Bundle bundle4 = new Bundle();
                            bundle4.putString("background", userdata_game.getMainposter());
                            bundle4.putString("character", userdata_game.getMainchar());
                            homeFragment.setArguments(bundle4);

                        getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();
                        return true;
                }
                return false;
            }
        });


    }
}