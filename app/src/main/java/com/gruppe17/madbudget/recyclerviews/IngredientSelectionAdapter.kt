package com.gruppe17.madbudget.recyclerviews

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.gruppe17.madbudget.R
import com.gruppe17.madbudget.models.Ingredient
import com.gruppe17.madbudget.models.IngredientSelectionWithIngredients
import kotlinx.android.synthetic.main.item_ingredient_in_recipe.view.*


class IngredientSelectionAdapter(
    var myDataset: ArrayList<IngredientSelectionWithIngredients>,
    var context: Context,
    var ingredientSelectionClickListener: OnIngredientSelectionClickListener,
    var ingredientCheckBoxClickListener: OnIngredientCheckBoxClickListener
) : RecyclerView.Adapter<IngredientSelectionAdapter.IngredientViewHolder>() {

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        val currentItem = myDataset[position]

        holder.ingredientName.text = currentItem.ingredientSelection.name
       /* holder.ingredientAmount.text = "${currentItem.ingredientSelection.amount.toString()} ${currentItem.ingredientSelection.unit}"
        holder.ingredientPicture.setImageDrawable(
            ContextCompat.getDrawable(
                context,
                R.drawable.brugsen
            )
        )*/

        if (currentItem.ingredients.isNotEmpty()) {
            var smallestPrice = Double.MAX_VALUE
            var smallestPriceProduct: Ingredient? = null

            for (curIng in currentItem.ingredients) {
                val ingPrice = curIng.calculatePrice()
                if (ingPrice < smallestPrice) {
                    smallestPrice = ingPrice
                    smallestPriceProduct = curIng
                }
            }

            holder.ingredientPriceCrowns.text = "%.0f".format(smallestPrice)
            val doubleAsString = smallestPrice.toString()
            val indexofDecimal = doubleAsString.indexOf(".")

            if (doubleAsString.substring(indexofDecimal).length == 2)
                holder.ingredientPriceEars.text = doubleAsString.substring(indexofDecimal).plus("0")
            else
                holder.ingredientPriceEars.text = doubleAsString.substring(indexofDecimal)

            holder.productName.text = smallestPriceProduct!!.name

        }

        holder.ingredientCheckBox.isChecked = myDataset[position].ingredientSelection.isSelected

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_ingredient_in_recipe, parent, false)
        return IngredientViewHolder(
            itemView,
            ingredientSelectionClickListener,
            ingredientCheckBoxClickListener
        )
    }

    override fun getItemCount(): Int {
        return myDataset.size
    }

    class IngredientViewHolder(
        itemView: View,
        ingredientSelectionClickListener: OnIngredientSelectionClickListener,
        ingredientCheckBoxClickListener: OnIngredientCheckBoxClickListener
    ) : RecyclerView.ViewHolder(itemView) {
        var ingredientName: TextView = itemView.ingredientgroup_name
        var ingredientPriceCrowns: TextView = itemView.ingredient_group_price_crowns
        var ingredientPriceEars: TextView = itemView.ingredient_group_price_ears
        var ingredientCheckBox: CheckBox = itemView.ingredient_checkbox
        var productName: TextView = itemView.ingredient_namead


        init {
            itemView.setOnClickListener {
                ingredientSelectionClickListener.onIngredientClick(adapterPosition)
            }
        }

        init {
            ingredientCheckBox.setOnClickListener {
                ingredientCheckBoxClickListener.onCheckBoxClick(adapterPosition)
            }
        }
    }

    interface OnIngredientSelectionClickListener {
        fun onIngredientClick(position: Int)
    }

    interface OnIngredientCheckBoxClickListener {
        fun onCheckBoxClick(position: Int)
    }

}