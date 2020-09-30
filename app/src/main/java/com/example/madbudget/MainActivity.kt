package com.example.madbudget

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recipes.setOnClickListener{
            val recipesActivity = Intent(this, Recipes::class.java)
            startActivity(recipesActivity);
        }
        createRecipes.setOnClickListener{
            val createRecipesActivity = Intent(this, CreateRecipesActivity::class.java)
            startActivity(createRecipesActivity)
        }
    }
}