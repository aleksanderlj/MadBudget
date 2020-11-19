package com.gruppe17.madbudget

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.runner.RunWith
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.gruppe17.madbudget.activities.MainActivity
import com.gruppe17.madbudget.models.Recipe
import org.junit.Rule
import org.junit.Test

@RunWith(AndroidJUnit4::class)
class FeatureTest {

    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity>
            = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun addRecipe(){
        onView(withId(R.id.recipes)).perform(click())
        Thread.sleep(500)
        onView(withId(R.id.new_recipe_button)).perform(click())
        Thread.sleep(500)
        onView(withId(R.id.recipe_title)).perform(replaceText("Testi"))
        pressBack()
        Thread.sleep(500)
        onView(withText("Ja")).perform(click())

    }
}