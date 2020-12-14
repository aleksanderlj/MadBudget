package com.gruppe17.madbudget

import com.gruppe17.madbudget.models.Ingredient
import com.gruppe17.madbudget.models.IngredientSelection
import com.gruppe17.madbudget.models.IngredientSelectionWithIngredients
import org.junit.Assert
import org.junit.Test

class UnitTests {

    @Test
    fun calculatePricesTest() {
        // Mock data
        val ingredientSelection1: IngredientSelection = IngredientSelection(
            0, "Ris", 100.0, "G", true, 0)
        val ingredientSelection2: IngredientSelection = IngredientSelection(
            0, "Mælk" , 2000.0, "ML", true, 0)
        val ingredientSelectionWithIngredientsList:
                ArrayList<IngredientSelectionWithIngredients> = ArrayList()
        val ingredients1: ArrayList<Ingredient> = ArrayList()
        val ingredients2: ArrayList<Ingredient> = ArrayList()
        ingredients1.add(Ingredient(0, "Ris", 100.0, "G", null,
            "", false, 200.00, 0, 1))
        ingredients1.add(Ingredient(0, "Ris", 100.0, "G", null,
            "", false, 100.00, 0, 1))
        ingredients2.add(Ingredient(0, "Mælk", 1000.0, "ML", null,
            "", false, 50.00, 0, 2))
        ingredients2.add(Ingredient(0, "Mælk", 1000.0, "ML", null,
            "", false, 70.00, 0, 2))
        val ingredientSelectionWithIngredients1: IngredientSelectionWithIngredients =
            IngredientSelectionWithIngredients(ingredientSelection1, ingredients1)
        val ingredientSelectionWithIngredients2: IngredientSelectionWithIngredients =
            IngredientSelectionWithIngredients(ingredientSelection2, ingredients2)
        ingredientSelectionWithIngredientsList.add(ingredientSelectionWithIngredients1)
        ingredientSelectionWithIngredientsList.add(ingredientSelectionWithIngredients2)

        // We expect the price to be 200 kr since you would pay 100 kr for the rice, and 50 kr for the milk time 2.
        Assert.assertEquals("Calculate the price for these ingredients", 200.00,
            calculatePrices(ingredientSelectionWithIngredientsList), 0.01)
    }

    // This method is a copy from RecipeActivity, however this one is changed to use mock up data.
    private fun calculatePrices(
        ingredientSelectionList: ArrayList<IngredientSelectionWithIngredients>): Double {
        var smallestPrice: Double
        var recipePrice: Double = 0.0

        for (curIngSel in ingredientSelectionList) {

            if (curIngSel.ingredientSelection.isSelected) {
                smallestPrice = Double.MAX_VALUE
                for (curIng in curIngSel.ingredients) {

                    val ingPrice = curIng.calculatePrice()
                    if (ingPrice < smallestPrice)
                        smallestPrice = ingPrice
                }
                recipePrice += smallestPrice
            }
        }
        return recipePrice
    }
}