package demidova.materialdesign.view.coordinator

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.widget.NestedScrollView
import com.google.android.material.appbar.AppBarLayout
import kotlin.math.abs

class NestedBehavior(context: Context?=null, attr:AttributeSet?=null): CoordinatorLayout.Behavior<View>(context,attr) {

    override fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {
        return (dependency is AppBarLayout)
    }


    override fun onDependentViewChanged(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {

        val bar  = dependency as AppBarLayout
        child.y =bar.height+bar.y
        //Log.d("mylogs","${child.y} child.y")

        return super.onDependentViewChanged(parent, child, dependency)
    }
}