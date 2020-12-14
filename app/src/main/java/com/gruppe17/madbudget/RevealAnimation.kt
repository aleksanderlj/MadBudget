package com.gruppe17.madbudget

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.app.Activity
import android.content.Intent
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewTreeObserver
import android.view.animation.AccelerateInterpolator
import kotlin.math.max

// Credit: https://codesnipps.simolation.com/post/android/create-circular-reveal-animation-when-starting-activitys/

class RevealAnimation {

    val view: View
    val activity: Activity
    var x: Int = 0
    var y: Int = 0

    constructor(view: View, intent: Intent, activity: Activity){
        this.view = view
        this.activity = activity

        view.visibility = View.VISIBLE
        x = intent.getIntExtra("X", 0)
        y = intent.getIntExtra("Y", 0)

        val viewTreeObserver = view.viewTreeObserver

        if(viewTreeObserver.isAlive){
            viewTreeObserver.addOnGlobalLayoutListener(object: ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    revealActivity(x, y)
                    view.viewTreeObserver.removeOnGlobalLayoutListener(this)
                }
            })
        }

    }

    fun revealActivity(x: Int, y: Int){
        val finalRadius = max(view.width, view.height) * 1.1

        val circularReveal = ViewAnimationUtils.createCircularReveal(view, x, y, 0.0f, finalRadius.toFloat())
        circularReveal.interpolator = AccelerateInterpolator()
        circularReveal.duration = 500

        view.visibility = View.VISIBLE
        circularReveal.start()
    }

    fun unRevealActivity(){
        val finalRadius = max(view.width, view.height) * 1.1

        val circularReveal = ViewAnimationUtils.createCircularReveal(view, x, y, finalRadius.toFloat(), 0.0f)
        circularReveal.duration = 300
        circularReveal.addListener(object: AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                view.visibility = View.VISIBLE
                activity.finish()
                activity.overridePendingTransition(0, 0)
            }
        })

        circularReveal.start()
    }

}