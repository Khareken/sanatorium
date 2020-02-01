package uz.micro.star.appshipox.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_map.*
import uz.micro.star.sihatgoh.R
import uz.xsoft.lesson4ict.fragments.BaseFragment
import uz.xsoft.lesson4ict.room.AppDatabase

class MapFragment : BaseFragment(R.layout.fragment_map), OnMapReadyCallback {
    var LOCATION_CODE = 107
    private lateinit var mMap: GoogleMap
    override fun onViewCreate() {

//        val mapFragment = activity!!.supportFragmentManager
//            .findFragmentById(R.id.mapX) as SupportMapFragment
        var mf = childFragmentManager.findFragmentById(R.id.mapX) as SupportMapFragment
        mf.getMapAsync(this)
        myLocation.setOnClickListener {
            if (mMap != null) {
                if (enableLocation())
                    if (mMap.myLocation != null) {
                        mMap.animateCamera(
                            CameraUpdateFactory.newLatLngZoom(
                                LatLng(
                                    mMap.myLocation.latitude,
                                    mMap.myLocation.longitude
                                ), 14f
                            )
                        )
                    }
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.uiSettings.isMyLocationButtonEnabled = false
        mMap.uiSettings.isCompassEnabled = false
        mMap.uiSettings.isMapToolbarEnabled = false

        enableLocation()

        val tashkent = LatLng(41.311403, 69.27967)
//        val umid_gulshani = LatLng(41.395472, 69.472885)
//        val turon = LatLng(41.344312, 69.358189)
//        val oq_tosh = LatLng(41.645086, 69.766452)
//        val chinobot = LatLng(41.353341, 69.306375)
//
//        mMap.addMarker(MarkerOptions().position(tashkent).title("Tashkent centre"))
//        mMap.addMarker(MarkerOptions().position(umid_gulshani).title("Umid Gulshani"))
//        mMap.addMarker(MarkerOptions().position(turon).title("Turon"))
//        mMap.addMarker(MarkerOptions().position(oq_tosh).title("Oq tosh"))
//        mMap.addMarker(MarkerOptions().position(chinobot).title("Chinobot"))
//        Database.getDatabase().sanatoriesFullInf.forEach {
//            san->
//            mMap.addMarker(MarkerOptions().position(LatLng(san.lat,san.lon)).title(san.name))
//        }
        AppDatabase.database!!.getInputData().getSanatoryOffline().forEach { san ->
            if (san.lat != null && san.lon != null) mMap.addMarker(
                MarkerOptions().position(
                    LatLng(
                        san.lat,
                        san.lon
                    )
                ).title(san.name)
            )
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(tashkent, 12f))
    }

    fun enableLocation(): Boolean {
        ActivityCompat.requestPermissions(
            activity!!, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            LOCATION_CODE
        )
        if (ContextCompat.checkSelfPermission(context!!, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            Toast.makeText(context, "Iltimos ruhsat bering !", Toast.LENGTH_SHORT).show()
            return false
        } else {
            mMap.isMyLocationEnabled = true
            return true
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == LOCATION_CODE) {
            mMap.isMyLocationEnabled = true
        }
    }
}