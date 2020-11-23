package com.gruppe17.madbudget

import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.runner.RunWith
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.statement.UiThreadStatement.runOnUiThread
import androidx.test.platform.app.InstrumentationRegistry
import com.gruppe17.madbudget.activities.RecipeActivity
import com.gruppe17.madbudget.database.AppDatabase
import com.gruppe17.madbudget.database.DatabaseBuilder
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

@RunWith(AndroidJUnit4::class)
class FeatureTest {
    private lateinit var db: AppDatabase

    @get:Rule
    var activityRule: ActivityScenarioRule<RecipeActivity>
            = ActivityScenarioRule(RecipeActivity::class.java)

    @Test
    fun addRecipe(){
        var recipeCount: Int = -1
        var newRecipeCount: Int = -1
        val context: Context = InstrumentationRegistry.getInstrumentation().targetContext
        db = DatabaseBuilder.get(context)
        recipeCount = db.recipeDao().getAll().count()
        onView(withId(R.id.new_recipe_button)).perform(click())
        Thread.sleep(500)
        onView(withId(R.id.recipe_title)).perform(replaceText("Testi"))
        pressBack()
        Thread.sleep(500)
        onView(withText("Ja")).perform(click())
        newRecipeCount = db.recipeDao().getAll().count()
        Assert.assertEquals("It worked!", recipeCount,newRecipeCount - 1)
    }
}