package com.gerryjuans.template.trending.placeholder

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.gerryjuans.template.databinding.TrendingPlaceholderBinding

class TrendingPlaceHolder @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val binding = TrendingPlaceholderBinding.inflate(
        LayoutInflater.from(context), this, true
    )

    init {
       binding.recyclerView.adapter = TrendingPlaceHolderAdapter()
    }

    fun show() {
        visibility = View.VISIBLE
        binding.containerShimmer.showShimmer(true)
    }

    fun hide() {
        visibility = View.GONE
        binding.containerShimmer.hideShimmer()
    }
}