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
    String background="NONE";
    String character="NONE";

    // 서버
    // userdata받아오기.. food gold character landmark poster 등등
    public void gett (String back, String charr) {
        if (!(back.equals("NONE"))) {
            background = back;
        }
        if (!(charr.equals("NONE"))) {
            character = charr;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ActionBar actb = getSupportActionBar();
        actb.hide();
        nv=findViewById(R.id.game_nav_view);
        charFragment =  new CharFragment();
        homeFragment =  new HomeFragment();
        backFragment =  new BackFragment();
        storFragment =  new StorFragment();
        FragmentManager fm = getSupportFragmentManager();

        getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();
        nv.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()) {
                    case R.id.gaming_character:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, charFragment).commit();
                        return true;
                    case R.id.gaming_background:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, backFragment).commit();
                        return true;
                    case R.id.gaming_store:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, storFragment).commit();
                        return true;

                    case R.id.gaming_home:
                        if (background!=null || character!=null) {
                            Bundle bundle = new Bundle();
                            bundle.putString("background", background);
                            bundle.putString("character", character);
                            homeFragment.setArguments(bundle);
                        }

                        getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();
                        return true;
                }
                return false;
            }
        });


    }
}