package edu.skku.cs.isrun.running.home.result

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.kakao.util.maps.helper.Utility
import edu.skku.cs.isrun.R
import edu.skku.cs.isrun.RunResult
import edu.skku.cs.isrun.databinding.RunningHomeResultFragmentBinding
import edu.skku.cs.isrun.running.home.RunningHomeViewModel
import net.daum.mf.map.api.*
import java.text.DecimalFormat
import java.util.*


class RunningHomeResult : Fragment() {

    companion object {
        fun newInstance() = RunningHomeResult()
    }

    private lateinit var ch: ImageView
    var app_character_list =
        arrayOf("cat", "dog", "hamster", "parrot", "bunny", "lion", "seal", "shiba")
    private lateinit var mapView: MapView
    private var binding: RunningHomeResultFragmentBinding? = null
    private lateinit var gold: TextView
    private val viewModel: RunningHomeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = RunningHomeResultFragmentBinding.inflate(inflater, container, false)
        setrunMap()
        return binding!!.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRunButton(view)

        val distance = view.findViewById<TextView>(R.id.distanceResult)
        val time = view.findViewById<TextView>(R.id.timeResult)
        val pace = view.findViewById<TextView>(R.id.paceResult)
        val goal_title = view.findViewById<TextView>(R.id.goal_title)
        val percent = view.findViewById<TextView>(R.id.percent)
        val progressBar = view.findViewById<ProgressBar>(R.id.runProgress)
        val ch = view.findViewById<ImageView>(R.id.petImage)
        gold = view.findViewById<TextView>(R.id.Gold)
        val food = view.findViewById<TextView>(R.id.petFood)
        val love = view.findViewById<TextView>(R.id.petLove)
        val newAchieve = view.findViewById<TextView>(R.id.newAchieve)

        val runResultObserver = Observer<RunResult> { runRusult ->
            gold.text = "Gold : ${runRusult.gold}"
            food.text = "Food : ${runRusult.food}"
            love.text = "Love : ${runRusult.love}"
            newAchieve.text = "None"
            if(runRusult.newAchivs != null){
                var temp =""
                for( i in 0 until runRusult.newAchivs.size){
                    temp += (runRusult.newAchivs[i]+"\n")
                }
                if(temp != ""){
                    newAchieve.text = temp
                }
            }
        }
        viewModel.runResult.observe(viewLifecycleOwner, runResultObserver)


        distance.text = "Distance : ${viewModel.toStringDistance()}"
        time.text = "Time : ${viewModel.toStringtime()}"
        pace.text = "Pace : ${viewModel.getPace()}"
        goal_title.text = "Goal : ${viewModel.toStringdistanceSet()}"
        percent.text = "${DecimalFormat("##0.00").format(viewModel.getPercent())}%"
        progressBar.progress = 100 - viewModel.getPercent().toInt()

        // show image of main character
        val chchange = app_character_list[viewModel.userData.value?.mcharidx!!]
        if (!((chchange == "NONE")))
        {
            if (chchange == "hamster") {
                try {
                    Glide.with(this).load(R.raw.hamster).into(ch)
                } catch (e: java.lang.Exception) {
                    e.printStackTrace()
                }
            } else if (chchange == "dog") {
                try {
                    Glide.with(this).load(R.raw.dog).into(ch)
                } catch (e: java.lang.Exception) {
                    e.printStackTrace()
                }
            } else if (chchange == "cat") {
                try {
                    Glide.with(this).load(R.raw.cat).into(ch)
                } catch (e: java.lang.Exception) {
                    e.printStackTrace()
                }
            } else if (chchange == "parrot") {
                try {
                    Glide.with(this).load(R.raw.parrot).into(ch)
                } catch (e: java.lang.Exception) {
                    e.printStackTrace()
                }
            } else if (chchange == "bunny") {
                try {
                    Glide.with(this).load(R.raw.bunny).into(ch)
                } catch (e: java.lang.Exception) {
                    e.printStackTrace()
                }
            } else if (chchange == "lion") {
                try {
                    Glide.with(this).load(R.raw.lion).into(ch)
                } catch (e: java.lang.Exception) {
                    e.printStackTrace()
                }
            } else if (chchange == "seal") {
                try {
                    Glide.with(this).load(R.raw.seal).into(ch)
                } catch (e: java.lang.Exception) {
                    e.printStackTrace()
                }
            } else if (chchange == "shiba") {
                try {
                    Glide.with(this).load(R.raw.shiba).into(ch)
                } catch (e: java.lang.Exception) {
                    e.printStackTrace()
                }
            }
        }

    }

    // function for setting run button to next navigation
    private fun setRunButton(view: View) {
        val navController = Navigation.findNavController(view)
        view.findViewById<Button>(R.id.returnBtn).setOnClickListener {
            activity?.runOnUiThread(Runnable {
                binding?.runMap?.removeView(mapView)
                viewModel.RunningHomeViewModel()
                navController.navigate(R.id.action_running_home_result_to_running_home)
            })
        }
    }

    // function for KAKAO MAP
    private fun setrunMap(){

        val keyHash = Utility.getKeyHash(this.context)
        Log.e("Hash key kakao", keyHash)

        mapView = MapView(this.activity)
        binding?.runMap?.addView(mapView)
        // Todo show run on map -> viewModel.___
        Log.e("GPS", ""+viewModel.gpsProgress.value?.size)
        val polyline = MapPolyline()
        polyline.tag = 1000
        polyline.lineColor = Color.argb(128, 255, 51, 0)

        if(viewModel.gpsProgress.value != null){
            for(gps in viewModel.gpsProgress.value!!){
                polyline.addPoint(MapPoint.mapPointWithGeoCoord(gps.Lat,gps.Lon))
            }
        }

        mapView.addPolyline(polyline)
        val mapPointBounds = MapPointBounds(polyline.mapPoints)
        val padding = 20 // px
        mapView.moveCamera(CameraUpdateFactory.newMapPointBounds(mapPointBounds, padding))

    }

}