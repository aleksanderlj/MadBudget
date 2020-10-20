package com.example.madbudget

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.madbudget.models.Ingredient
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_create_recipes.*
import kotlinx.android.synthetic.main.activity_create_recipes.ingredient_list
import kotlinx.android.synthetic.main.activity_create_recipes_wip.*
import kotlinx.android.synthetic.main.select_ingerdient_dialog.*

class CreateRecipeActivity : AppCompatActivity(), IngredientAdapter.OnRecipeAddClickListener, IngredientAdapter.OnRecipeEditClickListener {


    private val ingredientList: ArrayList<Ingredient> = ArrayList()
    private val myDataSetTest: ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_recipes_wip)

        val ingredient21 = ("Mountain Dew")
        val ingredient31 = ("Doritos")

        myDataSetTest.add(ingredient21)
        myDataSetTest.add(ingredient31)

        ingredient_list.adapter = IngredientAdapter(ingredientList, this, this, this)
        ingredient_list.layoutManager = LinearLayoutManager(this)
        ingredient_list.setHasFixedSize(true)

        val button: FloatingActionButton = add_ingredient_button
        button.setOnClickListener(View.OnClickListener {
            onRecipeAddClick(ingredientList.lastIndex)
        })

    }

    override fun onRecipeEditClick(position: Int) {

        val mDialogView = LayoutInflater.from(this).inflate(R.layout.select_ingerdient_dialog, null)

        //init spinner
        createSpinner(mDialogView)

        //show alertDialog
        val mAlertDialog: AlertDialog = createAlertDialog(mDialogView)

        //override OK button functionality
        val okButton: Button = mAlertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
        okButton.setOnClickListener(View.OnClickListener {
            editIngredient(mDialogView, mAlertDialog, position)
            mAlertDialog.dismiss()
        })
    }

    override fun onRecipeAddClick(position: Int) {

        val mDialogView =
            LayoutInflater.from(this).inflate(R.layout.select_ingerdient_dialog, null)

        //init spinner
        createSpinner(mDialogView)

        //show alertDialog
        val mAlertDialog: AlertDialog = createAlertDialog(mDialogView)

        //override OK button functionality
        val okButton: Button = mAlertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
        okButton.setOnClickListener(View.OnClickListener {
            addIngredient(mDialogView, mAlertDialog)

        })
    }

    private fun createAlertDialog(mDialogView: View): AlertDialog {

        return AlertDialog.Builder(this)
            .setView(mDialogView)
            .setTitle("Tilføj Ingrediens...") //TODO string resource
            .setPositiveButton("OK", null)
            .setNegativeButton("Cancel") {
                    _, _ -> Toast.makeText(this, "Cancel", Toast.LENGTH_SHORT).show()
            }
            .show()
    }

    private fun createSpinner(mDialogView: View){

        val spinner: Spinner = mDialogView.findViewById(R.id.select_ingredient_spinner)

        ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            myDataSetTest
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
    }

    private fun addIngredient(mDialogView: View, mAlertDialog: AlertDialog){

        val spinner: Spinner = mDialogView.findViewById(R.id.select_ingredient_spinner)

        //TODO Fix how information is retrieved
        if (spinner.selectedItem != null ){
            val ingredient = Ingredient()
            ingredient.ingredientName = spinner.selectedItem.toString()
            ingredient.amount = spinner_ingredient_weight?.text.toString()
            ingredient.hasBeenClicked = true
            ingredientList.add(ingredient)
            ingredient_list.adapter?.notifyDataSetChanged()
            mAlertDialog.dismiss()

        }else
            Toast.makeText(this,"Vælg ingrediens", Toast.LENGTH_LONG).show()
    }

    private fun editIngredient(mDialogView: View, mAlertDialog: AlertDialog, position: Int) {

        val spinner: Spinner = mDialogView.findViewById(R.id.select_ingredient_spinner)

        //TODO Fix how information is retrieved
        if (spinner.selectedItem != null ){
            val ingredient = ingredientList[position]
            ingredient.ingredientName = spinner.selectedItem.toString()
            ingredient.amount = spinner_ingredient_weight?.text.toString()
            ingredient.hasBeenClicked = true
            ingredientList[position] = ingredient
            ingredient_list.adapter?.notifyDataSetChanged()
            mAlertDialog.dismiss()
        }else
            Toast.makeText(this,"Vælg ingrediens", Toast.LENGTH_LONG).show()
    }
}
