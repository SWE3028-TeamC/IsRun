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
        binding = RunningLandmarkFragmentBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val near = binding?.nearSpot
        val new = binding?.newSpot
        val popular = binding?.popularSpot

        near?.setOnClickListener {
            near.setBackgroundResource(R.color.white)
            new?.setBackgroundResource(R.color.purple_500)
            popular?.setBackgroundResource(R.color.purple_500)
            // set near adapter
        }
        new?.setOnClickListener {
            near?.setBackgroundResource(R.color.purple_500)
            new.setBackgroundResource(R.color.white)
            popular?.setBackgroundResource(R.color.purple_500)
            // set new adapter
        }

        popular?.setOnClickListener {
            near?.setBackgroundResource(R.color.purple_500)
            new?.setBackgroundResource(R.color.purple_500)
            popular.setBackgroundResource(R.color.white)
            // set popular adapter
        }

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