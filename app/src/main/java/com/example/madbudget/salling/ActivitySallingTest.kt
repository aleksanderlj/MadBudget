package com.example.madbudget.salling

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.madbudget.R
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.activity_sallingtest.*

class ActivitySallingTest : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sallingtest)

        getButton.setOnClickListener {
            getText.text = "Check logcat"
            SallingCommunicator.getNearbyStores(this, 20) { response ->
                Log.i("Stores", response.toString())
            }

            SallingCommunicator.getProductSuggestions(this, "kylling") { response ->
                Log.i("Suggestions", response.toString())
            }

            SallingCommunicator.getSimilarProducts(this, "31802") { response ->
                Log.i("Similar", response.toString())
            }

            SallingCommunicator.getNearbyDiscounts(this, 20) { response ->
                Log.i("Discounts", response.toString())
            }

        }

    }
}