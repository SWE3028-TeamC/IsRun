package edu.skku.cs.isrun.running.home.setting

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SeekBar
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.navGraphViewModels
import edu.skku.cs.isrun.R
import edu.skku.cs.isrun.running.home.RunningHomeViewModel


class RunningHomeSetting : Fragment() {

    companion object {
        fun newInstance() = RunningHomeSetting()
    }

    private val viewModel: RunningHomeViewModel by activityViewModels()
    private var timeSet: Double = 0.0
    private var distanceSet: Double = 0.0
    private var freeMode: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.running_home_setting_fragment, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    @SuppressLint("SetTextI18n", "UseSwitchCompatOrMaterialCode")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val freeMode = view.findViewById<Switch>(R.id.free_switch)
        val recommendMode = view.findViewById<Switch>(R.id.recommend_switch)
        val timeView = view.findViewById<TextView>(R.id.timeTitle)
        val timePercent = view.findViewById<SeekBar>(R.id.seekBarTime)
        val distanceView = view.findViewById<TextView>(R.id.distanceTitle)
        val distancePercent = view.findViewById<SeekBar>(R.id.seekBarDistance)

        val freeModeObserver = Observer<Boolean> { _fm_isChecked ->
            freeMode.isChecked = _fm_isChecked
        }
        val recommendModeObserver = Observer<Boolean> { _rm_isChecked ->
            recommendMode.isChecked = _rm_isChecked
        }
        val timeSetObserver = Observer<Double> { timeSet->
            timeView.text = "Time(min) : $timeSet"
        }
        val distanceObserver = Observer<Double> { distanceSet->
            distanceView.text = "Distance(km) : $distanceSet"
        }

        viewModel.freeMode.observe(viewLifecycleOwner, freeModeObserver)
        viewModel.recommendMode.observe(viewLifecycleOwner, recommendModeObserver)
        viewModel.timeSet.observe(viewLifecycleOwner,timeSetObserver)
        viewModel.distanceSet.observe(viewLifecycleOwner,distanceObserver)

        setStartButton(view,viewModel)

        recommendMode.setOnCheckedChangeListener{ _, isChecked ->
            viewModel.recommendMode.value = isChecked
            if(viewModel.distanceSet.value!! < 24){
                viewModel.recommendationRun(false)
                timeView.text = "Time : ${viewModel.timeSet.value} min"
                timePercent.progress = viewModel.getTimeProgress()
            }else {
                viewModel.recommendationRun(true)
                distanceView.text = "Distance : ${viewModel.distanceSet.value} km"
                distancePercent.progress = viewModel.getDistanceProgress()
            }
        }
        // set text
        timeView.text = "Time : ${viewModel.timeSet.value} min"
        distanceView.text = "Distance : ${viewModel.distanceSet.value} km"
        // set progress
        timePercent.progress = viewModel.getTimeProgress()
        distancePercent.progress = viewModel.getDistanceProgress()


        timePercent.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                if(p2){
                    viewModel.getTime(p1)
                    if(viewModel.timeSet.value!! < 7.5 && viewModel.recommendMode.value == true){
                        viewModel.timeSet.value = 7.5
                        viewModel.distanceSet.value = 1.0
                        timePercent.progress = viewModel.getTimeProgress()
                    }
                    distancePercent.progress = viewModel.getDistanceProgress()
                }
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }
        })
        distancePercent.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                if(p2){
                    viewModel.getDistance(p1)
                    if(viewModel.distanceSet.value!! > 24.0 && viewModel.recommendMode.value == true) {
                        viewModel.distanceSet.value = 24.0
                        viewModel.timeSet.value = 180.0
                        distancePercent.progress = viewModel.getDistanceProgress()
                    }
                    timePercent.progress = viewModel.getTimeProgress()
                }
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }

        })

    }

    // function for setting run button to next navigation
    private fun setStartButton(view: View, viewModel: RunningHomeViewModel) {
        val navController = Navigation.findNavController(view)
        view.findViewById<Button>(R.id.startBtn).setOnClickListener {
            navController.navigate(R.id.action_running_home_setting_to_running_home_running)
        }
    }

}


