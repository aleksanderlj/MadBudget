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

        fun getNearbyStores(context: Context, radius: Int, callback: Response.Listener<String>) {

            var fusedLocationClient = startLocationUpdates(context)

            if (fusedLocationClient != null) {
                try {
                    fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                        VolleyGetter.send(
                            context,
                            "/v2/stores/?geo=${location?.latitude},${location?.longitude}&radius=$radius&$filters",
                            callback
                        )
                    }
                } catch (e: SecurityException) {
                    e.printStackTrace()
                }
            }
        }

        //TODO Husk at stoppe dem igen, ellers bruger det virkelig meget strøm
        fun startLocationUpdates(context: Context): FusedLocationProviderClient? {
            var fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
            var locationCallback = LocationCallback()

            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    context as AppCompatActivity,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    1
                )
                return null
            }

            fusedLocationClient.requestLocationUpdates(
                createLocationRequest(context),
                locationCallback,
                Looper.getMainLooper()
            )

            return fusedLocationClient
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

        fun getProductSuggestions(
            context: Context,
            product: String,
            callback: Response.Listener<String>
        ) {
            VolleyGetter.send(
                context,
                "/v1-beta/product-suggestions/relevant-products?query=$product",
                callback
            )
        }

        fun getSimilarProducts(
            context: Context,
            productId: String,
            callback: Response.Listener<String>
        ) {
            VolleyGetter.send(
                context,
                "/v1-beta/product-suggestions/similar-products?productId=$productId",
                callback
            )
        }

        fun getNearbyDiscounts(context: Context, radius: Int, callback: Response.Listener<String>) {
            var fusedLocationClient = startLocationUpdates(context)

            if (fusedLocationClient != null) {
                try {
                    fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                        VolleyGetter.send(
                            context,
                            "/v1/food-waste/?geo=${location?.latitude},${location?.longitude}&radius=$radius",
                            callback
                        )
                    }
                } catch (e: SecurityException) {
                    e.printStackTrace()
                }
            }
        }


    }
}