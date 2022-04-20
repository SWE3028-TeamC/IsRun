package edu.skku.cs.isrun.running.record

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RunningRecordViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    private var mText: MutableLiveData<String>? = null

    fun RunningAchievementViewModel() {
        mText = MutableLiveData()
        mText!!.setValue("This is home fragment")
    }

    fun getText(): LiveData<String>? {
        return mText
    }
}