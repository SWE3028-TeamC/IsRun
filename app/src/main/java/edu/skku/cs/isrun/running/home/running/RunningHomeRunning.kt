package edu.skku.cs.isrun.running.home.running

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.os.SystemClock
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Chronometer
import android.widget.Chronometer.OnChronometerTickListener
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.google.android.gms.location.*
import edu.skku.cs.isrun.R
import edu.skku.cs.isrun.running.home.RunningHomeViewModel
import java.text.SimpleDateFormat
import java.util.*


class RunningHomeRunning : Fragment(){

    private val TAG_CODE_PERMISSION_LOCATION = 1
    private var time:Long = 0
    private var distance:Double = 0.0
    private var longitude:Double = 0.0
    private var latitude:Double = 0.0
    private var running:Boolean = true
    private var pauseOffset:Long = 0
    private var timeStamp:String =""

    companion object {
        fun newInstance() = RunningHomeRunning()
    }

    private lateinit var distanceView: TextView
    private lateinit var averagePace: TextView
    private lateinit var client:FusedLocationProviderClient
    private lateinit var locationCallback:LocationCallback
    private val viewModel: RunningHomeViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.running_home_running_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    @SuppressLint("SetTextI18n", "UseSwitchCompatOrMaterialCode")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRunButton(view)

        val goal_time = view.findViewById<TextView>(R.id.goal_time)
        val goal_distance = view.findViewById<TextView>(R.id.goal_distance)
        distanceView = view.findViewById<TextView>(R.id.total_distance)
        averagePace = view.findViewById<TextView>(R.id.average_pace)
        val chronometer:Chronometer = view.findViewById(R.id.total_time)
        val start_pauseBtn:Button = view.findViewById(R.id.start_pauseBtn)

        val distanceObserver = Observer<Double> { distance ->
            distanceView.text = "$distance m"
        }

        viewModel.distance.observe(viewLifecycleOwner, distanceObserver)

        client = LocationServices.getFusedLocationProviderClient(requireActivity())

        try{
            Log.e("Running","$running")
            if(running){
                if (ActivityCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    Log.e("Permission", PackageManager.PERMISSION_GRANTED.toString())

                    ActivityCompat.requestPermissions(
                        requireActivity(), arrayOf(
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        ),
                        TAG_CODE_PERMISSION_LOCATION
                    )
                    return
                }else{
                    Log.e("GPS","Working")
                    getCurrentLocation()
                }
            }else{
                Log.e("GPS","Not working")
            }
        }catch(e: SecurityException){
            Log.e("SecurityException","$e")
        }

        goal_time.text = viewModel.toStringtime()
        goal_distance.text = viewModel.toStringdistance()

        chronometer.onChronometerTickListener = OnChronometerTickListener { cArg ->
            time = SystemClock.elapsedRealtime() - cArg.base
            viewModel.time.value = time
            val hour = (time / 3600000).toInt()
            val minutes = (time - hour * 3600000).toInt() / 60000
            val seconds = (time - hour * 3600000 - minutes * 60000).toInt() / 1000
            cArg.text = String.format("%02d:%02d:%02d",hour,minutes,seconds)
        }

        start_pauseBtn.setOnClickListener {
            if(running){
                chronometer.stop()
                pauseOffset = SystemClock.elapsedRealtime() - chronometer.base
                running = false
                client.removeLocationUpdates(locationCallback)
                start_pauseBtn.text = "resume"
            }else{
                chronometer.base = SystemClock.elapsedRealtime() - pauseOffset
                chronometer.start()
                running = true
                getCurrentLocation()
                start_pauseBtn.text = "pause"
            }
        }
        chronometer.base = SystemClock.elapsedRealtime() - pauseOffset
        chronometer.start()
        running = true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == 100 && grantResults.isNotEmpty() &&
            (grantResults[0] + grantResults[1] == PackageManager.PERMISSION_GRANTED)){
            getCurrentLocation()
        }else{
            Toast.makeText(context, "Permission Denied!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    @SuppressLint("MissingPermission")
    private fun getCurrentLocation(){
        val locationManger = activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if(locationManger.isProviderEnabled(LocationManager.GPS_PROVIDER)
            || locationManger.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
            client.lastLocation
                .addOnSuccessListener { location ->
                    if( location != null){
                        longitude = location.longitude
                        latitude = location.latitude
//                         averagePace.text = "$latitude"
                        timeStamp = getCurrentTimeStamp()!!
                        Log.e("My location ","Longitude : ${location.longitude}, Latitude ${location.latitude}")
                    }
                    val locationRequest = LocationRequest.create()
                    locationRequest.run {
                        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
                        interval = 1000
                    }

                    locationCallback = object: LocationCallback() {
                        override fun onLocationResult(locationResult: LocationResult) {
                            val loc_inCallback = locationResult.lastLocation
                            distance += viewModel.getGPSDistance(latitude, longitude, loc_inCallback.latitude, loc_inCallback.longitude)
                            longitude = loc_inCallback.longitude
                            latitude = loc_inCallback.latitude
//                             averagePace.text = "$latitude"
                            timeStamp = getCurrentTimeStamp()!!
                            Log.e("My location ","Time : $timeStamp, Longitude : ${loc_inCallback.longitude}, Latitude ${loc_inCallback.latitude}, Distance : $distance")

                        }
                    }
                    Looper.myLooper()?.let {
                        client.requestLocationUpdates(locationRequest, locationCallback, it)
                    }

                }
                .addOnFailureListener{
                    Log.e("GPS", "Location error: ${it.message}")
                    it.printStackTrace()
                }
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun getCurrentTimeStamp(): String? {
        return try {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.ssss")
            dateFormat.format(Date())
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    // function for setting run button to next navigation
    private fun setRunButton(view: View) {
        val navController = Navigation.findNavController(view)
        view.findViewById<Button>(R.id.finishBtn).setOnClickListener {
            navController.navigate(R.id.action_running_home_running_to_running_home_result)
        }
    }
}

