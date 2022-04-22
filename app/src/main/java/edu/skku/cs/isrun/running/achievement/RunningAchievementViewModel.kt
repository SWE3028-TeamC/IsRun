package edu.skku.cs.isrun.running.achievement

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RunningAchievementViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    private var mText: MutableLiveData<String>? = null
    private var recordAdapter: Achievement = Achievement(false, "title", "percent")
    private var recordList = mutableListOf<Achievement>()

    // Sample List
    private var sampleList = mutableListOf<Achievement>(Achievement(false, "100M!","50"),
        Achievement(true, "Your very first Run!", "98"),
        Achievement(false, "Too fast, Too Strong, Too good!", "13"))

    fun RunningAchievementViewModel() {
        mText = MutableLiveData()
        mText!!.setValue("This is home fragment")
    }

    fun getText(): MutableList<Achievement> {
        return sampleList
    }

}