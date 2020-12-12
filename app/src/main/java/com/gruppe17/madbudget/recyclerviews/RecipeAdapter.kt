package com.gruppe17.madbudget.recyclerviews

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gruppe17.madbudget.R
import com.gruppe17.madbudget.models.RecipeWithIngredientSelections
import com.gruppe17.madbudget.rest.coop.model.CoopStore
import kotlinx.android.synthetic.main.item_recipe.view.*
import org.w3c.dom.Text

class RecipeAdapter(private var myDataset: List<RecipeWithIngredientSelections>, private val cellClickListener: CellClickListener) : RecyclerView.Adapter<RecipeAdapter.RecipesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipesViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_recipe,parent,false)
        return RecipesViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecipesViewHolder, position: Int) {
        val currentItem = myDataset[position]
        holder.name.text = currentItem.recipe.name
        holder.price.text = "%.2f kr,-".format(currentItem.recipe.price)
        holder.showedDistance.text = "2km"
        holder.walkingDude.setImageResource(R.drawable.walking_dude)
        holder.itemView.setOnClickListener{
            cellClickListener.onCellClickListener(currentItem)
        }
        holder.storeAddress.text = "Kollegiebakken 7"
        holder.storeClosingTimes.text = "07:00 - 21:00"

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
        val showedDistance: TextView = itemView.recipe_showed_distance
        val walkingDude: ImageView = itemView.recipe_walking_dude
        val storeAddress: TextView = itemView.store_address
        val storeClosingTimes: TextView = itemView.store_closing_times
        val storeIcon: ImageView = itemView.store_icon
    }
}