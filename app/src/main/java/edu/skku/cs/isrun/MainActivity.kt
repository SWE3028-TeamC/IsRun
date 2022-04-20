package edu.skku.cs.isrun

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import edu.skku.cs.isrun.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.getRoot())

        val navView = findViewById<BottomNavigationView>(R.id.run_nav_view)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration: AppBarConfiguration = AppBarConfiguration.Builder(
            R.id.running_home, R.id.running_achievement, R.id.running_record, R.id.running_landmark
        ).build()

        val navController = findNavController(this, R.id.nav_host_fragment_main_activity)
        setupActionBarWithNavController(this, navController, appBarConfiguration)
        setupWithNavController(binding!!.runNavView, navController)
    }
}