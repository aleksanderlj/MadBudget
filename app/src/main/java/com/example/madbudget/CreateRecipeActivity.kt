package com.example.madbudget

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.madbudget.models.Ingredient
import kotlinx.android.synthetic.main.activity_create_recipes.*
import org.jetbrains.anko.startActivityForResult

class CreateRecipeActivity : AppCompatActivity(), IngredientAdapter.OnRecipeClickListener{

    val ingredientList: ArrayList<Ingredient> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_recipes)

        val ingredient1 = Ingredient()

        ingredientList.add(ingredient1)

        ingredient_list.adapter = IngredientAdapter(ingredientList,this,this)
        ingredient_list.layoutManager = LinearLayoutManager(this)
        ingredient_list.setHasFixedSize(true)

    }

    override fun onRecipeClick(position: Int) {
        val createIngredientActivity = Intent(this, CreateIngredientActivity::class.java)
        startActivityForResult(createIngredientActivity,1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null){
            if (resultCode == RESULT_OK){
                ingredientList.add(0,data.getSerializableExtra("ingredient_object") as Ingredient)
                ingredient_list.adapter?.notifyDataSetChanged()
            }
        }
    }
}