package com.example.madbudget

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.madbudget.models.Ingredient
import kotlinx.android.synthetic.main.activity_create_ingredient.*
import kotlinx.android.synthetic.main.ingredient_item_in_recipe.*

class CreateIngredientActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_ingredient)

        var button = test_button

        button.setOnClickListener(View.OnClickListener {
            val ingredient = Ingredient()
            ingredient.ingredientName = "ost"
            ingredient.amount = 25.5
            ingredient.ingredientType = "Mejeri"

            val intent: Intent = Intent()
            intent.putExtra("ingredient_object", ingredient)
            setResult(RESULT_OK, intent)
            finish()

        })
    }





}