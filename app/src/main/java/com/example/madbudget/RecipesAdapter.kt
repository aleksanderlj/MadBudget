package com.example.madbudget

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.madbudget.models.Recipe
import kotlinx.android.synthetic.main.recipe_item.view.*

class RecipesAdapter(private var myDataset: ArrayList<Recipe>,  private val cellClickListener: CellClickListener) : RecyclerView.Adapter<RecipesAdapter.RecipesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipesViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recipe_item,parent,false)
        return RecipesViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecipesViewHolder, position: Int) {
        val currentItem = myDataset[position]
        holder.name.text = currentItem.recipeName
        holder.rating.text = currentItem.recipeRating.toString()
        holder.timeToMake.text = currentItem.recipeTimeToMake
        holder.itemView.setOnClickListener{
            cellClickListener.onCellClickListener(currentItem)
        }
    }

    override fun getItemCount(): Int {
        return myDataset.size
    }

    fun updateResource(newDataset: ArrayList<Recipe>){
        myDataset = newDataset
    }

    class RecipesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.recipe_name
        val rating: TextView = itemView.recipe_rating
        val timeToMake: TextView = itemView.recipe_time_to_make
    }
}