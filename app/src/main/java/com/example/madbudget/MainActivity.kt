package com.example.madbudget

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
    }
}