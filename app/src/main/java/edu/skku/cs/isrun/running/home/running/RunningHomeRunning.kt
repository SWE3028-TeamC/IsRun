package edu.skku.cs.isrun.running.home.running

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation
import edu.skku.cs.isrun.R

class RunningHomeRunning : Fragment() {

    companion object {
        fun newInstance() = RunningHomeRunning()
    }

    private lateinit var viewModel: RunningHomeRunningViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.running_home_running_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RunningHomeRunningViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRunButton(view)
    }

    // function for setting run button to next navigation
    private fun setRunButton(view: View) {
        val navController = Navigation.findNavController(view)
        view.findViewById<Button>(R.id.finishBtn).setOnClickListener {
            navController.navigate(R.id.action_running_home_running_to_running_home_result)
        }
    }

}