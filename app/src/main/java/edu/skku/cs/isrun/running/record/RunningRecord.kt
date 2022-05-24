package edu.skku.cs.isrun.running.record

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import edu.skku.cs.isrun.R
import edu.skku.cs.isrun.databinding.RunningRecordFragmentBinding

class RunningRecord : Fragment() {

    companion object {
        fun newInstance() = RunningRecord()
    }

    private var binding: RunningRecordFragmentBinding? = null
    private lateinit var recordviewModel: RunningRecordViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        recordviewModel = ViewModelProvider(this).get(RunningRecordViewModel::class.java)
        binding = RunningRecordFragmentBinding.inflate(inflater, container, false)

        val recordList = binding?.root?.findViewById<ListView>(R.id.record_list)
        val recordAdapter = RecordAdapter(recordviewModel.getList(), this.requireContext())
        if(recordList != null){
            recordList.adapter = recordAdapter
        }

        return binding!!.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}