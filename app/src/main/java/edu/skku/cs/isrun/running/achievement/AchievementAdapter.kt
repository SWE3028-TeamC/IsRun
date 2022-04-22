package edu.skku.cs.isrun.running.achievement

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckBox
import android.widget.TextView
import androidx.core.content.ContextCompat
import edu.skku.cs.isrun.R

class Achievement(var done: Boolean, var title: String, var percent: String)

class AchievementAdapter(private var Records: MutableList<Achievement>, private var mContext: Context) : BaseAdapter() {

    override fun getCount(): Int {
        return Records.size
    }

    override fun getItem(i: Int): Any {
        return Records.get(i)
    }

    override fun getItemId(i: Int): Long {
        return i.toLong()
    }

    @SuppressLint("SetTextI18n")
    override fun getView(i: Int, view: View?, viewGroup: ViewGroup?): View? {
        var viewtemp = view
        if (viewtemp == null) {
            val inflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            viewtemp = inflater.inflate(R.layout.item_achievement, viewGroup, false)
        }

        val checkBox: CheckBox =  viewtemp!!.findViewById(R.id.done)
        val textViews = java.util.ArrayList<TextView>()
        textViews.add(viewtemp.findViewById(R.id.achievement_title))
        textViews.add(viewtemp.findViewById(R.id.achieve_percent))

        val white = ContextCompat.getColor(mContext, R.color.white)
        val purple = ContextCompat.getColor(mContext, R.color.purple_500)

        val temp: Achievement = Records[i]

        checkBox.isChecked = temp.done
        textViews[0].text = temp.title
        textViews[1].text = temp.percent+"%"

        return viewtemp
    }


}