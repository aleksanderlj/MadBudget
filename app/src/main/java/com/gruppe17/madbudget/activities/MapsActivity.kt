package com.gruppe17.madbudget.activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.*

import com.google.android.gms.maps.model.*
import com.google.android.material.slider.Slider
import com.gruppe17.madbudget.R
import com.gruppe17.madbudget.Utility
import com.gruppe17.madbudget.database.AppDatabase
import com.gruppe17.madbudget.database.DatabaseBuilder
import com.gruppe17.madbudget.models.Store
import kotlinx.android.synthetic.main.activity_maps.*
import kotlinx.android.synthetic.main.activity_maps.navigation
import kotlinx.android.synthetic.main.activity_recipes.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.collections.ArrayList

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnMyLocationClickListener{

    private lateinit var mMap: GoogleMap
    private lateinit var innerCircle: Circle
    private lateinit var lastLocation: Location
    private lateinit var markers: ArrayList<Marker>
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var selectedStores: ArrayList<Store>
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        db = DatabaseBuilder.get(this)

        initNavigationMenu()

        GlobalScope.launch {
            db.storeDAO().deleteAllExisting()
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        markers = ArrayList()
        selectedStores = ArrayList()

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
                val tempStore:Store = i.tag as Store
                if (tempStore.distance!!.toDouble() <= slider.value){
                    i.alpha = 1f
                    tempStore.isSelected = true
                    i.tag = tempStore
                }
                if (tempStore.distance!!.toDouble() > slider.value){
                    i.alpha = 0.2f
                    tempStore.isSelected = false
                    i.tag = tempStore
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

        for( i in Utility.getTestCoopStoreList().Data){
            val storeName: Int = setStoreIcon(i.R)
            val marker: Marker = mMap.addMarker(
                MarkerOptions()
                    .title(i.R)
                    .position(LatLng(i.L.coordinates[1],i.L.coordinates[0]))
                    .alpha(0.2f)
                    .icon(BitmapDescriptorFactory.fromResource(storeName))
            )

            fusedLocationClient.lastLocation.addOnSuccessListener {
                if (it != null){
                    lastLocation = it
                    val results: FloatArray = FloatArray(3)
                    Location.distanceBetween(lastLocation.latitude,lastLocation.longitude,i.L.coordinates[1],i.L.coordinates[0], results)
                    marker.tag = Store(0,i.N,i.K,i.A,i.S,false ,results[0])
                    markers.add(marker)
                }
            }

        }

        for (i in markers) {
            val tempStore: Store = i.tag as Store
            if (tempStore.distance!!.toDouble() <= radius_slider_bar.value) {
                i.alpha = 1f
                i.isVisible = true
                tempStore.isSelected = true
                i.tag = tempStore
            }
        }
    }

    private fun setStoreIcon(storeName: String): Int {
        return when (storeName) {
            "SuperBrugsen" -> R.drawable.brugsen
            "Dagli'Brugsen" -> R.drawable.daglibrugsen
            "Irma" -> R.drawable.irma
            "Fakta" -> R.drawable.fakta
            "Kvickly" -> R.drawable.kvickly
            "COOP MAD" -> R.drawable.coopmad
            else -> R.drawable.ghetto
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

    override fun onDestroy() {
        super.onDestroy()

        for (i in markers) {
            val tempStore = i.tag as Store
            if (tempStore.isSelected)
                selectedStores.add(tempStore)
        }

        GlobalScope.launch {
            db.storeDAO().insertAll(selectedStores)
            selectedStores.clear()
        }
    }

    private fun initNavigationMenu(){
        navigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.page_1 ->{
                    val recipesActivity = Intent(this, RecipeActivity::class.java)
                    startActivity(recipesActivity);
                    true
                }
                R.id.page_2 ->{
                    true
                }
                R.id.page_3 -> {
                    Toast.makeText(this,"Ikke implementeret", Toast.LENGTH_LONG).show()
                    true
                }
                R.id.page_4 -> {
                    Toast.makeText(this,"Ikke implementeret", Toast.LENGTH_LONG).show()
                    true
                }
                else -> false
            }
        }
    }

}