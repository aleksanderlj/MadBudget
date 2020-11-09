package com.gruppe17.madbudget.ingsel

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gruppe17.madbudget.R
import com.gruppe17.madbudget.models.Ingredient
import kotlinx.android.synthetic.main.item_ing_sel.view.*

class IngSelDialogAdapter(val dataset: List<Ingredient>):
    RecyclerView.Adapter<IngSelDialogAdapter.IngSelDialogViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngSelDialogViewHolder {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.item_ing_sel, parent, false) as View
        return IngSelDialogViewHolder(item)
    }

    override fun onBindViewHolder(holder: IngSelDialogViewHolder, position: Int) {
        holder.itemView.ing_name_dialog.text = dataset[position].ingredientName
        holder.itemView.ing_price_dialog.text = dataset[position].ingredientPrice.toString()
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    class IngSelDialogViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
    }

}