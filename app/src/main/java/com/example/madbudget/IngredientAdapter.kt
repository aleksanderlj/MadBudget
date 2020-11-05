package com.example.madbudget

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.madbudget.models.Ingredient
import kotlinx.android.synthetic.main.ingredient_item_wp.view.*


class IngredientAdapter(private var myDataset: ArrayList<Ingredient>, var context: Context) : RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.ingredient_item_wp, parent, false)
        return IngredientViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        val currentItem = myDataset[position]
        holder.ingredientName.text = currentItem.ingredientName
        //holder.ingredientPrice.text = currentItem.ingredientPrice.toString()

    }

    override fun getItemCount(): Int {
        return myDataset.size
    }

    class IngredientViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var ingredientName: TextView = itemView.ingredient_name
        var ingredientPrice: TextView = itemView.ingredient_price

    }

}