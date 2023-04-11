package com.example.washforme.utils

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import androidx.core.view.ViewCompat.startNestedScroll
import androidx.recyclerview.widget.RecyclerView
import com.example.washforme.R
import com.google.android.material.appbar.AppBarLayout

class CustomAppBarLayoutBehavior(context: Context?, attrs: AttributeSet?) : AppBarLayout.Behavior(context, attrs) {

    private var recyclerView: RecyclerView? = null

    override fun onStartNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: AppBarLayout,
        directTargetChild: View,
        target: View,
        axes: Int,
        type: Int
    ): Boolean {
        if (target is RecyclerView && target.id == R.id.itemsRecycler) {
            recyclerView = target
        }
        return super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, axes, type)
    }

    override fun onNestedPreScroll(
        coordinatorLayout: CoordinatorLayout,
        child: AppBarLayout,
        target: View,
        dx: Int,
        dy: Int,
        consumed: IntArray,
        type: Int
    ) {
        if (target is RecyclerView && target.id == R.id.itemsRecycler) {
            startNestedScroll(target, ViewCompat.SCROLL_AXIS_VERTICAL)
            super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type)
        } else {
            super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type)
        }
    }

}
