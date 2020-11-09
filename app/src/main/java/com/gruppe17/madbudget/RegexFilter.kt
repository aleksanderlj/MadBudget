package com.gruppe17.madbudget

import com.gruppe17.madbudget.coop.model.CoopProduct
import com.gruppe17.madbudget.models.Ingredient

class RegexFilter {
    //TODO Lav IngredientAmount felter til var og brug setters ya dang ol dumbo
    companion object {
        val amountRegex = Regex("(([0-9]+)([,.])?([0-9]+)?(-))?([0-9]+)([,.])?([0-9]+)? ?(?<unit>KG|GRAM|G|CL|ML|LITER|L)([^A-ZÆØÅ]|$)")
        val stkXRegex = Regex("([0-9]+) ?(X|STK)")
        val caMaxMinRegex = Regex("(MIN|CA|MAX)[ .]+([0-9]+) ?(KG|GRAM|G|CL|ML|LITER|L)")
        val halfRegex = Regex("(1/2) ?(LITER|L|KG)")

        // TODO lav dette til en constructor i ingredient
        fun convertCoopIngredient(coopIng: CoopProduct): Ingredient{
            var ingAmount = parseIngredientFields(coopIng.name1, coopIng.name2)

            var id = -1
            var name = coopIng.name1 + " " + coopIng.name2
            var amount = ingAmount.totalAmount
            var unit = ingAmount.unit
            var pieces = ingAmount.pieces
            var type = null
            var hasBeenClicked = false
            var price = coopIng.price
            var parentId = -1

            return Ingredient(id, name, amount, unit, pieces, type, hasBeenClicked, price, parentId)
        }

        fun parseIngredientFields(field1: String, field2: String): IngredientAmount {
            val ing1 = parseIngredientField(field1)
            var ing2 = parseIngredientField(field1)

            if(ing2.totalAmount == null && ing1.totalAmount != null){
                ing2 = IngredientAmount(ing1.totalAmount, ing2.unit, ing2.pieces)
            }
            if(ing2.unit == null && ing1.unit != null){
                ing2 = IngredientAmount(ing2.totalAmount, ing1.unit, ing2.pieces)
            }
            if(ing2.pieces == null && ing1.pieces != null){
                ing2 = IngredientAmount(ing2.totalAmount, ing2.unit, ing1.pieces)
            }

            return ing2
        }

        fun parseIngredientField(field: String): IngredientAmount {
            //TODO husk to upper case
            val input = field.toUpperCase()
            //val input = "FLÆSKESTEG 1,2-2,4 KG".toUpperCase()
            //val input = "800-1600G"
            //val input = "8-12% 450G"
            //val input = "A38 LETMÆLK"
            //val input = "PR. 1/2 KG"
            //val input = "1/2 L ØKO"
            //val input = "CA. 610 G, MIN. 519 G"
            //val input = "FREDERIK DANBO ML  45+ CA 650G"
            //val input = "CA. 685 G MIN. 582-788 G"
            //val input = "MIN 830 G 45+ / 27 %"
            //val input = "5X28 G"
            //val input = "550 G 10 STK"
            //val input = "5 X 250 ML"
            //val input = "15 STK S/M"
            //val input = "2 STK./100 G"
            //val input = "DIS IS NUTTIN"


            var ingredientAmount = IngredientAmount(null, null, null)

            if (halfRegex.containsMatchIn(input)) {
                println("Half")
                ingredientAmount = getHalfAmount(input)
            } else if(caMaxMinRegex.containsMatchIn(input)) {
                println("Ca Min Max")
                ingredientAmount = getCaMinMaxAmount(input)
            } else if (amountRegex.containsMatchIn(input)) {
                println("Default amount")
                ingredientAmount = getDefaultAmount(input)
            }

            if(stkXRegex.containsMatchIn(input)) {
                println("Pieces")
                ingredientAmount = applyPieces(input, ingredientAmount)
            }

            ingredientAmount = convertUnits(ingredientAmount)

            println("Amount=${ingredientAmount.totalAmount}, Unit=${ingredientAmount.unit}, Pieces=${ingredientAmount.pieces}")

            return ingredientAmount
        }

        fun convertUnits(ingredientAmount: IngredientAmount): IngredientAmount{
            var newObject = ingredientAmount
            if(ingredientAmount.unit != null && ingredientAmount.totalAmount != null){
                when(ingredientAmount.unit){
                    "KG" -> {
                        newObject = IngredientAmount(ingredientAmount.totalAmount*1000, "G", ingredientAmount.pieces)
                    }
                    "CL" -> {
                        newObject = IngredientAmount(ingredientAmount.totalAmount*10, "ML", ingredientAmount.pieces)
                    }
                    "LITER", "L" -> {
                        newObject = IngredientAmount(ingredientAmount.totalAmount*1000, "ML", ingredientAmount.pieces)
                    }
                }
            }
            return newObject
        }

        fun getCaMinMaxAmount(input: String): IngredientAmount{
            val match = caMaxMinRegex.findAll(input)

            var number: Double? = null
            var unit: String? = null
            for (s in match){
                if(s.groups[1]!!.value == "CA"){
                    number = s.groups[2]!!.value.toDouble()
                    unit = s.groups[3]!!.value
                    break
                } else {
                    number = s.groups[2]!!.value.toDouble()
                    unit = s.groups[3]!!.value
                }
            }

            return IngredientAmount(number, unit, null)
        }

        fun applyPieces(input: String, ingredientAmount: IngredientAmount): IngredientAmount{
            val match = stkXRegex.find(input)
            val pieces = match!!.groups[1]!!.value.toInt()

            var newAmount = ingredientAmount.totalAmount
            if(match.groups[2]!!.value == "X"){
                newAmount = pieces * ingredientAmount.totalAmount!!
            }

            return IngredientAmount(newAmount, ingredientAmount.unit, pieces)
        }

        fun getHalfAmount(input: String): IngredientAmount {
            val match = halfRegex.find(input)
            return IngredientAmount(0.5, match!!.groups[2]!!.value, null)
        }

        fun getDefaultAmount(input: String): IngredientAmount {
            val match = amountRegex.find(input)

            var amountDouble: Double
            if (match!!.groups[2] != null) {
                // There are two numbers present
                amountDouble = getDoubleFromDigits(match.groups[2], match.groups[4])
                amountDouble += getDoubleFromDigits(match.groups[6], match.groups[8])
                amountDouble /= 2
            } else {
                // There is only one number present
                amountDouble = getDoubleFromDigits(match.groups[6], match.groups[8])
            }

            return IngredientAmount(amountDouble, match.groups[9]!!.value, null)
        }

        fun getDoubleFromDigits(digit1: MatchGroup?, digit2: MatchGroup?): Double {
            var s = digit1!!.value
            if (digit2 != null) {
                s += "." + digit2.value
            }
            return s.toDouble()
        }
    }

    data class IngredientAmount(
        val totalAmount: Double?,
        val unit: String?,
        val pieces: Int?
    )
}