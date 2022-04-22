package edu.skku.cs.isrun.running.achievement

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
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
    ): View {
        achievementviewModel = ViewModelProvider(this).get(RunningAchievementViewModel::class.java)
        binding = RunningAchievementFragmentBinding.inflate(inflater, container, false)

        // TODO: Add to list view, Make class for achievement data
        var achievementList = binding?.root?.findViewById<ListView>(R.id.achievement_list)
        var achievementAdapter = AchievementAdapter(achievementviewModel.getText(), this.requireContext())
        if (achievementList != null) {
            achievementList.adapter = achievementAdapter
        }

        println(achievementviewModel.getText()[0])

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