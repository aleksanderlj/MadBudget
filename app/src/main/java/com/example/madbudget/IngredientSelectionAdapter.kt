package com.example.madbudget

import android.content.Context
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageSwitcher
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.madbudget.models.IngredientSelection
import kotlinx.android.synthetic.main.ingredient_item_in_recipe.view.*
import org.w3c.dom.Text


class IngredientSelectionAdapter(var myDataset: ArrayList<IngredientSelection>,
                                 var context: Context,
                                 var onIngredientSelectionClickListener: OnIngredientSelectionClickListener
) : RecyclerView.Adapter<IngredientSelectionAdapter.IngredientViewHolder>()
{

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        val currentItem = myDataset[position]
        holder.ingredientName.text = currentItem.ingredientSelectionName
        holder.ingredientPrice.text = currentItem.ingredientList[0].ingredientPrice.toString()
        holder.ingredientAmount.text = currentItem.ingredientSelectionAmount
        holder.ingredientPicture.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ghetto))

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        var itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.ingredient_item_in_recipe, parent, false)
        return IngredientViewHolder(itemView, onIngredientSelectionClickListener)
    }

    override fun getItemCount(): Int {
        return myDataset.size
    }

    class IngredientViewHolder(itemView: View, onIngredientSelectionClickListener: OnIngredientSelectionClickListener) : RecyclerView.ViewHolder(itemView) {
        var ingredientName: TextView = itemView.ingredient_namead
        var ingredientPrice: TextView = itemView.ingredient_pricead
        var ingredientAmount: TextView = itemView.ingredient_amount
        var ingredientPicture: ImageView = itemView.ingredient_store_image

        init {
            itemView.setOnClickListener {
               onIngredientSelectionClickListener.onIngredientClick(adapterPosition)
            }
        }
    }

    interface OnIngredientSelectionClickListener{
        fun onIngredientClick(position: Int)
    }

}