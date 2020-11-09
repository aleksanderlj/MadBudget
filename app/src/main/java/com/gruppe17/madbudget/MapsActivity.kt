package com.gruppe17.madbudget

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import com.beust.klaxon.Klaxon
import com.gruppe17.madbudget.salling.SallingCommunicator
import com.gruppe17.madbudget.salling.jsonModels.JsonStore
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.material.slider.Slider
import kotlinx.android.synthetic.main.activity_maps.*
import java.util.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnMyLocationClickListener {

    private lateinit var mMap: GoogleMap
    private lateinit var innerCircle: Circle
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var lastLocation: Location
    private lateinit var markers: ArrayList<Marker>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        markers = ArrayList()

        radius_slider_bar.addOnSliderTouchListener(object : Slider.OnSliderTouchListener {
            override fun onStartTrackingTouch(slider: Slider) {}

            override fun onStopTrackingTouch(slider: Slider) {

            }
        })

        radius_slider_bar.addOnChangeListener { slider, value, fromUser ->
            innerCircle.radius = value.toDouble()

            for (i in markers){
                if (i.tag.toString().toDouble() <= slider.value / 1000){
                    i.alpha = 1f
                    i.isVisible = true
                }
                if (i.tag.toString().toDouble() > slider.value / 1000){
                    i.alpha = 0.2f
                }
            }
        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Permission check
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }

        mMap.isMyLocationEnabled = true
        mMap.setOnMyLocationButtonClickListener(this)
        mMap.setOnMyLocationClickListener(this)

        onMyLocationButtonClick()

        innerCircle = mMap.addCircle(CircleOptions().center(LatLng(0.0,0.0)).radius(0.0))

        SallingCommunicator.getNearbyStores(applicationContext, 4.toDouble()) { response ->
           val json = Klaxon().parseArray<JsonStore>(response.toString())!!
            for (i in json.indices) {
                val marker = mMap.addMarker(
                    MarkerOptions()
                        .title(json[i].brand,)
                        .position(LatLng(json[i].coordinates[1], json[i].coordinates[0]))
                        .alpha(0.2f)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ghetto)))
                marker.tag = json[i].distance_km
                markers.add(marker)
            }
        }
    }

    override fun onMyLocationButtonClick(): Boolean {

        // Permission check
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return true
        }
         fusedLocationClient.lastLocation.addOnSuccessListener(this) {
            if (it != null) {
                lastLocation = it
                val currentLatLng = LatLng(it.latitude, it.longitude)
                innerCircle.center = currentLatLng
            }

        }
        return false
    }

    override fun onMyLocationClick(p0: Location) {
    }





}