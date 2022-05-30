package edu.skku.cs.isrun.running.record

class Record(var runidx:Int, var goaltime:String, var goalleng: Double, var length: Double, var starttime:String, var endtime:String){

    fun getDate():String{
        return starttime.split(" ")[0]
    }

    fun getTime(): Double {
        val startTime = starttime.split(" ")[1]
        val starttime_coponents = startTime.split(":")
        val endTime = endtime.split(" ")[1]
        val endtime_components = endTime.split(":")
        val startHour = starttime_coponents[0].toDouble()
        val startMin = starttime_coponents[1].toDouble()
        val endHour = endtime_components[0].toDouble()
        val endMin = endtime_components[1].toDouble()
        if(endHour >= startHour){
            return (endHour - startHour)*60 + (endMin - startMin)
        }else{
            return (endHour + 24-startHour)*60 + (endMin - startMin)
        }

    }

    fun getPace(): Double {
        return if(length.equals(0.0)){
            0.0
        }else{
            getTime()/length*1000
        }
    }

}