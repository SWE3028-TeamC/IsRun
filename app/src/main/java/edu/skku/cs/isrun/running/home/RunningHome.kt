package edu.skku.cs.isrun.running.home

import android.content.Context
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.kakao.util.maps.helper.Utility
import edu.skku.cs.isrun.R
import edu.skku.cs.isrun.databinding.RunningHomeFragmentBinding
import net.daum.mf.map.api.MapView


class RunningHome: Fragment() {

    private var binding: RunningHomeFragmentBinding? = null
    private var ACCESS_FINE_LOCATION = 1000

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = RunningHomeFragmentBinding.inflate(inflater, container, false)
        setMap()

        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRunButton(view)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    // function for setting run button to next navigation
    private fun setRunButton(view: View) {
        val navController = Navigation.findNavController(view)
        binding?.run?.setOnClickListener {
            navController.navigate(R.id.action_running_home_to_running_home_setting)
        }
    }

    // function for KAKAO MAP
    private fun setMap(){

        var keyHash = Utility.getKeyHash(this.context)
        Log.e("Hash key kakao", keyHash)

        val mapView = MapView(this.activity)
//        Log.e("mapView", mapView.)
        binding?.mapView?.addView(mapView)
        // tracking on
        mapView.currentLocationTrackingMode = MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading
    }

    // request location permission
    private fun permissionCheck() {

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    // check GPS
    private fun checkLocationService(): Boolean{
        val locationManger = context?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManger.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }


}


