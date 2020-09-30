package com.example.madbudget

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.madbudget.models.Ingredient
import kotlinx.android.synthetic.main.activity_create_recipes.*

class CreateRecipesActivity : AppCompatActivity(){
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_recipes)

        val ingredientList: ArrayList<Ingredient> = ArrayList()

        val ingredient1 = Ingredient()

        ingredientList.add(ingredient1)

        ingredient_list.adapter = IngredientAdapter(ingredientList)
        ingredient_list.layoutManager = LinearLayoutManager(this)
        ingredient_list.setHasFixedSize(true)

    }











}