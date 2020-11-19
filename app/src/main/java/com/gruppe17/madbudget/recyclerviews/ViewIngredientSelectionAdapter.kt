package com.gruppe17.madbudget.recyclerviews

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gruppe17.madbudget.R
import com.gruppe17.madbudget.models.Ingredient
import kotlinx.android.synthetic.main.item_dialog_view_ingredient.view.*


class ViewIngredientSelectionAdapter(private var myDataset: ArrayList<Ingredient>, var context: Context) : RecyclerView.Adapter<ViewIngredientSelectionAdapter.IngredientViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_dialog_view_ingredient, parent, false)
        return IngredientViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        val currentItem = myDataset[position]
        holder.ingredientName.text = currentItem.name
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