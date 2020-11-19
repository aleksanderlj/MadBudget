package com.gruppe17.madbudget.recyclerviews

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gruppe17.madbudget.R
import com.gruppe17.madbudget.models.RecipeWithIngredientSelections
import kotlinx.android.synthetic.main.item_recipe.view.*

class RecipeAdapter(private var myDataset: List<RecipeWithIngredientSelections>, private val cellClickListener: CellClickListener) : RecyclerView.Adapter<RecipeAdapter.RecipesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipesViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_recipe,parent,false)
        return RecipesViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecipesViewHolder, position: Int) {
        val currentItem = myDataset[position]
        holder.name.text = currentItem.recipe.name
        holder.price.text = "%.2f kr".format(currentItem.recipe.price)
        holder.timeToMake.text = currentItem.recipe.timeToMake
        holder.showedDistance.text = currentItem.recipe.showedDistance.toString() + " km"
        holder.walkingDude.setImageResource(R.drawable.walking_dude)
        holder.itemView.setOnClickListener{
            cellClickListener.onCellClickListener(currentItem)
        }
    }

    override fun getItemCount(): Int {
        return myDataset.size
    }

    fun updateResource(newDataset: List<RecipeWithIngredientSelections>){
        myDataset = newDataset
    }

    class RecipesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.recipe_name
        val price: TextView = itemView.recipe_price
        val timeToMake: TextView = itemView.recipe_time_to_make
        val showedDistance: TextView = itemView.recipe_showed_distance
        val walkingDude: ImageView = itemView.recipe_walking_dude
    }
}