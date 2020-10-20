package com.example.madbudget


import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.androidbuts.multispinnerfilter.KeyPairBoolData
import com.androidbuts.multispinnerfilter.MultiSpinnerSearch
import com.example.madbudget.models.Ingredient
import com.example.madbudget.salling.ActivitySallingTest
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_create_recipes.ingredient_list
import kotlinx.android.synthetic.main.activity_create_recipes_wip.*


class CreateRecipeActivity : AppCompatActivity(), IngredientAdapter.OnRecipeAddClickListener, IngredientAdapter.OnRecipeEditClickListener {


    private val ingredientList: ArrayList<Ingredient> = ArrayList()
    private val myDataSetTest: ArrayList<Ingredient> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_recipes_wip)

        val ingredient1: Ingredient = Ingredient()
        val ingredient2: Ingredient = Ingredient()
        val ingredient3: Ingredient = Ingredient()
        val ingredient4: Ingredient = Ingredient()
        ingredient1.ingredientName = "bund"
        ingredient2.ingredientName = "igen"
        ingredient3.ingredientName = "he"
        ingredient4.ingredientName = "bawd"
        myDataSetTest.add(ingredient1)
        myDataSetTest.add(ingredient2)
        myDataSetTest.add(ingredient3)
        myDataSetTest.add(ingredient4)


        ingredient_list.adapter = IngredientAdapter(ingredientList, this, this, this)
        ingredient_list.layoutManager = LinearLayoutManager(this)
        ingredient_list.setHasFixedSize(true)

        createSpinner( LayoutInflater.from(this).inflate(R.layout.select_ingerdient_dialog, null))


        val button: FloatingActionButton = add_ingredient_button
        button.setOnClickListener(View.OnClickListener {
            /*onRecipeAddClick(ingredientList.lastIndex)*/
            val i = Intent(this, AddIngredientActivity::class.java)
            startActivity(i)
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
            .setNegativeButton("Cancel") { _, _ -> Toast.makeText(
                this,
                "Cancel",
                Toast.LENGTH_SHORT
            ).show()
            }
            .show()
    }

    private fun createSpinner(mDialogView: View){

        val list = resources.getStringArray(R.array.sports_array)
        val listArray1: MutableList<KeyPairBoolData> = ArrayList()
        for (i in 0 until list.size) {
            val h = KeyPairBoolData()
            h.id = i + 1.toLong()
            h.name = list.get(i)
            h.isSelected = i < 5
            listArray1.add(h)
        }

        val multiSelectSpinnerWithSearch: MultiSpinnerSearch = mDialogView.findViewById(R.id.multiItemSelectionSpinner)
        multiSelectSpinnerWithSearch.isSearchEnabled = true
        multiSelectSpinnerWithSearch.setClearText("Close & Clear");


        multiSelectSpinnerWithSearch.setItems(
            listArray1
        ) { items ->
            for (i in items.indices) {
                if (items[i].isSelected) {
                    Log.i(
                        "HEJ", i.toString() + " : " + items[i].name + " : " + items[i].isSelected
                    )
                }
            }
        }
    }

    private fun addIngredient(mDialogView: View, mAlertDialog: AlertDialog){

       /* val spinner: Spinner = mDialogView.findViewById(R.id.select_ingredient_spinner)

        //TODO Fix how information is retrieved
        if (spinner.selectedItem != null ){
            val ingredient = Ingredient()
            ingredient.ingredientName = spinner.selectedItem.toString()
            ingredient.amount = spinner_ingredient_weight?.text.toString()
            ingredient.isSelected = true
            ingredientList.add(ingredient)
            ingredient_list.adapter?.notifyDataSetChanged()
            mAlertDialog.dismiss()

        }else
            Toast.makeText(this, "Vælg ingrediens", Toast.LENGTH_LONG).show()*/
    }

    private fun editIngredient(mDialogView: View, mAlertDialog: AlertDialog, position: Int) {

       /* val spinner: Spinner = mDialogView.findViewById(R.id.select_ingredient_spinner)

        //TODO Fix how information is retrieved
        if (spinner.selectedItem != null ){
            val ingredient = ingredientList[position]
            ingredient.ingredientName = spinner.selectedItem.toString()
            ingredient.amount = spinner_ingredient_weight?.text.toString()
            ingredient.isSelected = true
            ingredientList[position] = ingredient
            ingredient_list.adapter?.notifyDataSetChanged()
            mAlertDialog.dismiss()
        }else
            Toast.makeText(this, "Vælg ingrediens", Toast.LENGTH_LONG).show()*/
    }
}
