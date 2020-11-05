package com.example.madbudget

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.madbudget.models.Ingredient
import kotlinx.android.synthetic.main.ingredient_item.view.*

class RecipeIngredientsAdapter(private val myDataset: List<Ingredient>) : RecyclerView.Adapter<RecipeIngredientsAdapter.RecipeIngredientsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeIngredientsViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.ingredient_item, parent, false)
        return RecipeIngredientsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecipeIngredientsViewHolder, position: Int) {
        val currentItem = myDataset[position]
        holder.name.text = currentItem.ingredientName
        holder.amount.text = currentItem.amount
    }

    override fun getItemCount(): Int {
        return myDataset.size
    }

    class RecipeIngredientsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.ingredient_name
        val amount: TextView = itemView.ingredient_amount
    }
}