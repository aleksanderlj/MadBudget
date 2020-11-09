package com.gruppe17.madbudget

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.gruppe17.madbudget.models.IngredientSelectionWithIngredients
import kotlinx.android.synthetic.main.ingredient_item_in_recipe.view.*


class IngredientSelectionAdapter(var myDataset: ArrayList<IngredientSelectionWithIngredients>,
                                 var context: Context,
                                 var ingredientSelectionClickListener: OnIngredientSelectionClickListener) : RecyclerView.Adapter<IngredientSelectionAdapter.IngredientViewHolder>()
{

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        val currentItem = myDataset[position]

        holder.ingredientName.text = currentItem.ingredientSelection.ingredientSelectionName
        holder.ingredientAmount.text = currentItem.ingredientSelection.ingredientSelectionAmount
        holder.ingredientPicture.setImageDrawable(
            ContextCompat.getDrawable(
                context,
                R.drawable.ghetto
            )
        )

        if (currentItem.ingredients.isNotEmpty()){
            holder.ingredientPrice.text = currentItem.ingredients.first().ingredientPrice.toString()
        }

        holder.ingredientCheckBox.isChecked = myDataset[position].ingredientSelection.isSelected

        holder.ingredientCheckBox.setOnClickListener {
            myDataset[position].ingredientSelection.isSelected = !myDataset[position].ingredientSelection.isSelected
            // onIngredientCheckBoxClickListener.onCheckBoxClick(position)

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.ingredient_item_in_recipe, parent, false)
        return IngredientViewHolder(itemView, ingredientSelectionClickListener)
    }

    override fun getItemCount(): Int {
        return myDataset.size
    }

    class IngredientViewHolder(itemView: View, ingredientSelectionClickListener: OnIngredientSelectionClickListener) : RecyclerView.ViewHolder(itemView) {
        var ingredientName: TextView = itemView.ingredient_namead
        var ingredientPrice: TextView = itemView.ingredient_pricead
        var ingredientAmount: TextView = itemView.ingredient_amount
        var ingredientPicture: ImageView = itemView.ingredient_store_image
        var ingredientCheckBox: CheckBox = itemView.ingredient_checkbox


       /* init {
            itemView.setOnClickListener {
                ingredientSelectionClickListener.onIngredientClick(adapterPosition)
            }
        }*/

        /*init {
            ingredientCheckBox.setOnClickListener {
                onIngredientCheckBoxClickListener.onCheckBoxClick(adapterPosition)
            }
        }*/
    }

    interface OnIngredientSelectionClickListener{
        fun onIngredientClick(position: Int)
    }

    interface OnIngredientCheckBoxClickListener{
        fun onCheckBoxClick(position: Int)
    }

}