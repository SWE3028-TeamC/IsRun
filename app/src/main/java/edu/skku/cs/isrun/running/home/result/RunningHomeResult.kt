package edu.skku.cs.isrun.running.home.result

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.viewModelScope
import androidx.navigation.Navigation
import edu.skku.cs.isrun.R
import edu.skku.cs.isrun.running.home.RunningHomeViewModel
import org.w3c.dom.Text
import java.text.DecimalFormat

class RunningHomeResult : Fragment() {

    companion object {
        fun newInstance() = RunningHomeResult()
    }

    private val viewModel: RunningHomeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.running_home_result_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
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

        distance.text = "Distance : ${viewModel.toStringDistance()}"
        time.text = "Time : ${viewModel.toStringtime()}"
        pace.text = "Pace : ${viewModel.getPace()}"
        goal_title.text = "Goal : ${viewModel.toStringdistanceSet()}"
        percent.text = "${DecimalFormat("##0.00").format(viewModel.getPercent())}%"
        progressBar.progress = 100 - viewModel.getPercent().toInt()

    }

    // function for setting run button to next navigation
    private fun setRunButton(view: View) {
        val navController = Navigation.findNavController(view)
        view.findViewById<Button>(R.id.returnBtn).setOnClickListener {
            viewModel.RunningHomeViewModel()
            navController.navigate(R.id.action_running_home_result_to_running_home)
        }
    }

}