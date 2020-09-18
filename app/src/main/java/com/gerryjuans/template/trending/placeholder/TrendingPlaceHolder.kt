package com.gerryjuans.template.trending.placeholder

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.ShimmerFrameLayout

class TrendingPlaceHolder @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ShimmerFrameLayout(context, attrs, defStyleAttr) {

    init {
        val recyclerView = RecyclerView(context).also {
            it.layoutParams = LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
            )
            it.layoutManager = object : LinearLayoutManager(context) {
                override fun canScrollVertically() = false
            }
            it.adapter = TrendingPlaceHolderAdapter()
        }

        addView(recyclerView)
    }

    fun show() {
        visibility = View.VISIBLE
        startShimmer()
    }

    fun hide() {
        visibility = View.GONE
        hideShimmer()
    }
}