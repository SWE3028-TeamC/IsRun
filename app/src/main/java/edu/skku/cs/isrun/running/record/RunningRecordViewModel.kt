package edu.skku.cs.isrun.running.record

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RunningRecordViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    private var mText: MutableLiveData<String>? = null
    private var recordTemp: Record = Record("date", "distance", "pace")
    private var recordList = mutableListOf<Record>()

    /////////////////////////////////////////////////////////
    // Sample List
    private var sampleList = mutableListOf<Record>(Record("2022.04.23", "30.12", "32"),
                Record("2022.04.21", "10.32", "12.45"),
                Record("2022.04.18", "15.04","9.10"))
    /////////////////////////////////////////////////////////

    fun RunningAchievementViewModel() {
        mText = MutableLiveData()
        mText!!.setValue("This is home fragment")
    }

    fun getText(): LiveData<String>? {
        return mText
    }

    fun getList(): MutableList<Record> {
        return sampleList
    }
}