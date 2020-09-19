package com.gerryjuans.template.trending.list

import android.animation.Animator
import android.animation.ValueAnimator
import android.view.View
import com.gerryjuans.template.api.GithubRepo

class TrendingListAnimHelper {

    fun measure(view: View, maxWidth: Int) {
        view.measure(
            View.MeasureSpec.makeMeasureSpec(maxWidth, View.MeasureSpec.EXACTLY),
            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        )
    }

    fun animateExpand(view: View, maxWidth: Int, item: GithubRepo) {
        measure(view, maxWidth)
        animate(view, 0, view.measuredHeight)
    }

    fun animateCollapse(view: View) {
        animate(view, view.height, 0)
    }

    private fun animate(view: View, start: Int, end: Int) {
        val isExpanding = end > start

        ValueAnimator.ofInt(start, end)
            .apply {
                duration = 100
                addUpdateListener {
                    val height = it.animatedValue as Int
                    val param = view.layoutParams
                    param.height = height
                    view.layoutParams = param
                }
                addListener(object: Animator.AnimatorListener{
                    override fun onAnimationRepeat(animation: Animator?) {}
                    override fun onAnimationEnd(animation: Animator?) {
                        view.visibility = if (isExpanding) View.VISIBLE else View.GONE
                    }
                    override fun onAnimationCancel(animation: Animator?) {}
                    override fun onAnimationStart(animation: Animator?) {
                        view.visibility = View.VISIBLE
                    }
                })
            }
            .start()
    }
}