package com.example.madbudget.salling

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.android.volley.Response
import com.google.android.gms.location.*
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.Task
import org.json.JSONObject

class SallingCommunicator {

    companion object {
        var filters = "country=dk&fields=address,brand,coordinates,distance_km,name,id"
        //filter: zip, city, country ("dk", "se", "de", "pl"), street, brand ("netto", "bilka", "foetex", "salling", "carlsjr", "br")
        //fields: address, brand, coordinates, created, distance_km, hours, modified, name, phoneNumber, sapSiteId, type, vikingStoreId, attributes, id

        fun getAllNearbyStores(context: Context, radius: Int, callback: Response.Listener<String>) {
            var fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
            var locationCallback = LocationCallback()

            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(context as AppCompatActivity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
                return
            }

            // TODO move location updates to the current activity instead of recreating on each function call.
            fusedLocationClient.requestLocationUpdates(
                createLocationRequest(context),
                locationCallback,
                Looper.getMainLooper()
            )

            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                Log.i("Location Info", "/v2/stores/?"+ filters + "&geo=${location?.latitude},${location?.longitude}&radius=$radius")
                VolleyGetter.send(context, "/v2/stores/?geo=${location?.latitude},${location?.longitude}&radius=$radius", callback)
            }

        }

        fun createLocationRequest(context: Context): LocationRequest? {
            val locationRequest = LocationRequest.create().apply {
                interval = 10000
                priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            }

            val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
            val client: SettingsClient = LocationServices.getSettingsClient(context)
            val task: Task<LocationSettingsResponse> = client.checkLocationSettings(builder.build())

            return locationRequest
        }





    }
}