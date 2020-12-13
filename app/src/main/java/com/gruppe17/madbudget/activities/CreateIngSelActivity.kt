package com.gruppe17.madbudget.activities

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.view.animation.AnimationUtils
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isEmpty
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.gruppe17.madbudget.R
import com.gruppe17.madbudget.Utility
import com.gruppe17.madbudget.database.AppDatabase
import com.gruppe17.madbudget.database.DatabaseBuilder
import com.gruppe17.madbudget.models.Ingredient
import com.gruppe17.madbudget.models.IngredientSelection
import com.gruppe17.madbudget.recyclerviews.CreateIngredientSelectionDialogAdapter
import kotlinx.android.synthetic.main.activity_create_ingsel.*
import kotlinx.android.synthetic.main.dialog_ing_sel_search.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CreateIngSelActivity : AppCompatActivity(),
    CreateIngredientSelectionDialogAdapter.OnDialogIngredientClickListener {

    private var dialogIngNotSelected = ArrayList<Ingredient>()
    private var dialogIngSelected = ArrayList<Ingredient>()
    private var mAlertDialog: AlertDialog? = null
    private lateinit var db: AppDatabase
    private var recipeId = -1
    private var ingSelId = -1
    private lateinit var ingSelSearchAdapter: CreateIngredientSelectionDialogAdapter
    private lateinit var ingListAdapter: CreateIngredientSelectionDialogAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_ingsel)

        val fs = Firebase.firestore
        val collection = fs.collection("Assortments").document("1885").collection("Products")

        recipeId = intent.getIntExtra("ClickedRecipe", -1)

        //dialogIngNotSelected = Utility.getTestIngredientList()
        dialogIngSelected = ArrayList<Ingredient>()
        btn_ingsel_save.isEnabled = false

        inglist_selected.setHasFixedSize(true)
        inglist_selected.layoutManager = LinearLayoutManager(this)
        ingListAdapter = CreateIngredientSelectionDialogAdapter(dialogIngSelected, false)
        inglist_selected.adapter = ingListAdapter

        /*
        ingSelSearchAdapter =
            CreateIngredientSelectionDialogAdapter(dialogIngNotSelected, true)

        collection.limit(10).get().addOnSuccessListener { response ->
            for(doc in response){
                val i = RegexFilter.convertCoopIngredient(doc.toObject(CoopProduct::class.java))
                dialogIngNotSelected.add(i)
            }
            ingSelSearchAdapter.notifyDataSetChanged(dialogIngNotSelected)
        }

        val spinnerList = ArrayList<String>()
        spinnerList.add("G")
        spinnerList.add("ML")
        spinnerList.add("STK")
        var spinnerAdapter = object : ArrayAdapter<String>(
            this,
            R.layout.item_unit_spinner,
            R.id.item_spinner_text, spinnerList
        ) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                var view = super.getView(position, convertView, parent)
                view.item_spinner_text.text = spinnerList[position]
                return view
            }
        }

        unit_spinner.adapter = spinnerAdapter

         */

        btn_ingsel_search.setOnClickListener {
            //initSearchDialog()
            val i = Intent(this, SearchIngredientActivity::class.java)
            //i.putExtra("ClickedRecipe", recipeId)
            startActivityForResult(i, 1)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 1){

            ingSelId = data!!.getIntExtra("IngSelId", -1)
            db = DatabaseBuilder.get(this)

            GlobalScope.launch {
                dialogIngSelected.clear()
                dialogIngSelected.addAll(db.ingredientDao().getAllByIngredientSelectionId(ingSelId))
                runOnUiThread {
                    ingListAdapter.notifyDataSetChanged()
                }
            }

        }
    }

    /*
    private fun initSearchDialog() {
        var newAlertDialog = AlertDialog.Builder(this)
            .setView(LayoutInflater.from(this).inflate(R.layout.dialog_ing_sel_search, null))
            .setTitle("Tilføj ingrediensgruppe")
            .setPositiveButton("OK") { dialog, which ->

            }
            .create()

        mAlertDialog = newAlertDialog

        newAlertDialog.show()

        newAlertDialog.window!!.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)


        newAlertDialog.inglist_notselected.setHasFixedSize(true)
        newAlertDialog.inglist_notselected.layoutManager = LinearLayoutManager(this)
        newAlertDialog.inglist_notselected.adapter = ingSelSearchAdapter


        newAlertDialog.ingsel_search.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                var adapter =
                    newAlertDialog.inglist_notselected.adapter as CreateIngredientSelectionDialogAdapter
                adapter.filter.filter(s.toString())
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {}

        })


    }

     */


    override fun onBackPressed() {
        if(ingsel_name.text.toString().trim().isEmpty() && ingsel_amount.text.toString().trim().isEmpty() && dialogIngSelected.isEmpty()){
            super.onBackPressed()
        } else {

            val backPressDialog = AlertDialog.Builder(this)
                .setTitle("Gem ingrediensgruppe?")
                .setPositiveButton("Gem") { dialog, which ->
                    if (!ingsel_name.text.toString().trim().isEmpty() &&
                        !dialogIngSelected.isEmpty()
                    ) {

                        if(ingSelId == -1){
                            db = DatabaseBuilder.get(this)
                            GlobalScope.launch {
                                val ingSel = IngredientSelection(
                                    0,
                                    ingsel_name.text.toString(),
                                    null,
                                    null,
                                    true,
                                    recipeId
                                )
                                val ingSelId = db.ingredientSelectionDao().insert(ingSel)
                                val removables = ArrayList<Ingredient>()
                                for (i in dialogIngSelected) {
                                    i.ingredientSelectionParentId = ingSelId.toInt()
                                    if(i.selectedAmount <= 0) {
                                        removables.add(i)
                                    }
                                }
                                dialogIngSelected.removeAll(removables)
                                db.ingredientDao().insertAll(dialogIngSelected)
                                db.ingredientDao().deleteAll(removables)
                                runOnUiThread {
                                    super.onBackPressed()
                                }
                            }
                        } else {
                            db = DatabaseBuilder.get(this)
                            GlobalScope.launch {
                                val ingSel = IngredientSelection(
                                    ingSelId,
                                    ingsel_name.text.toString(),
                                    null,
                                    null,
                                    true,
                                    recipeId
                                )
                                db.ingredientSelectionDao().update(ingSel)

                                val removables = ArrayList<Ingredient>()
                                for (i in dialogIngSelected) {
                                    i.ingredientSelectionParentId = ingSelId
                                    if(i.selectedAmount <= 0) {
                                        removables.add(i)
                                    }
                                }

                                dialogIngSelected.removeAll(removables)

                                db.ingredientDao().updateAll(dialogIngSelected)
                                db.ingredientDao().deleteAll(removables)
                                runOnUiThread {
                                    super.onBackPressed()
                                }
                            }
                        }


                    } else {

                        if (ingsel_name.text.toString().trim().isEmpty()) {
                            val vibrate = AnimationUtils.loadAnimation(this, R.anim.vibrate)
                            ingsel_name.setError("Udfyld navn")
                            ingsel_name.startAnimation(vibrate)
                        }
                        if (dialogIngSelected.isEmpty()) {
                            val vibrate = AnimationUtils.loadAnimation(this, R.anim.vibrate)
                            tv_info2.setError("Vælg mindst én vare")
                            tv_info2.requestFocus()
                            tv_info2.startAnimation(vibrate)
                        }

                    }

                }
                .setNegativeButton("Kassér") { dialog, which ->
                    super.onBackPressed()
                }
                .setNeutralButton("Annuller") { dialog, which -> }
                .show()

            val window = backPressDialog!!.window
            val attr = window!!.attributes

            attr.gravity = Gravity.BOTTOM
            window.attributes = attr
        }
    }

    override fun onDialogIngredientSelect(pos: Int) {
        val item = dialogIngNotSelected.removeAt(pos)
        dialogIngSelected.add(0, item)

        val notSelectedAdapter =
            mAlertDialog!!.inglist_notselected.adapter!! as CreateIngredientSelectionDialogAdapter
        val selectedAdapter =
            inglist_selected.adapter!! as CreateIngredientSelectionDialogAdapter

        notSelectedAdapter.notifyItemRemoved(item)
        selectedAdapter.notifyItemAdded(item)
    }

    override fun onDialogIngredientDeselect(pos: Int) {
        val item = dialogIngSelected.removeAt(pos)
        dialogIngNotSelected.add(0, item)

        val selectedAdapter =
            inglist_selected.adapter!! as CreateIngredientSelectionDialogAdapter
        val notSelectedAdapter =
            mAlertDialog!!.inglist_notselected.adapter!! as CreateIngredientSelectionDialogAdapter

        selectedAdapter.notifyItemRemoved(item)
        notSelectedAdapter.notifyItemAdded(item)
    }
}