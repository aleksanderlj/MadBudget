package com.example.madbudget.salling

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class VolleyGetter(var context: Context) {
    fun test() {
        var requestQueue = Volley.newRequestQueue(context)
        var url = "https://api.sallinggroup.com/v1/food-waste/?zip=8000"
        var responseHolder = ""

        var stringRequest = StringRequest(
            Request.Method.GET,
            url,
            Response.Listener<String> { response ->
                Log.i("VolleySuccess", response.toString())
            },
            Response.ErrorListener { Log.e("VolleyError", "Test response") })
        requestQueue.add(stringRequest)

        // TODO https://developer.android.com/training/volley/simple#kotlin

    }
}