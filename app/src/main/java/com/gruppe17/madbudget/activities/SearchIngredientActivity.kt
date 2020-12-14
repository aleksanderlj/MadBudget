package com.gruppe17.madbudget.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.gruppe17.madbudget.R
import com.gruppe17.madbudget.Utility
import com.gruppe17.madbudget.database.DatabaseBuilder
import com.gruppe17.madbudget.models.Ingredient
import com.gruppe17.madbudget.models.IngredientSelection
import com.gruppe17.madbudget.recyclerviews.CreateIngredientSelectionDialogAdapter
import com.gruppe17.madbudget.rest.coop.RegexFilter
import com.gruppe17.madbudget.rest.coop.model.CoopProduct
import io.grpc.okhttp.internal.Util
import kotlinx.android.synthetic.main.activity_create_ingsel.*
import kotlinx.android.synthetic.main.activity_create_ingsel.inglist_selected
import kotlinx.android.synthetic.main.activity_search_ingredients.*
import kotlinx.android.synthetic.main.dialog_ing_sel_search.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SearchIngredientActivity : AppCompatActivity() {

    val ingredientList = ArrayList<Ingredient>()
    lateinit var listAdapter: CreateIngredientSelectionDialogAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_ingredients)

        ing_search_list.setHasFixedSize(true)
        ing_search_list.layoutManager = LinearLayoutManager(this)
        listAdapter = CreateIngredientSelectionDialogAdapter(ingredientList, true)
        ing_search_list.adapter = listAdapter


        /*
        val fs = Firebase.firestore
        val collection = fs.collection("Assortments").document("1885").collection("Products")

        collection.limit(10).get().addOnSuccessListener { response ->
            for(doc in response){
                val i = RegexFilter.convertCoopIngredient(doc.toObject(CoopProduct::class.java))
                ingredientList.add(i)
            }
            listAdapter.notifyDataSetChanged(ingredientList)
        }

         */

        // We load test data to not overload the firestore
        val s = Utility.readFileAsString(R.raw.assortment, this)
        val cp = Utility.parseArray<CoopProduct>(s)!!
        for (i in cp){
            val n = RegexFilter.convertCoopIngredient(i)
            ingredientList.add(n)
        }
        listAdapter.notifyDataSetChanged()

        ing_search_box.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                listAdapter.filter.filter(s.toString())
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {}

        })

        ing_search_box.requestFocus()
        //val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        //imm.showSoftInput(ing_search_box, InputMethodManager.SHOW_IMPLICIT)
        //window!!.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        btn_back.setOnClickListener{finish()}

    }

    override fun finish() {
        val savedList = ArrayList<Ingredient>()
        for(i in ingredientList){
            if(i.selectedAmount > 0){
                savedList.add(i)
            }
        }

        val db = DatabaseBuilder.get(this)
        GlobalScope.launch {
            val ingSel = IngredientSelection()
            val ingSelId = db.ingredientSelectionDao().insert(ingSel)
            for (i in savedList) {
                i.ingredientSelectionParentId = ingSelId.toInt()
            }
            db.ingredientDao().insertAll(savedList)
            runOnUiThread {
                val i = Intent()
                i.putExtra("IngSelId", ingSelId.toInt())
                setResult(Activity.RESULT_OK, i)
                super.finish()
            }
        }
    }
}