package com.gerryjuans.template.trending.error

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.gerryjuans.template.databinding.TrendingErrorBinding
import com.gerryjuans.template.util.setThrottleListener

class ErrorView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var binding = TrendingErrorBinding.inflate(
        LayoutInflater.from(context), this, true
    )

    fun setButtonClickedListener(onClicked: () -> Unit) {
        binding.button.setThrottleListener { onClicked() }
    }
}