package edu.skku.cs.isrun.running.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RunningHomeViewModel: ViewModel() {

    private var mText: MutableLiveData<String>? = null

    fun RunningHomeViewModel() {
        mText = MutableLiveData()
        mText!!.setValue("This is home fragment")
    }

    fun getText(): LiveData<String>? {
        return mText
    }
}