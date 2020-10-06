package com.example.madbudget.salling

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task

class SallingCommunicator {


    companion object {
        fun getAllNearbyStores(context: Context, radius: Int) {
            var fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(context as AppCompatActivity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
                return
            }

            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                Log.i("Location Info", "/v2/stores/?geo=${location?.latitude},${location?.longitude}&radius=$radius")
                VolleyGetter.send(context, "/v2/stores/?geo=${location?.latitude},${location?.longitude}&radius=$radius")
            }

            fusedLocationClient.requestLocationUpdates()
        }

        fun createLocationRequest(context: Context) {
            val locationRequest = LocationRequest.create().apply {
                interval = 10000
                priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            }

            val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
            val client: SettingsClient = LocationServices.getSettingsClient(context)
            val task: Task<LocationSettingsResponse> = client.checkLocationSettings(builder.build())


        }



    }
}