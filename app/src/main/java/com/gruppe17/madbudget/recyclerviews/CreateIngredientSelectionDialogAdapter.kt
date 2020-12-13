package com.gruppe17.madbudget.recyclerviews

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.gruppe17.madbudget.R
import com.gruppe17.madbudget.models.Ingredient
import kotlinx.android.synthetic.main.item_dialog_ing_sel.view.*
import kotlinx.android.synthetic.main.item_ingredient_search.view.*

class CreateIngredientSelectionDialogAdapter(
    val dataset: ArrayList<Ingredient>,
    val notSelected: Boolean
) :
    RecyclerView.Adapter<CreateIngredientSelectionDialogAdapter.IngSelDialogViewHolder>(),
    Filterable
{

    var datasetFull = ArrayList<Ingredient>()

    init{
        datasetFull.addAll(dataset)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngSelDialogViewHolder {
        val item = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_ingredient_search, parent, false) as View
        return IngSelDialogViewHolder(item, notSelected, dataset)
    }

    override fun onBindViewHolder(holder: IngSelDialogViewHolder, position: Int) {
        val priceDouble = dataset[position].price!!
        val indexOfDecimal = priceDouble.toString().indexOf(".")
        val priceInt = priceDouble.toInt()
        var priceDecimal = priceDouble.toString().substring(indexOfDecimal)
        if(priceDecimal.length == 2){
            priceDecimal += "0"
        } else if(priceDecimal.length > 3) {
            priceDecimal = priceDecimal.substring(0, 2)
        }

        holder.itemView.ing_name.text = dataset[position].name
        holder.itemView.ing_price.text = priceInt.toString()
        holder.itemView.ing_price_decimal.text = priceDecimal

        holder.itemView.ing_selected_amount.text = dataset[position].selectedAmount.toString()
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    class IngSelDialogViewHolder(
        itemView: View,
        selector: Boolean,
        dataset: ArrayList<Ingredient>
    ) : RecyclerView.ViewHolder(itemView) {


        init {
            /*
            if (selector) {
                itemView.setOnClickListener {
                    onDialogIngredientClickListener.onDialogIngredientSelect(adapterPosition)
                }
            } else {
                itemView.setOnClickListener {
                    onDialogIngredientClickListener.onDialogIngredientDeselect(adapterPosition)
                }
            }

             */

            itemView.ing_add.setOnClickListener{
                dataset[adapterPosition].selectedAmount++
                itemView.ing_selected_amount.text = dataset[adapterPosition].selectedAmount.toString()
            }

            itemView.ing_subtract.setOnClickListener{
                if(dataset[adapterPosition].selectedAmount > 0) {
                    dataset[adapterPosition].selectedAmount--
                    itemView.ing_selected_amount.text =
                        dataset[adapterPosition].selectedAmount.toString()
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
                    if(n.name!!.contains(pattern)){
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

    fun notifyDataSetChanged(dataset: ArrayList<Ingredient>){
        datasetFull.clear()
        datasetFull.addAll(dataset)
        notifyDataSetChanged()
    }

}