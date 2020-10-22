package com.example.madbudget

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.androidbuts.multispinnerfilter.KeyPairBoolData
import com.androidbuts.multispinnerfilter.MultiSpinnerSearch
import com.example.madbudget.models.Ingredient
import com.example.madbudget.models.IngredientSelection
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_create_recipes.ingredient_selection_list
import kotlinx.android.synthetic.main.activity_create_recipes_wip.*
import kotlinx.android.synthetic.main.select_ingerdient_dialog.*
import kotlinx.android.synthetic.main.show_ingredient_dialog.*


class CreateRecipeActivity : AppCompatActivity(), IngredientSelectionAdapter.OnIngredientSelectionClickListener{

    private val ingredientList: ArrayList<Ingredient> = ArrayList()
    private val ingredientSelectionList: ArrayList<IngredientSelection> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_recipes_wip)

        ingredient_selection_list.adapter = IngredientSelectionAdapter(ingredientSelectionList,this, this )
        ingredient_selection_list.layoutManager = LinearLayoutManager(this)
        ingredient_selection_list.setHasFixedSize(true)

        val button: FloatingActionButton = add_ingredient_button
        button.setOnClickListener(View.OnClickListener {
            val mAlertDialog = createAlertDialog(
                LayoutInflater.from(this).inflate(R.layout.select_ingerdient_dialog, null)
            )
            val okButton: Button = mAlertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
            okButton.setOnClickListener(View.OnClickListener {
                val ingredientSelection = IngredientSelection(mAlertDialog.spinner_ingredient_selection_name.text.toString(),ingredientList)
                ingredientSelectionList.add(ingredientSelection)
                ingredient_selection_list.adapter?.notifyDataSetChanged()
                mAlertDialog.dismiss()

            })
        })
    }

    private fun createAlertDialog(mDialogView: View): AlertDialog {

        createSpinner(mDialogView)

        return AlertDialog.Builder(this)
            .setView(mDialogView)
            .setTitle("Tilføj Ingrediens...") //TODO string resource
            .setPositiveButton("OK", null)
            .setNegativeButton("Cancel") { _, _ ->
                Toast.makeText(
                    this,
                    "Cancel",
                    Toast.LENGTH_SHORT
                ).show()
            }
            .show()
    }

    private fun createSpinner(mDialogView: View) {

        val list = resources.getStringArray(R.array.sports_array)
        val listArray1: MutableList<KeyPairBoolData> = ArrayList()
        for (i in list.indices) {
            val h = KeyPairBoolData()
            h.id = i + 1.toLong()
            h.name = list[i]
            h.price = "10"
            listArray1.add(h)
        }

        val multiSelectSpinnerWithSearch: MultiSpinnerSearch =
            mDialogView.findViewById(R.id.multiItemSelectionSpinner)
        multiSelectSpinnerWithSearch.isSearchEnabled = true
        multiSelectSpinnerWithSearch.setClearText("Close & Clear");
        multiSelectSpinnerWithSearch.hintText = "Vælg Ingrediens"

        multiSelectSpinnerWithSearch.setItems(listArray1) {
            for (i in it.indices) {
                if (it[i].isSelected) {
                    val ingredient = Ingredient()
                    ingredient.ingredientName = it[i].name
                    ingredient.ingredientPrice = it[i].price
                    ingredientList.add(ingredient)
                }
            }
        }
    }

    override fun onIngredientClick(position: Int) {

        val mAlertDialog = AlertDialog.Builder(this)
            .setView(LayoutInflater.from(this).inflate(R.layout.show_ingredient_dialog, null))
            .setTitle("Ingredienser")
            .setPositiveButton("OK", null)
            .setNegativeButton("Cancel") { _, _ ->
                Toast.makeText(
                    this,
                    "Cancel",
                    Toast.LENGTH_SHORT
                ).show()
            }
            .show()


        val ingredient = Ingredient()
        ingredient.ingredientName = "bund"
        ingredient.ingredientPrice = "12"
        ingredientSelectionList[0].ingredientList.add(ingredient)
        mAlertDialog.ingredient_list.adapter = IngredientAdapter(ingredientSelectionList[0].ingredientList,this)
        mAlertDialog.ingredient_list.layoutManager = LinearLayoutManager(this)
        mAlertDialog.ingredient_list.setHasFixedSize(true)
    }

}

