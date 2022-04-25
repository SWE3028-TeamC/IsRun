package edu.skku.cs.isrun.running.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import edu.skku.cs.isrun.R

import edu.skku.cs.isrun.databinding.RunningHomeFragmentBinding

class RunningHome: Fragment() {

    private var binding: RunningHomeFragmentBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val homeViewModel: RunningHomeViewModel =
            ViewModelProvider(this)[RunningHomeViewModel::class.java]
        binding = RunningHomeFragmentBinding.inflate(inflater, container, false)

        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRunButton()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    // function for setting run button to next navigation
    private fun setRunButton() {
        println("in setRunButton")
        binding?.run?.setOnClickListener {
            Navigation.createNavigateOnClickListener(R.id.running_home_setting, null)
        }
    }
}