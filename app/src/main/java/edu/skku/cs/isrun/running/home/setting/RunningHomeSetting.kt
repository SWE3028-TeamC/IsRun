package edu.skku.cs.isrun.running.home.setting

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        viewModel = ViewModelProvider(this).get(RunningHomeSettingViewModel::class.java)
        // TODO: Use the ViewModel
    }

}