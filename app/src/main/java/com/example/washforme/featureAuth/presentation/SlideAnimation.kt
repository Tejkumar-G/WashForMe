package com.example.washforme.featureAuth.presentation

import android.util.Log
import android.view.View


class SlideAnimation {

    companion object {
        const val DURATION = 5000L
    }

    operator fun invoke(view: View, animation: AnimationEnum) {
        when (animation) {
            AnimationEnum.SLIDE_IN -> slideIn(view)
            AnimationEnum.SLIDE_OUT -> slideOut(view)
        }
    }

    private fun slideIn(view: View) {
        view.visibility = View.VISIBLE
        view.alpha = 0f
        view.translationY = view.height.toFloat()
        view.animate()
            .alpha(1f)
            .translationY(0f)
            .setDuration(DURATION)
            .start()
    }

    private fun slideOut(view: View) {
        Log.v("value o", view.width.toFloat().toString())
        view.animate()
            .alpha(0f)
            .translationX(view.width.toFloat())
            .setDuration(DURATION)
            .withEndAction { view.visibility = View.GONE }
            .start()
    }

//    operator fun invoke(view2: View, view1: View) {
//        val viewHeight = view1.height
//
//        view1.animate().translationX(0f).setDuration(1500).start()
//
//        view2.animate().translationX(-viewHeight.toFloat()).setDuration(1500)
//            .withEndAction {
//                view2.visibility = View.GONE
//            }.start()
//    }

/*    operator fun invoke(view2: View, view1: View) {
        val slideIn = TranslateAnimation(0f, 0f, -view1.height.toFloat(), 0f)
        slideIn.duration = 1500
        view1.startAnimation(slideIn)

        val slideOut = TranslateAnimation(0f, 0f, 0f, -view2.height.toFloat())
        slideOut.duration = 1500

        slideOut.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}

            override fun onAnimationEnd(animation: Animation?) {
                view2.visibility = View.GONE
            }

            override fun onAnimationRepeat(animation: Animation?) {}
        })
        view2.startAnimation(slideOut)
    }*/

    enum class AnimationEnum {
        SLIDE_IN,
        SLIDE_OUT
    }
}