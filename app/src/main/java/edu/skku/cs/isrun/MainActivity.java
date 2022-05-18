package edu.skku.cs.isrun;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nv=findViewById(R.id.game_nav_view);
        charFragment =  new CharFragment();
        homeFragment =  new HomeFragment();
        backFragment =  new BackFragment();
        storFragment =  new StorFragment();

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
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();
                        return true;
                }
                return false;
            }
        });


    }
}