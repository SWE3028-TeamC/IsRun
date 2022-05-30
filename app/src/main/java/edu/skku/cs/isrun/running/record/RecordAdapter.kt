package edu.skku.cs.isrun.running.record

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.core.content.ContextCompat
import edu.skku.cs.isrun.R
import java.text.DecimalFormat

class RecordAdapter(private var Records: Array<Record>, private var mContext: Context): BaseAdapter() {
    override fun getCount(): Int {
        return Records.size
    }

    override fun getItem(i: Int): Any {
        return Records[i]
    }

    override fun getItemId(i: Int): Long {
        return i.toLong()
    }

    @SuppressLint("SetTextI18n")
    override fun getView(i: Int, view: View?, viewGroup: ViewGroup?): View {
        var viewtemp = view
        if (viewtemp == null) {
            val inflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            viewtemp = inflater.inflate(R.layout.item_record, viewGroup, false)
        }

        val textViews = java.util.ArrayList<TextView>()
        textViews.add(viewtemp!!.findViewById(R.id.date))
        textViews.add(viewtemp.findViewById(R.id.total_distance))
        textViews.add(viewtemp.findViewById(R.id.average_pace))

        val white = ContextCompat.getColor(mContext, R.color.white)
        val purple = ContextCompat.getColor(mContext, R.color.purple_500)

        val temp: Record = Records[i]

        textViews[0].text = temp.getDate()
        textViews[1].text = "${DecimalFormat("###0.00").format(temp.length)} m"
        textViews[2].text = "${DecimalFormat("###0.00").format(temp.getPace())} m/km"

        return viewtemp
    }

}