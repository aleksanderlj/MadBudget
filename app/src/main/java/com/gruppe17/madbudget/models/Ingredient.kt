package com.gruppe17.madbudget.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Ingredient(
    @ColumnInfo(name = "ingredient_id")
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "ingredient_name") var name: String?,
    @ColumnInfo(name = "ingredient_amount")var amount: Double?,
    @ColumnInfo(name = "ingredient_unit")var unit: String?,
    @ColumnInfo(name = "ingredient_pieces")var pieces: Int?,
    @ColumnInfo(name = "ingredient_type") var type: String?,
    @ColumnInfo(name = "has_been_clicked") var hasBeenClicked: Boolean = false,
    @ColumnInfo(name = "ingredient_price") var price: Double?,
    @ColumnInfo(name = "ingredient_selection_parent_id") var ingredientSelectionParentId: Int
): Serializable {
    constructor(): this(0, "", null, null, null, null,false, null,0)

    override fun toString(): String {
        return "Ingredient(ingredientId=$id, ingredientName=$name, amount=$amount, unit=$unit, pieces=$pieces, ingredientType=$type, hasBeenClicked=$hasBeenClicked, ingredientPrice=$price, ingredientSelectionParentId=$ingredientSelectionParentId)"
    }

    companion object{
        fun calcIngredientPrice(ingSel: IngredientSelection, ing: Ingredient): Double{
            val amount = ingSel.amount
            val unit = ingSel.unit
            var price = ing.price!!

            when(unit){
                "STK" -> {
                    if(ing.pieces != null){
                        val piecePrice = ing.price!! / ing.pieces!!
                        price = piecePrice * amount!!

                    }
                }

                "ML", "G" -> {
                    if(ing.unit.equals(unit) && ing.amount != null){
                        val amountPrice = ing.price!! / ing.amount!!
                        price = amountPrice * amount!!
                    }
                }

                else -> {
                    price = ing.price!!
                }
            }

            return price
        }
    }


}