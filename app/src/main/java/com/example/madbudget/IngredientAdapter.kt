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


class IngredientAdapter(private var myDataset: ArrayList<Ingredient>, val mOnRecipeAddClickListener: OnRecipeAddClickListener,
    val mOnRecipeEditClickListener: OnRecipeEditClickListener, var context: Context) : RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        var itemView = LayoutInflater.from(parent.context).inflate(R.layout.ingredient_item_in_recipe,parent,false)
        return IngredientViewHolder(itemView, mOnRecipeAddClickListener, mOnRecipeEditClickListener)
    }

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        val currentItem = myDataset[position]
        holder.ingredientName.text = currentItem.ingredientName
        // holder.ingredientAmount.text = currentItem.amount
        /*holder.ingredientCategory.text = currentItem.ingredientType

        if (currentItem == myDataset[myDataset.size - 1 ])
            holder.editButton.visibility = View.GONE
        else
            holder.editButton.visibility = View.VISIBLE

        //TODO add icons instead of text
        if (currentItem.hasBeenClicked){
            holder.deleteButton.text = "Slet"
        }else
            holder.deleteButton.text = "Tilføj ny"

         */
    }

    override fun getItemCount(): Int {
        return myDataset.size
    }

    class IngredientViewHolder(itemView: View, mOnRecipeAddClickListener: OnRecipeAddClickListener,
        mOnRecipeEditClickListener: OnRecipeEditClickListener) : RecyclerView.ViewHolder(itemView) {

        var ingredientName: TextView = itemView.ingredient_name
       // var ingredientAmount: TextView = itemView.ingredient_amount
        // var ingredientCategory: TextView = itemView.ingredient_category
       /* var deleteButton: Button = itemView.delete_button
        var editButton: Button = itemView.edit_button

        init {
           deleteButton.setOnClickListener {
                mOnRecipeAddClickListener.onRecipeAddClick(adapterPosition)
            }
        }
        init {
            editButton.setOnClickListener {
                mOnRecipeEditClickListener.onRecipeEditClick(adapterPosition)
            }
        }*/
    }

    interface OnRecipeAddClickListener{
        fun onRecipeAddClick(position: Int)
    }

    interface OnRecipeEditClickListener{
        fun onRecipeEditClick(position: Int)
    }

}
