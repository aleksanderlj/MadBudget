package com.gruppe17.madbudget

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.gruppe17.madbudget.activities.RecipeActivity
import com.gruppe17.madbudget.database.AppDatabase
import com.gruppe17.madbudget.database.DatabaseBuilder
import org.hamcrest.CoreMatchers.*
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


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
        Thread.sleep(1000)
        onView(withId(R.id.et_new_recipe_name)).perform(replaceText("Testi"))
        onView(withText("OK")).perform(click())
        Thread.sleep(1000)
        pressBack()
        Thread.sleep(1000)
        newRecipeCount = db.recipeDao().getAll().count()
        Assert.assertEquals("It worked!", recipeCount, newRecipeCount - 1)
    }

    @Test
    fun createRecipeWithIngredient(){
        var recipeCount: Int = -1
        var newRecipeCount: Int = -1
        val context: Context = InstrumentationRegistry.getInstrumentation().targetContext
        db = DatabaseBuilder.get(context)
        recipeCount = db.recipeDao().getAll().count()
        onView(withId(R.id.new_recipe_button)).perform(click())
        onView(withId(R.id.et_new_recipe_name)).perform(replaceText("Testii"))
        onView(withId(R.id.et_new_recipe_time)).perform(replaceText("30"))
        Thread.sleep(1000)
        onView(withText("OK")).perform(click())

        Thread.sleep(1000)
        onView(withId(R.id.add_ingredient_button)).perform(click())
        onView(withId(R.id.search_bar)).perform(replaceText("inTest"))
        onView(withId(R.id.ingsel_amount)).perform(replaceText("300"))
        Thread.sleep(1000)
        onView(withId(R.id.unit_spinner)).perform(click())
        onData(allOf(`is`(instanceOf(String::class.java)), `is`("ML"))).perform(click())
        onView(withId(R.id.unit_spinner)).check(matches(withSpinnerText(containsString("ML"))))
        onView(withId(R.id.btn_ingsel_search)).perform(click())

        onView(withId(R.id.ingsel_search)).perform(replaceText("Hakket oksekød"))
        onView(withId(R.id.inglist_notselected)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withText("OK")).perform(click())
        Thread.sleep(1000)
        pressBack()
        onView(withText("GEM")).perform(click())
        Thread.sleep(1000)
        pressBack()
        onView(withText("JA")).perform(click())

        newRecipeCount = db.recipeDao().getAll().count()
        Assert.assertEquals("There should be one recipe more.", recipeCount + 1, newRecipeCount)
    }
}