package com.gruppe17.madbudget.rest.coop

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class CoopVolleyGetter{
    companion object {
        fun send(context: Context, url: String, callback: Response.Listener<String>) {
            var requestQueue = RequestQueueSingleton.get(context)

            var stringRequest = object : StringRequest(
                Request.Method.GET,
                "https://api.cl.coop.dk$url",
                callback,
                Response.ErrorListener { error ->
                    var jsonError = String(error.networkResponse.data)
                    Log.e("VolleyError", jsonError)
                }) {
                override fun getHeaders(): MutableMap<String, String> {
                    val headers = HashMap<String, String>()
                    headers["Ocp-Apim-Subscription-Key"] = "20ec1564caa349bfa9f9a1f3b1a23bff"
                    return headers
                }
            }

            requestQueue.add(stringRequest)

            // https://developer.android.com/training/volley/simple#kotlin
        }
    }
}

class RequestQueueSingleton{
    companion object{
        private var rq: RequestQueue? = null

        fun get(context: Context): RequestQueue {
            if(rq == null){
                rq = Volley.newRequestQueue(context)
            }
            return rq as RequestQueue
        }
    }
}