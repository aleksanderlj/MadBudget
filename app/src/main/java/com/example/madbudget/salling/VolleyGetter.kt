package com.example.madbudget.salling

import android.content.Context
import android.util.Log
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley


class VolleyGetter() {
    companion object {
        fun send(context: Context, url: String) {
            var requestQueue = Volley.newRequestQueue(context)
            //var url = "https://api.sallinggroup.com/v1/food-waste/?zip=8000"

            var stringRequest = object : StringRequest(
                Request.Method.GET,
                url,
                Response.Listener<String> { response ->
                    Log.i("VolleySuccess", response.toString())
                },
                Response.ErrorListener { error ->
                    var jsonError = String(error.networkResponse.data)
                    Log.e("VolleyError", jsonError)
                }) {
                override fun getHeaders(): MutableMap<String, String> {
                    val headers = HashMap<String, String>()
                    headers["Authorization"] = "JWT " + TokenBuilder.getJWT(url.substring(28))
                    return headers
                }
            }

            requestQueue.add(stringRequest)

            // TODO https://developer.android.com/training/volley/simple#kotlin
        }
    }
}