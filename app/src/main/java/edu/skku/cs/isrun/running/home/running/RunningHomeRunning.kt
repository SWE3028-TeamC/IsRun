package edu.skku.cs.isrun.running.home.running

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.os.Handler
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
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.google.android.gms.location.*
import com.google.gson.GsonBuilder
import edu.skku.cs.isrun.R
import edu.skku.cs.isrun.RunResult
import edu.skku.cs.isrun.running.home.GPSdata
import edu.skku.cs.isrun.running.home.RunningHomeViewModel
import org.eclipse.paho.client.mqttv3.*
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence
import java.nio.charset.StandardCharsets
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*


class RunningHomeRunning : Fragment(){

    private val TAG_CODE_PERMISSION_LOCATION = 1
    private var time:Long = 0
    private var longitude:Double = 0.0
    private var latitude:Double = 0.0
    private var running:Boolean = true
    private var pauseOffset:Long = 0
    private var timeStamp:String =""
    private var gpsLog: ArrayList<GPSdata> = ArrayList()

    companion object {
        fun newInstance() = RunningHomeRunning()
    }

    private lateinit var distanceView: TextView
    private lateinit var averagePace: TextView
    private lateinit var percent: TextView
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

        val goal_time = view.findViewById<TextView>(R.id.goal_time)
        val goal_distance = view.findViewById<TextView>(R.id.goal_distance)
        distanceView = view.findViewById(R.id.total_distance)
        averagePace = view.findViewById(R.id.average_pace)
        percent = view.findViewById(R.id.percent)
        val chronometer:Chronometer = view.findViewById(R.id.total_time)
        val start_pauseBtn:Button = view.findViewById(R.id.start_pauseBtn)

