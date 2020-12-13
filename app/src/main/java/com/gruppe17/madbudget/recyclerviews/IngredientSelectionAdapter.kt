package com.gruppe17.madbudget.recyclerviews

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
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
        holder.ingredientAmount.text = "${currentItem.ingredientSelection.amount.toString()} ${currentItem.ingredientSelection.unit}"
        holder.ingredientPicture.setImageDrawable(
            ContextCompat.getDrawable(
                context,
                R.drawable.brugsen
            )
        )

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

            holder.ingredientPrice.text = "%.2f kr".format(smallestPrice)
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
        var ingredientName: TextView = itemView.ingredient_namead
        var ingredientPrice: TextView = itemView.ingredient_pricead
        var ingredientAmount: TextView = itemView.ingredient_amount
        var ingredientPicture: ImageView = itemView.ingredient_store_image
        var ingredientCheckBox: CheckBox = itemView.ingredient_checkbox
        var productName: TextView = itemView.ingsel_productname


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