package edu.skku.cs.isrun.running.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

import edu.skku.cs.isrun.databinding.RunningHomeBinding

class RunningHome: Fragment() {

    private var binding: RunningHomeBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val homeViewModel: RunningHomeViewModel = ViewModelProvider(this).get(RunningHomeViewModel::class.java)
        binding = RunningHomeBinding.inflate(inflater, container, false)
        val root: View = binding!!.getRoot()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}