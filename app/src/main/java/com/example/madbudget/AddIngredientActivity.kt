/*
package com.example.madbudget

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.androidbuts.multispinnerfilter.KeyPairBoolData
import com.androidbuts.multispinnerfilter.MultiSpinnerSearch

class AddIngredientActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_ingredient_activity)

        val list = resources.getStringArray(R.array.sports_array)

        val listArray1: MutableList<KeyPairBoolData> = ArrayList()

        for (i in list.indices) {
            val h = KeyPairBoolData()
            h.id = ((i + 1).toLong())
            h.name = list[i].toString()
            h.isSelected = false
            listArray1.add(h)
        }


        val multiSelectSpinnerWithSearch: MultiSpinnerSearch =
            LayoutInflater.from(this).inflate(R.layout.select_ingerdient_dialog, null)
                .findViewById(R.id.multiItemSelectionSpinner)

        //TODO CONVERT TO KOTLIN
           multiSelectSpinnerWithSearch.setItems(listArray1, items -> {
            //The followings are selected items.
            for (int i = 0; i < items.size(); i++) {
            Log.i("YO", i + " : " + items.get(i).getName() + " : " + items.get(i).isSelected());
        }
        });

    }
}*/
