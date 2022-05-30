package edu.skku.cs.isrun.running.record

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.google.gson.GsonBuilder
import edu.skku.cs.isrun.databinding.RunningRecordFragmentBinding
import edu.skku.cs.isrun.running.home.RunningHomeViewModel
import org.eclipse.paho.client.mqttv3.*
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence
import java.nio.charset.StandardCharsets
import java.text.DecimalFormat

class RunningRecord : Fragment() {

    companion object {
        fun newInstance() = RunningRecord()
    }

    private var binding: RunningRecordFragmentBinding? = null
    private val viewModel: RunningHomeViewModel by activityViewModels()
    private lateinit var recordList: ListView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = RunningRecordFragmentBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recordList = binding?.recordList!!
        val totalDist = binding?.totalDistance
        val totalTime = binding?.totalTime
        val avrPace = binding?.averagePace

        val runResultObserver = Observer<Array<Record>> { runRecord ->
            var total_dist = 0.0
            var total_time = 0.0
            var avr_pace = 0.0
            for(record in viewModel.recordList.value!!){
                total_dist += record.length
                total_time += record.getTime()
            }
            if(!total_dist.equals(0.0)){
                avr_pace = total_time/total_dist*1000
            }
            totalDist?.text = DecimalFormat("#####0.000").format(total_dist/1000)
            totalTime?.text = DecimalFormat("#####0.00").format(total_time/60)
            avrPace?.text = DecimalFormat("#####0.00").format(avr_pace)

            val recordAdapter = RecordAdapter(runRecord, requireContext())
            recordList.adapter = recordAdapter
        }
        viewModel.recordList.observe(viewLifecycleOwner, runResultObserver)
        getRunRecord()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
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
                        if (arg0 == "${viewModel.uid.value}/GetUserRuns") {
//                            val start: Int = response.indexOf(":")
//                            val end: Int = response.length
//                            val responseBody = response.substring(start,end)
                            Log.e("", response)
                            val rundata_response =
                                gson.fromJson(response, RecordResponse::class.java)
                            viewModel.recordList.value = (rundata_response.UserData)
                            println("RunData")
                            Log.e("", rundata_response.UserData.size.toString())
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

    fun getRunRecord(){
        val aa = "{\"UserId\":\"${viewModel.uid.value}\"}"
        println(aa)
        mqttgoget(aa,"UserData/GetUserRuns")
    }

}