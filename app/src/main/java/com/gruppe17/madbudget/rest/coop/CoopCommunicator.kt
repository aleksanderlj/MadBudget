package com.gruppe17.madbudget.rest.coop

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.android.volley.Response
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task
import java.lang.Exception

class CoopCommunicator {

    companion object {

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

        fun getAssortment(context: Context, storeId: String, callback: Response.Listener<String>) {
            send(context, "/assortmentapi/v1/product/$storeId", callback)
        }

        fun getProducts(context: Context, storeId: String, callback: Response.Listener<String>) {
            send(context, "/productapi/v1/product/$storeId", callback)
        }

        fun getStoreMapOptimized(context: Context, storeId: String, callback: Response.Listener<String>) {
            send(context, "/storeapi/v1/stores/shopformap/$storeId", callback)
        }

        fun getStoreByBrandMapOptimized(context: Context, brand: String, page: Int, pageSize: Int, callback: Response.Listener<String>) {
            send(context, "/storeapi/v1/stores/shopformap/shopsByRetailGroup/$brand?page=$page&size=$pageSize", callback)
        }

        fun getNearbyStoresMapOptimized(context: Context, radius: Int, page: Int, pageSize: Int,  callback: Response.Listener<String>){
            var fusedLocationClient = CoopCommunicator.startLocationUpdates(context)

            if (fusedLocationClient != null) {
                try {
                    fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                        send(
                            context,
                            "/storeapi/v1/stores/shopformap/find/radius/$radius?latitude=${location?.latitude}&longitude=${location?.longitude}&page=$page&size=$pageSize",
                            callback
                        )
                    }
                } catch (e: SecurityException) {
                    e.printStackTrace()
                }
            }
        }

        fun getAllStoresMapOptimized(context: Context, page: Int, pageSize: Int, callback: Response.Listener<String>){
            send(context, "/storeapi/v1/stores/shopformap?page=$page&size=$pageSize", callback)
        }

        private fun send(context: Context, msg: String, callback: Response.Listener<String>){
            try {
                CoopVolleyGetter.send(context, msg, callback)
            } catch (e: Exception){
                e.printStackTrace()
            }
        }

        /*
        fun getStore(context: Context, storeId: String, callback: Response.Listener<String>) {
            SallingVolleyGetter.send(context, "/storeapi/v1/stores/$storeId", callback)
        }
         */
        /*
        fun getNearbyStores(context: Context, radius: Int, page: Int, pageSize: Int,  callback: Response.Listener<String>){
            var fusedLocationClient = CoopCommunicator.startLocationUpdates(context)

            if (fusedLocationClient != null) {
                try {
                    fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                        CoopVolleyGetter.send(
                            context,
                            "/storeapi/v1/stores/find/radius/$radius?latitude=${location?.latitude}&longitude=${location?.longitude}&page=$page&size=$pageSize",
                            callback
                        )
                    }
                } catch (e: SecurityException) {
                    e.printStackTrace()
                }
            }
        }
         */
        /*
        fun getStoreByBrand(context: Context, brand: String, page: Int, pageSize: Int, callback: Response.Listener<String>) {
            SallingVolleyGetter.send(context, "/storeapi/v1/stores/shopsByRetailGroup/$brand?page=$page&size=$pageSize", callback)
        }
         */

    }
}

