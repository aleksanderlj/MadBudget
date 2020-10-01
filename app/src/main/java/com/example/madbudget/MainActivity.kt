package com.example.madbudget

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.madbudget.salling.ActivitySallingTest
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recipes.setOnClickListener({
            // Do stuff
        })
        createRecipes.setOnClickListener({
            // Do other stuff
        })
        testyBoi.setOnClickListener{
            var i = Intent(this, ActivitySallingTest::class.java)
            startActivity(i)
        }
    }
}