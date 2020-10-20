package com.example.madbudget;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.androidbuts.multispinnerfilter.KeyPairBoolData;
import com.androidbuts.multispinnerfilter.MultiSpinnerSearch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddIngredientActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_ingredient_activity);


        final List<String> list = Arrays.asList(getResources().getStringArray(R.array.sports_array));
        final List<KeyPairBoolData> listArray1 = new ArrayList<>();


        for (int i = 0; i < list.size(); i++) {
            KeyPairBoolData h = new KeyPairBoolData();
            h.setId(i + 1);
            h.setName(list.get(i));
            h.setSelected(false);
            listArray1.add(h);
        }

        MultiSpinnerSearch multiSelectSpinnerWithSearch = findViewById(R.id.multiItemSelectionSpinner);
        multiSelectSpinnerWithSearch.setHintText("Vælg ingrediens");
        multiSelectSpinnerWithSearch.setItems(listArray1, items -> {
            //The followings are selected items.
            for (int i = 0; i < items.size(); i++) {
                Log.i("YO", i + " : " + items.get(i).getName() + " : " + items.get(i).isSelected());
            }
        });
    }
}