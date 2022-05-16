package edu.skku.cs.isrun.running.home.setting

import androidx.lifecycle.ViewModel
import kotlin.math.round

class RunningHomeSettingViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    var freeMode = false
    var recommendMode = true
    var timeSet = 30.0
    var distanceSet = 4.0


    // average jogging : 8km/h | running 12km/h
    fun recommendationRun(time_check:Boolean) {
        if(time_check){
            this.distanceSet = round(800.0/60.0*this.timeSet)/100
        }else{
            this.timeSet = round(this.distanceSet*6000.0/8.0)/100
        }
    }

    fun getTimeProgress(): Int{
        return ((this.timeSet-10.0)*100.0/170.0).toInt()
    }

    fun getDistanceProgress(): Int{
        return ((this.distanceSet-1.0)*100.0/41.7).toInt()
    }

    fun getTime(timeProgress:Int){
        this.timeSet = round((timeProgress.toDouble()*170.0/100.0 + 10.0)*100)/100
        if(this.recommendMode){
            recommendationRun(true)
        }
    }

    fun getDistance(distanceProgress:Int){
        this.distanceSet = round((distanceProgress.toDouble()*41.7/100.0 + 1.0)*100)/100
        if(this.recommendMode){
            recommendationRun(false)
        }
    }


}