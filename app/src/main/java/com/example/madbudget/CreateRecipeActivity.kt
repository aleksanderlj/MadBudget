package com.example.madbudget

import android.app.AlertDialog
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.madbudget.models.Ingredient
import kotlinx.android.synthetic.main.activity_create_recipes.*
import kotlinx.android.synthetic.main.select_ingerdient_dialog.*

class CreateRecipeActivity : AppCompatActivity(), IngredientAdapter.OnRecipeClickListener {


    private val ingredientList: ArrayList<Ingredient> = ArrayList()
    private val myDataSetTest: ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_recipes)

        val ingredient1 = Ingredient()

        val ingredient2 = ("Mountain Dew")
        val ingredient3 = ("Doritos")
        ingredientList.add(ingredient1)

        myDataSetTest.add(ingredient2)
        myDataSetTest.add(ingredient3)


        ingredient_list.adapter = IngredientAdapter(ingredientList, this, this)
        ingredient_list.layoutManager = LinearLayoutManager(this)
        ingredient_list.setHasFixedSize(true)

    }

    override fun onRecipeClick(position: Int) {

        if (!ingredientList[position].hasBeenClicked) {

            val mDialogView =
                LayoutInflater.from(this).inflate(R.layout.select_ingerdient_dialog, null)

            //init spinner
            createSpinner(mDialogView)

            //show alertDialog
            createAlertDialog(mDialogView)
        }else{
            ingredientList.removeAt(position)
            ingredient_list.adapter?.notifyDataSetChanged()
        }
    }

    private fun createAlertDialog(mDialogView: View){

        val spinner: Spinner = mDialogView.findViewById(R.id.select_ingredient_spinner)

        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)
            .setTitle("Tilføj Ingrediens...") //TODO string resource
            .setPositiveButton("OK",null)
            .setNegativeButton("Cancel"){
                    _,_ -> Toast.makeText(this, "Cancel",Toast.LENGTH_SHORT).show()
            }

        val mAlertDialog = mBuilder.show()

        val okButton: Button = mAlertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
        okButton.setOnClickListener(View.OnClickListener {
            if (spinner.selectedItem != null ){
                val ingredient = Ingredient()
                ingredient.ingredientName = spinner.selectedItem.toString()
                ingredient.amount = spinner_ingredient_amount?.text.toString()
                ingredient.hasBeenClicked = true
                ingredientList.add(0,ingredient)
                ingredient_list.adapter?.notifyDataSetChanged()
                mAlertDialog.dismiss()
            }else
               Toast.makeText(this,"Vælg ingrediens", Toast.LENGTH_LONG).show()
        })
    }

    private fun createSpinner(mDialogView: View){
        val spinner: Spinner = mDialogView.findViewById(R.id.select_ingredient_spinner)
        ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            myDataSetTest
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }
    }
}

