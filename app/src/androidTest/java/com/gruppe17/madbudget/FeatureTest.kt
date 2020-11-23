package com.gruppe17.madbudget

import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.runner.RunWith
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import com.gruppe17.madbudget.activities.MainActivity
import com.gruppe17.madbudget.database.AppDatabase
import com.gruppe17.madbudget.database.DatabaseBuilder
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

@RunWith(AndroidJUnit4::class)
class FeatureTest {
    private lateinit var db: AppDatabase

    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity>
            = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun addRecipe(){
        onView(withId(R.id.recipes)).perform(click())
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

    @Test
    fun mapFunctionality(){
        onView(withId(R.id.map_button)).perform(click())
        onView(withId(R.id.radius_slider_bar)).perform(swipeRight())
        //onView(withId(R.id.radius_slider_bar)).check(value())
    }
}