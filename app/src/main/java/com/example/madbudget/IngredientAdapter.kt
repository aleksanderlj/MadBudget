package com.example.madbudget

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.madbudget.models.Ingredient
import kotlinx.android.synthetic.main.ingredient_item_in_recipe.view.*
import org.w3c.dom.Text


class IngredientAdapter(private var myDataset: ArrayList<Ingredient>, val mOnRecipeClickListener: OnRecipeClickListener, var context: Context) : RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        var itemView = LayoutInflater.from(parent.context).inflate(R.layout.ingredient_item_in_recipe,parent,false)
        return IngredientViewHolder(itemView, mOnRecipeClickListener)
    }

     fun update(modelList:ArrayList<Ingredient>){
        myDataset = modelList
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        var currentItem = myDataset[position]
        holder.ingredientName.text = currentItem.ingredientName
        holder.ingredientAmount.text = currentItem.amount.toString()
        holder.ingredientCategory.text = currentItem.ingredientType

    }

    override fun getItemCount(): Int {
        return myDataset.size
    }

    class IngredientViewHolder(itemView: View, mOnRecipeClickListener: OnRecipeClickListener) : RecyclerView.ViewHolder(itemView) {

        var ingredientName: TextView = itemView.ingredient_name
        var ingredientAmount: TextView = itemView.ingredient_amount
        var ingredientCategory: TextView = itemView.ingredient_category

        init {
            itemView.setOnClickListener {
                mOnRecipeClickListener.onRecipeClick(adapterPosition)
            }
        }
    }

    interface OnRecipeClickListener{
        fun onRecipeClick(position: Int)
    }

    interface updateRecyclerView{
        fun update(modelList:ArrayList<Ingredient>)
    }
}
