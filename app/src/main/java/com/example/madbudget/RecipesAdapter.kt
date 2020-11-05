package com.example.madbudget

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.madbudget.models.Recipe
import com.example.madbudget.models.RecipeWithIngredientSelections
import kotlinx.android.synthetic.main.recipe_item.view.*

class RecipesAdapter(private var myDataset: List<RecipeWithIngredientSelections>,  private val cellClickListener: CellClickListener) : RecyclerView.Adapter<RecipesAdapter.RecipesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipesViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recipe_item,parent,false)
        return RecipesViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecipesViewHolder, position: Int) {
        val currentItem = myDataset[position]
        holder.name.text = currentItem.recipe.recipeName
        holder.price.text = currentItem.recipe.price.toString() + " kr,-"
        holder.timeToMake.text = currentItem.recipe.recipeTimeToMake
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