package com.gruppe17.madbudget

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gruppe17.madbudget.salling.ActivitySallingTest
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recipes.setOnClickListener{
            val recipesActivity = Intent(this, Recipes::class.java)
            startActivity(recipesActivity);
        }

        testyBoi.setOnClickListener{
            var i = Intent(this, ActivitySallingTest::class.java)
            startActivity(i)
        }

        map_button.setOnClickListener {
            val mapsActivity = Intent(this,MapsActivity::class.java)
            startActivity(mapsActivity)
        }

    }
}