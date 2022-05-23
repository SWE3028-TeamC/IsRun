package edu.skku.cs.isrun.running.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.text.DecimalFormat
import kotlin.math.*

class RunningHomeViewModel: ViewModel() {

    private var mText: MutableLiveData<String> = MutableLiveData()
    var freeMode:MutableLiveData<Boolean> = MutableLiveData()
    var recommendMode:MutableLiveData<Boolean> =  MutableLiveData()
    var timeSet:MutableLiveData<Double> = MutableLiveData()
    var distanceSet:MutableLiveData<Double> = MutableLiveData()
    var time:MutableLiveData<Long> = MutableLiveData()
    var distance:MutableLiveData<Double> = MutableLiveData()

    fun RunningHomeViewModel() {
        mText.value = "This is home fragment"
        freeMode.value = false
        recommendMode.value = true
        timeSet.value = 30.0
        distanceSet.value = 4.0
        time.value = 0
        distance.value = 0.0
    }

    // average jogging : 8km/h | running 12km/h
    fun recommendationRun(time_check:Boolean) {
        if(time_check){
            this.distanceSet.value = 8.0/60.0* this.timeSet.value!!
        }else{
            this.timeSet.value = this.distanceSet.value!!*60.0/8.0
        }
    }

    fun getTimeProgress(): Int{
        return ((this.timeSet.value!!-5.0)*100.0/175.0).toInt()
    }

    fun getDistanceProgress(): Int{
        return ((this.distanceSet.value!!-1.0)*100.0/41.7).toInt()
    }

    fun getTime(timeProgress:Int){
        this.timeSet.value = timeProgress*175/100 + 5.0
        if(this.recommendMode.value!!){
            recommendationRun(true)
        }
    }

    fun getDistance(distanceProgress:Int){
        this.distanceSet.value = distanceProgress*41.7/100 + 1.0
        if(this.recommendMode.value!!){
            recommendationRun(false)
        }
    }

    fun getGPSDistance(
        lastLatitude: Double,
        lastLongitude: Double,
        currentLatitude: Double,
        currentLongitude: Double
    ): Double {
        val dLat = Math.toRadians(currentLatitude - lastLatitude)
        val dLon = Math.toRadians(currentLongitude - lastLongitude)
        val a = sin(dLat/2).pow(2.0) + sin(dLon/2).pow(2.0)*cos(Math.toRadians(lastLatitude))*cos(Math.toRadians(currentLatitude))
        val c = 2*asin(sqrt(a))
        val moveDistance = 6371.8 * c * 1000
        distance.value = distance.value?.plus(moveDistance)

        return moveDistance
    }

    fun toStringtimeSet(): String {
        val time = floor(this.timeSet.value!!).toInt()
        val hour = time/60
        val minutes = time%60
        val seconds = floor((this.timeSet.value!! - time)*60).toInt()
        return "${hour}h ${minutes}m ${seconds}s"
    }

    fun toStringtime(): String {
        val time = this.time.value
        var hour = 0
        var minutes = 0
        var seconds = 0
        if(time != null){
            hour = (time / 3600000).toInt()
            minutes = (time - hour * 3600000).toInt() / 60000
            seconds = (time - hour * 3600000 - minutes * 60000).toInt() / 1000
        }
        return "${hour}h ${minutes}m ${seconds}s"
    }

    fun toStringdistanceSet():String{
        return "${DecimalFormat("####.00").format(this.distanceSet.value)} km"
    }

    fun toStringDistance():String{
        val distance = this.distance.value!!
        if(distance.equals(0.0)){
            return "0m"
        }
        val km = floor(distance/1000).toInt()
        val m = distance - km*1000
        val kmSting = if(km == 0) "" else "${km}km "
        val mString = if(m.equals(0.0)) "" else " ${DecimalFormat("##0.00").format(m)}m"

        return "$kmSting$mString"
    }

    fun getPace():String{
        val distance = this.distance.value!!
        if(distance.equals(0.0)){
            return "0s/km"
        }
        val time = this.time.value!!.toDouble() / (distance/1000)
        val paceh: Int = (time / 3600000.0).toInt()
        val pacem: Int = (time - paceh * 3600000.0).toInt() / 60000
        val paces: Int = (time - paceh * 3600000 - pacem * 60000).toInt() / 1000
        val pacehString = if(paceh == 0) "" else "${paceh}h"
        val pacemString = if(pacem == 0) "" else " ${pacem}m"
        val pacesString = if(paces == 0) "" else " ${paces}s"

        return "$pacehString$pacemString$pacesString/km"
    }

    fun getPercent():Double{
        val distance = this.distance.value!!
        if(distance.equals(0.0)){
            return 0.00
        }
        var percent = distance/ (distanceSet.value!!*1000)

        return  percent*100
    }

}