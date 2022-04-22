package edu.skku.cs.isrun.running.home.setting

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import edu.skku.cs.isrun.R

class RunningHome_seting : Fragment() {

    companion object {
        fun newInstance() = RunningHome_seting()
    }

    private lateinit var viewModel: RunningHomeSetingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.running_home_seting_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RunningHomeSetingViewModel::class.java)
        // TODO: Use the ViewModel
    }

}