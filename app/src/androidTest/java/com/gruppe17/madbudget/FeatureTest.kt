package com.gruppe17.madbudget

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.runner.RunWith
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import com.google.android.material.slider.Slider
import com.gruppe17.madbudget.activities.RecipeActivity
import com.gruppe17.madbudget.database.AppDatabase
import com.gruppe17.madbudget.database.DatabaseBuilder
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import java.util.regex.Matcher

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

    /*@Test
    fun mapFunctionality(){
        onView(withId(R.id.page_2)).perform(click())
        onView(withId(R.id.radius_slider_bar)).perform(swipeRight())
        val slider: ViewInteraction = onView(withId(R.id.radius_slider_bar))
        var sliderValue = getValue(slider)
        Assert.assertEquals("hej", 4000, sliderValue)
    }*/
}