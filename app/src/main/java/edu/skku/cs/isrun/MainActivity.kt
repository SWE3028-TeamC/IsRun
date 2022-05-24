package edu.skku.cs.isrun

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.Layout
import android.util.Base64
import android.util.Log
import android.view.ViewGroup
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kakao.util.maps.helper.Utility
import edu.skku.cs.isrun.databinding.ActivityMainBinding
import edu.skku.cs.isrun.running.home.RunningHomeViewModel
import net.daum.mf.map.api.MapView
import java.security.MessageDigest

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val runViewModel: RunningHomeViewModel by viewModels()
    private val mode = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        runViewModel.RunningHomeViewModel()

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

        // Intent
        findViewById<Button>(R.id.modeBtn).setOnClickListener {
            val gameIntent = Intent(this, MainActivity_game::class.java).apply{ }
            startActivity(gameIntent)
        }

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController

        setupActionBarWithNavController(this, navController, appBarConfiguration)

        binding.navView.setupWithNavController(navController)

        var keyHash = Utility.getKeyHash(this)
        Log.e("Hash key kakao", keyHash)
//        // function for hashcode for debugging
//        fun getAppKeyHash() {
//            try {
//                val info =
//                    packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
//                for (signature in info.signatures) {
//                    var md: MessageDigest
//                    md = MessageDigest.getInstance("SHA")
//                    md.update(signature.toByteArray())
//                    val something = String(Base64.encode(md.digest(), 0))
//                    Log.e("Hash key", something)
//                }
//            } catch (e: Exception) {
//
//                Log.e("name not found", e.toString())
//            }
//        }
//        getAppKeyHash()

    }


}