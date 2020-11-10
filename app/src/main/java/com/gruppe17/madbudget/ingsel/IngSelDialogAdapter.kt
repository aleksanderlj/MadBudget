package com.gruppe17.madbudget.ingsel

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.gruppe17.madbudget.R
import com.gruppe17.madbudget.models.Ingredient
import kotlinx.android.synthetic.main.item_ing_sel.view.*

class IngSelDialogAdapter(
    val dataset: ArrayList<Ingredient>,
    val onDialogIngredientClickListener: OnDialogIngredientClickListener,
    val notSelected: Boolean
) :
    RecyclerView.Adapter<IngSelDialogAdapter.IngSelDialogViewHolder>(),
    Filterable
{

    var datasetFull = ArrayList<Ingredient>()

    init{
        datasetFull.addAll(dataset)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngSelDialogViewHolder {
        val item = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_ing_sel, parent, false) as View
        return IngSelDialogViewHolder(item, onDialogIngredientClickListener, notSelected)
    }

    override fun onBindViewHolder(holder: IngSelDialogViewHolder, position: Int) {
        holder.itemView.ing_name_dialog.text = dataset[position].ingredientName
        holder.itemView.ing_price_dialog.text = dataset[position].ingredientPrice.toString() + "kr"
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    class IngSelDialogViewHolder(
        itemView: View,
        onDialogIngredientClickListener: OnDialogIngredientClickListener,
        selector: Boolean
    ) : RecyclerView.ViewHolder(itemView) {

        init {
            if (selector) {
                itemView.setOnClickListener {
                    onDialogIngredientClickListener.onDialogIngredientSelect(
                        adapterPosition
                    )
                }
            } else {
                itemView.setOnClickListener {
                    onDialogIngredientClickListener.onDialogIngredientDeselect(
                        adapterPosition
                    )
                }
            }

        }
    }


    interface OnDialogIngredientClickListener {
        fun onDialogIngredientSelect(pos: Int)
        fun onDialogIngredientDeselect(pos: Int)
    }

    override fun getFilter(): Filter {
        return wordFilter
    }

    private val wordFilter = object: Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filteredList = ArrayList<Ingredient>()

            if(constraint == null || constraint.length==0){
                filteredList.addAll(datasetFull)
            } else {
                var pattern = constraint.toString().toUpperCase().trim()
                for(n in datasetFull){
                    if(n.ingredientName!!.contains(pattern)){
                        filteredList.add(n)
                    }
                }
            }

            val results = FilterResults()
            results.values = filteredList

            return results
        }

        override fun publishResults(p0: CharSequence?, results: FilterResults?) {
            dataset.clear()
            dataset.addAll(results!!.values as ArrayList<Ingredient>)
            notifyDataSetChanged()
        }

    }

    fun notifyItemRemoved(item: Ingredient){
        datasetFull.remove(item)
        notifyDataSetChanged()
    }

    fun notifyItemAdded(item: Ingredient){
        datasetFull.add(item)
        notifyDataSetChanged()
    }

}