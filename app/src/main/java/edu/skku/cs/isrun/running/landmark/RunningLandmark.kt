package edu.skku.cs.isrun.running.landmark

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import edu.skku.cs.isrun.R
import edu.skku.cs.isrun.databinding.RunningLandmarkFragmentBinding

class RunningLandmark : Fragment() {

    companion object {
        fun newInstance() = RunningLandmark()
    }

    private var binding: RunningLandmarkFragmentBinding? = null
    private lateinit var landmarkviewModel: RunningLandmarkViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        landmarkviewModel = ViewModelProvider(this).get(RunningLandmarkViewModel::class.java)
        binding = RunningLandmarkFragmentBinding.inflate(inflater, container, false)
        return binding!!.root
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