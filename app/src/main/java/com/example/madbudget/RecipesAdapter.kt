package com.example.madbudget

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.madbudget.models.Recipe
import kotlinx.android.synthetic.main.recipe_item.view.*

class RecipesAdapter(private val myDataset: ArrayList<Recipe>) : RecyclerView.Adapter<RecipesAdapter.RecipesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipesViewHolder {
        var itemView = LayoutInflater.from(parent.context).inflate(R.layout.recipe_item,parent,false)
        return RecipesViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecipesViewHolder, position: Int) {
        val currentItem = myDataset[position]
        holder.textView.text = currentItem.recipeName
    }

    override fun getItemCount(): Int {
        return myDataset.size
    }

    class RecipesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textView: TextView = itemView.recipe_name
        var imageView: ImageView = itemView.recipe_image
    }
}