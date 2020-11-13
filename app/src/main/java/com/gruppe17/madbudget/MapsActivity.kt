package com.gruppe17.madbudget

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import com.beust.klaxon.Klaxon
import com.gruppe17.madbudget.salling.SallingCommunicator
import com.gruppe17.madbudget.salling.jsonModels.JsonStore
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.*

import com.google.android.gms.maps.model.*
import com.google.android.material.slider.Slider
import com.gruppe17.madbudget.coop.CoopCommunicator
import com.gruppe17.madbudget.coop.model.CoopStoreList
import kotlinx.android.synthetic.main.activity_maps.*
import java.util.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnMyLocationClickListener{

    private lateinit var mMap: GoogleMap
    private lateinit var innerCircle: Circle
    private lateinit var lastLocation: Location
    private lateinit var markers: ArrayList<Marker>
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        markers = ArrayList()

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this as AppCompatActivity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
        }

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
        
        fusedLocationClient.lastLocation.addOnSuccessListener {
            if (it != null){
                lastLocation = it
                val latLng = LatLng(lastLocation.latitude, lastLocation.longitude)
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15.0.toFloat()))
            }
        }

        val initialCircleSize = 700.00
        innerCircle = mMap.addCircle(CircleOptions().center(LatLng(0.0,0.0)).radius(initialCircleSize).strokeColor(Color.GRAY))
        radius_slider_bar.value = initialCircleSize.toFloat()

        CoopCommunicator.getNearbyStoresMapOptimized(applicationContext, 4000, 1, 1000){ response ->
            val json = Klaxon().parse<CoopStoreList>(response.toString())!!
            for (i in json.stores) {
                val marker = mMap.addMarker(
                    MarkerOptions()
                        .title(i.retailGroup)
                        .position(LatLng(i.location.coordinates[0], i.location.coordinates[1]))
                        .alpha(0.2f))
                //marker.tag
                when (i.retailGroup){
                    "fakta" -> marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.fakta))
                    "irma" -> marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.irma))
                    "superbrugsen" -> marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.super_brugsen))
                    "kvickly" -> marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.kvickly))
                    "brugsen" -> marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.brugsen))
                    "Lokalbrugsen" -> marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.brugsen))
                    "dagli'Brugsen" -> marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.dagli_brugsen))
                    "Dagli'Brugsen" -> marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.dagli_brugsen))
                }

                markers.add(marker)
            }
            /*for (i in markers) {
                if (i.tag.toString().toDouble() <= radius_slider_bar.value / 1000) {
                    i.alpha = 1f
                    i.isVisible = true
                }
            }*/
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