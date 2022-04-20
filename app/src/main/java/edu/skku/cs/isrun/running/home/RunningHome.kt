package edu.skku.cs.isrun.running.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

import edu.skku.cs.isrun.databinding.RunningHomeFragmentBinding

class RunningHome: Fragment() {

    private var binding: RunningHomeFragmentBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val homeViewModel: RunningHomeViewModel = ViewModelProvider(this).get(RunningHomeViewModel::class.java)
        binding = RunningHomeFragmentBinding.inflate(inflater, container, false)
        val root: View = binding!!.root

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}