package com.example.madbudget.salling

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.madbudget.R
import kotlinx.android.synthetic.main.activity_sallingtest.*

class ActivitySallingTest : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sallingtest)

        TokenBuilder.getJWT("")

        getButton.setOnClickListener {
            getText.text = "000"
            VolleyGetter.send(this, "https://api.sallinggroup.com/v1/food-waste/?zip=8000")
        }

    }
}