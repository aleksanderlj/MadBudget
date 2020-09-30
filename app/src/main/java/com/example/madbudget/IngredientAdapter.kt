package com.example.madbudget

import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import com.example.madbudget.models.Ingredient
import kotlinx.android.synthetic.main.ingredient_item.view.*

class IngredientAdapter(private val myDataset: ArrayList<Ingredient>) : RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        var itemView = LayoutInflater.from(parent.context).inflate(R.layout.ingredient_item,parent,false)
        return IngredientViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        val currentItem = myDataset[position]
        holder.editText.text.clear()
        holder.editText.text.append(currentItem.ingredientName)

    }

    override fun getItemCount(): Int {
        return myDataset.size
    }

    class IngredientViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var editText: EditText = itemView.ingredient_name

    }

}
