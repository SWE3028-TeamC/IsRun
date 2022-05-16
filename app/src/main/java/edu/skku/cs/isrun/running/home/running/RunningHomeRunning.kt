package edu.skku.cs.isrun.running.home.running

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Chronometer
import android.widget.Chronometer.OnChronometerTickListener
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import edu.skku.cs.isrun.R
import edu.skku.cs.isrun.running.home.RunningHomeViewModel


class RunningHomeRunning : Fragment() {

    private var hour:Int = 0
    private var minutes:Int = 0
    private var seconds:Int = 0
    private var distance:Double = 0.0
    private lateinit var myLocation:Location
    private var running:Boolean = false
    private var pauseOffset:Long = 0
    private lateinit var viewModel: RunningHomeViewModel

    companion object {
        fun newInstance() = RunningHomeRunning()
    }

//    private var viewModel: RunningHomeViewModel by navGraphViewModels(R.id.running_home)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.running_home_running_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[RunningHomeViewModel::class.java]
        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRunButton(view)
        viewModel = ViewModelProvider(this)[RunningHomeViewModel::class.java]

        val locationManger = context?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (this.context?.let {
                ActivityCompat.checkSelfPermission(
                    it,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            } != PackageManager.PERMISSION_GRANTED && this.context?.let {
                ActivityCompat.checkSelfPermission(
                    it,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            } != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        locationManger.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5f, locationListener)

        val goal_time = view.findViewById<TextView>(R.id.goal_time)
        val goal_distance = view.findViewById<TextView>(R.id.goal_distance)
        val chronometer:Chronometer = view.findViewById(R.id.total_time)
        val start_pauseBtn:Button = view.findViewById(R.id.start_pauseBtn)

        goal_time.text = viewModel.toStringtime()
        goal_distance.text = viewModel.toStringdistance()

        chronometer.onChronometerTickListener = OnChronometerTickListener { cArg ->
            val time = SystemClock.elapsedRealtime() - cArg.base
            hour = (time / 3600000).toInt()
            minutes = (time - hour * 3600000).toInt() / 60000
            seconds = (time - hour * 3600000 - minutes * 60000).toInt() / 1000
            cArg.text = String.format("%02d:%02d:%02d",hour,minutes,seconds)
        }

        start_pauseBtn.setOnClickListener {
            if(running){
                chronometer.stop()
                pauseOffset = SystemClock.elapsedRealtime() - chronometer.base
                running = false
                start_pauseBtn.text = "resume"
            }else{
                chronometer.base = SystemClock.elapsedRealtime() - pauseOffset
                chronometer.start()
                running = true
                start_pauseBtn.text = "pause"
            }
        }
        chronometer.base = SystemClock.elapsedRealtime() - pauseOffset
        chronometer.start()
        running = true
    }

    // function for setting run button to next navigation
    private fun setRunButton(view: View) {
        // link time and distance to viewModel or pass it by Bundle
        val navController = Navigation.findNavController(view)
        view.findViewById<Button>(R.id.finishBtn).setOnClickListener {
            navController.navigate(R.id.action_running_home_running_to_running_home_result)
        }
    }

    private var locationListener: LocationListener = object : LocationListener{
        override fun onLocationChanged(loc: Location) {
            myLocation = loc
            Log.e("My location ","Longitude : ${loc.longitude}, Latitude ${loc.latitude}")
        }

    }

}