        val distanceObserver = Observer<Double> { distance ->
            distanceView.text = "${DecimalFormat("####0.0").format(distance)} m"
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

        goal_time.text = viewModel.toStringtimeSet()
        goal_distance.text = viewModel.toStringdistanceSet()

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

        val navController = Navigation.findNavController(view)
        view.findViewById<Button>(R.id.finishBtn).setOnClickListener {
            client.removeLocationUpdates(locationCallback)
            // Todo send to server
            //  RunData/RunEnd {SenderId(testid), RunIdx(Userdata.run), End_Time(timestamp), EndLat(latitude), EndLon(longitude)}
            //  response = NewPosterIdx, NewAchivs, gold , food ,love -> show achieve?
            println("End Here")
            viewModel.UpdateGPS(gpsLog)
            gpsLog.clear()
            EndRun(timeStamp, latitude, longitude)
            val handler = Handler()
            handler.postDelayed(Runnable() { run() { } }, 3000)
            navController.navigate(R.id.action_running_home_running_to_running_home_result)
        }
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

    @SuppressLint("MissingPermission")
    private fun getCurrentLocation(){
        val locationManger = activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if(locationManger.isProviderEnabled(LocationManager.GPS_PROVIDER)
            || locationManger.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
            client.lastLocation
                .addOnSuccessListener { location ->
                    if( location != null){
                        val oldlon = longitude
                        longitude = location.longitude
                        latitude = location.latitude
                        timeStamp = getCurrentTimeStamp()!!
                        val gps = GPSdata(timeStamp, longitude, latitude)
                        gpsLog.add(gps)
                        viewModel.gpsProgress.value?.add(gps)
                        // Todo send to server
                        //  UserData(testid)/RunStart {"SenderId":(testid),
                        //                              RunIdx(Userdata.run),
                        //                              Time_Goal, Dist_Goal,
                        //                              Start_Time(timestamp),
                        //                              StartLat, StartLon}
                        //  response = NewPosterIdx
                        if(oldlon.equals(0.0)){
                            val t: Thread = object : Thread() {
                                override fun run() {
                                    try {
                                        println("Start Here")
                                        StartRun(timeStamp, latitude, longitude)
                                    } catch (e: java.lang.Exception) {
                                        e.printStackTrace()
                                    }
                                }
                            }
                            t.isDaemon = true
                            t.start()
                        }
                        Log.e("My location ","Longitude : ${location.longitude}, Latitude ${location.latitude}")
                    }

                    val locationRequest = LocationRequest.create()
                    locationRequest.run {
                        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
                        interval = 1000
                    }
                    locationCallback = object: LocationCallback() {
                        @SuppressLint("SetTextI18n")
                        override fun onLocationResult(locationResult: LocationResult) {
                            val loc_inCallback = locationResult.lastLocation
                            val distance = viewModel.getGPSDistance(latitude, longitude, loc_inCallback.latitude, loc_inCallback.longitude)
                            if(distance > 0.5){
                                longitude = loc_inCallback.longitude
                                latitude = loc_inCallback.latitude
                                averagePace.text = viewModel.getPace()
                                percent.text = "${DecimalFormat("##0.00").format(viewModel.getPercent())} %"
                                timeStamp = getCurrentTimeStamp()!!
                                // current gps
                                val gps = GPSdata(timeStamp, longitude, latitude)
                                gpsLog.add(gps)
                                viewModel.gpsProgress.value?.add(gps)
                                if(gpsLog.size == 10){
                                    // Todo send to server and flush
                                    //  RunningData/userID(testid)/RunIdx(Userdata.run)
                                    //  [{Time(timestamp), Lat(latitude), Lon(longitude)}, ...]
                                    //  no response
                                    val t: Thread = object : Thread() {
                                        override fun run() {
                                            try {
                                                println("Here")
                                                viewModel.UpdateGPS(gpsLog)
                                                gpsLog.clear()
                                            } catch (e: java.lang.Exception) {
                                                e.printStackTrace()
                                            }
                                        }
                                    }
                                    t.isDaemon = true
                                    t.start()
                                }
                                Log.e("My location ","Time : $timeStamp, Longitude : ${loc_inCallback.longitude}, Latitude ${loc_inCallback.latitude}, Distance Moved : $distance")
                            }
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

    fun mqttgoget(aa: String, topic: String?) {
        // aa = string of json format for post
        // topic selecting query for request
        val MQTT_BROKER_IP = "tcp://ec2-52-79-242-94.ap-northeast-2.compute.amazonaws.com:1883"
        try {
            val client = MqttClient(
                MQTT_BROKER_IP,  //URI
                MqttClient.generateClientId(),  //ClientId
                MemoryPersistence()
            )
            client.connect()
            client.setCallback(object : MqttCallback {
                override fun connectionLost(cause: Throwable) { //Called when the client lost the connection to the broker
                    println("Connection Lost")
                }

                override fun deliveryComplete(arg0: IMqttDeliveryToken) {}

                // arg0 = response topic
                // arg1 = response json
                @Throws(Exception::class)
                override fun messageArrived(arg0: String, arg1: MqttMessage) {
                    val response = arg1.toString()
                    Log.e("Response", response)
                    val gson = GsonBuilder().create()

                    activity?.runOnUiThread(Runnable {
                        if (arg0 == "${viewModel.uid.value}/RunStart") {
                            val rundata_response =
                                gson.fromJson(response, RunResult::class.java)
                            viewModel.runResult.value = (rundata_response)
                            println("Run Start")
                            println(rundata_response.newAchivs)
                        } else if (arg0 == "${viewModel.uid.value}/RunEnd") {
                            val rundata_response =
                                gson.fromJson(response, RunResult::class.java)
                            val old_Achivs = viewModel.runResult.value?.newAchivs

                            Log.e("Result", "Gold : ${rundata_response.gold}")
                            viewModel.runResult.value = (rundata_response)
                            if(old_Achivs != null){
                                viewModel.runResult.value?.attachNewAchi(old_Achivs)
                            }
                        }
                        client.disconnect()
                    })
                }
            })
            client.subscribe("${viewModel.uid.value}/#", 2)
            client.publish(topic, MqttMessage(aa.toByteArray(StandardCharsets.UTF_8)))
        } catch (e: MqttException) {
            e.printStackTrace()
        } //Persistence
    }

    fun StartRun(timeStamp: String, startlat:Double, startlon:Double){
        val aa = "{\"SenderId\":\"${viewModel.uid.value}\"" +
                ",\"RunIdx\":\"${viewModel.userData.value?.run}\"" +
                ",\"Time_Goal\":\"${viewModel.timeSet.value}\"" +
                ",\"Dist_Goal\":\"${viewModel.distanceSet.value}\"" +
                ",\"Start_Time\":\"$timeStamp\"" +
                ",\"StartLat\":\"$startlat\"" +
                ",\"StartLon\":\"$startlon\"}"
        println(aa)
        mqttgoget(aa, "RunData/RunStart")
    }


    fun EndRun(timeStamp: String, startlat:Double, startlon:Double){
        val aa = "{\"SenderId\":\"${viewModel.uid.value}\"" +
                ",\"RunIdx\":\"${viewModel.userData.value?.run}\"" +
                ",\"End_Time\":\"$timeStamp\"" +
                ",\"EndLat\":\"$startlat\"" +
                ",\"EndLon\":\"$startlon\"" +
                ",\"Dist_Achv\":\"${viewModel.distance.value}\"}"
        println(aa)
        viewModel.userData.value?.run = viewModel.userData.value?.run?.plus(1)!!
        mqttgoget(aa, "RunData/RunEnd")
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

}


