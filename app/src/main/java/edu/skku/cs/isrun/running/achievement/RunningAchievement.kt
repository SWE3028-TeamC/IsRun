package edu.skku.cs.isrun.running.achievement

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import edu.skku.cs.isrun.R
import edu.skku.cs.isrun.databinding.RunningAchievementFragmentBinding

class RunningAchievement : Fragment() {

    companion object {
        fun newInstance() = RunningAchievement()
    }

    private var binding: RunningAchievementFragmentBinding? = null
    private lateinit var achievementviewModel: RunningAchievementViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        achievementviewModel = ViewModelProvider(this).get(RunningAchievementViewModel::class.java)
        binding = RunningAchievementFragmentBinding.inflate(inflater, container, false)
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