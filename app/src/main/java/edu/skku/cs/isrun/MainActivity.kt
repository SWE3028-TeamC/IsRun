package edu.skku.cs.isrun

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kakao.util.maps.helper.Utility
import edu.skku.cs.isrun.databinding.ActivityMainBinding
import edu.skku.cs.isrun.running.home.RunningHomeViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val runViewModel: RunningHomeViewModel by viewModels()
    private val uid = "testid"
    var app_character_list = arrayOf("cat", "dog", "hamster", "parrot", "bunny", "lion", "seal", "shiba")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        runViewModel.RunningHomeViewModel()
        runViewModel.uid.value = uid

        binding = ActivityMainBinding.inflate(layoutInflater)
        // Hiding action bar on the top
        supportActionBar?.hide()
        setContentView(binding.root)

        val navView = findViewById<BottomNavigationView>(R.id.nav_view)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration: AppBarConfiguration = AppBarConfiguration(
            setOf(R.id.running_home, R.id.running_achievement, R.id.running_record, R.id.running_landmark)
        )

        // Intent to run
        findViewById<Button>(R.id.modeBtn).setOnClickListener {
            val gameIntent = Intent(this, MainActivity_game::class.java).apply{
                // putExtra uid
            }
            startActivity(gameIntent)
        }

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController

        setupActionBarWithNavController(this, navController, appBarConfiguration)

        binding.navView.setupWithNavController(navController)

        val keyHash = Utility.getKeyHash(this)
        Log.e("Hash key kakao", keyHash)

        // Todo get userdata and set to viewModel
        //  new Thread?
        runViewModel.UpdateUser()
    }


}