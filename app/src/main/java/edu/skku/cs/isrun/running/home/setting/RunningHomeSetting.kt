package edu.skku.cs.isrun.running.home.setting

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation
import edu.skku.cs.isrun.R

class RunningHomeSetting : Fragment() {

    companion object {
        fun newInstance() = RunningHomeSetting()
    }

    private lateinit var viewModel: RunningHomeSettingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.running_home_setting_fragment, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[RunningHomeSettingViewModel::class.java]
        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setStartButton(view)
    }

    // function for setting run button to next navigation
    private fun setStartButton(view: View) {
        val navController = Navigation.findNavController(view)
        view.findViewById<Button>(R.id.startBtn).setOnClickListener {
            navController.navigate(R.id.action_running_home_setting_to_running_home_running)
        }
    }

}