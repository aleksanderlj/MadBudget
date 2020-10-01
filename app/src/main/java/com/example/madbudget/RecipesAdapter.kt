package com.example.madbudget

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.madbudget.models.Recipe
import kotlinx.android.synthetic.main.recipe_item.view.*

class RecipesAdapter(private val myDataset: ArrayList<Recipe>,  private val cellClickListener: CellClickListener) : RecyclerView.Adapter<RecipesAdapter.RecipesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipesViewHolder {
        var itemView = LayoutInflater.from(parent.context).inflate(R.layout.recipe_item,parent,false)
        return RecipesViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecipesViewHolder, position: Int) {
        val currentItem = myDataset[position]
        holder.name.text = currentItem.recipeName
        holder.rating.text = "Rating: " +  currentItem.recipeRating.toString() + "/5"
        holder.timeToMake.text = "Time: " + currentItem.recipeTimeToMake
        holder.itemView.setOnClickListener{
            cellClickListener.onCellClickListener()
        }
    }

    override fun getItemCount(): Int {
        return myDataset.size
    }

    class RecipesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: TextView = itemView.recipe_name
        var rating: TextView = itemView.recipe_rating
        var timeToMake: TextView = itemView.recipe_time_to_make
    }
}