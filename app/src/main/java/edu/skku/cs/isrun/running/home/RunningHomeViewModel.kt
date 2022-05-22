package edu.skku.cs.isrun.running.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.math.round

class RunningHomeViewModel: ViewModel() {

    // ToDo: Learn about LiveData
    private var mText: MutableLiveData<String> = MutableLiveData()
    var freeMode:MutableLiveData<Boolean> = MutableLiveData()
    var recommendMode:MutableLiveData<Boolean> =  MutableLiveData()
    var timeSet:MutableLiveData<Double> = MutableLiveData()
    var distanceSet:MutableLiveData<Double> = MutableLiveData()
    var time:MutableLiveData<Double> = MutableLiveData()
    var distance:MutableLiveData<Double> = MutableLiveData()

    fun RunningHomeViewModel() {
        mText.value = "This is home fragment"
        freeMode.value = false
        recommendMode.value = true
        timeSet.value = 30.0
        distanceSet.value = 4.0
        time.value = 0.0
        distance.value = 0.0

    }

    // average jogging : 8km/h | running 12km/h
    fun recommendationRun(time_check:Boolean) {
        if(time_check){
            this.distanceSet.value = round(800.0/60.0* this.timeSet.value!!) /100
        }else{
            this.timeSet.value = round(this.distanceSet.value!!*6000.0/8.0) /100
        }
    }

    fun getTimeProgress(): Int{
        return ((this.timeSet.value!!-5.0)*100.0/175.0).toInt()
    }

    fun getDistanceProgress(): Int{
        return ((this.distanceSet.value!!-1.0)*100.0/41.7).toInt()
    }

    fun getTime(timeProgress:Int){
        this.timeSet.value = round((timeProgress*175/100 + 5.0)*100) /100
        if(this.recommendMode.value!!){
            recommendationRun(true)
        }
    }

    fun getDistance(distanceProgress:Int){
        this.distanceSet.value = round((distanceProgress*41.7/100 + 1.0)*10) /10
        if(this.recommendMode.value!!){
            recommendationRun(false)
        }
    }

    fun toStringtime(): String {
        val time = round(this.timeSet.value!!).toInt()
        val hour = time/60
        val minute = time%60
        return "${hour}h ${minute}m"
    }

    fun toStringdistance():String{
        return "${this.distanceSet.value} km"
    }

}