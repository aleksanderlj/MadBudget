package com.gruppe17.madbudget.salling

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley


class SallingVolleyGetter {
    companion object {
        fun send(context: Context, url: String, callback: Response.Listener<String>) {
            var requestQueue = Volley.newRequestQueue(context)

            var stringRequest = object : StringRequest(
                Request.Method.GET,
                "https://api.sallinggroup.com$url",
                callback,
                Response.ErrorListener { error ->
                    var jsonError = String(error.networkResponse.data)
                    Log.e("VolleyError", jsonError)
                }) {
                override fun getHeaders(): MutableMap<String, String> {
                    val headers = HashMap<String, String>()
                    headers["Authorization"] = "JWT " + TokenBuilder.getJWT(url)
                    return headers
                }
            }

            requestQueue.add(stringRequest)

            // TODO https://developer.android.com/training/volley/simple#kotlin
        }
    }
}