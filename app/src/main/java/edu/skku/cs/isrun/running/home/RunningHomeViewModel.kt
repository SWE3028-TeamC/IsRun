package edu.skku.cs.isrun.running.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.math.round

class RunningHomeViewModel: ViewModel() {

    // ToDo: Learn about LiveData
    private var mText: MutableLiveData<String>? = null
    var freeMode = false
    var recommendMode = true
    var timeSet = 30.0
    var distanceSet = 4.0
    var time = 0.0
    var distance = 0.0

    fun RunningHomeViewModel() {
        mText = MutableLiveData()
        mText!!.value = "This is home fragment"
    }

    fun getText(): LiveData<String>? {
        return mText
    }

    // average jogging : 8km/h | running 12km/h
    fun recommendationRun(time_check:Boolean) {
        if(time_check){
            this.distanceSet = round(800.0/60.0*this.timeSet) /100
        }else{
            this.timeSet = round(this.distanceSet*6000.0/8.0) /100
        }
    }

    fun getTimeProgress(): Int{
        return ((this.timeSet-5.0)*100.0/175.0).toInt()
    }

    fun getDistanceProgress(): Int{
        return ((this.distanceSet-1.0)*100.0/41.7).toInt()
    }

    fun getTime(timeProgress:Int){
        this.timeSet = round((timeProgress*175/100 + 5.0)*100) /100
        if(this.recommendMode){
            recommendationRun(true)
        }
    }

    fun getDistance(distanceProgress:Int){
        this.distanceSet = round((distanceProgress*41.7/100 + 1.0)*10) /10
        if(this.recommendMode){
            recommendationRun(false)
        }
    }

    fun toStringtime(): String {
        val time = round(this.timeSet).toInt()
        val hour = time/60
        val minute = time%60
        return "0$hour:$minute"
    }

    fun toStringdistance():String{
        return "${this.distanceSet} km"
    }

}