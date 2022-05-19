package edu.skku.cs.isrun;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    ImageView ch;
    ImageView bg;
    NavigationBarView nv;
    CharFragment charFragment;
    HomeFragment homeFragment;
    BackFragment backFragment;
    StorFragment storFragment;
    String background="image_2";
    String character="cat";

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);







        // User initial data setup
        // 앱 실행시에 서버에서 받아오면 userdata_game에 모두 저장
        // 앱 실행중 값 업데이트시에 서버로 바로 보내기??? <-미구현


        userdata_game.setUserid("aaaa11");
        int[] temp = {0,1};
        userdata_game.setCharacter_list(temp);
        userdata_game.setFood(5);
        userdata_game.setGold(10);
        userdata_game.setPoster_list(new int[]{0,3,4,5,8,9,10});
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