package com.gerryjuans.template.trending

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.gerryjuans.template.R
import com.gerryjuans.template.api.GithubRepo
import com.gerryjuans.template.databinding.TrendingItemBinding
import com.gerryjuans.template.util.loadImage
import com.gerryjuans.template.util.setThrottleListener
import java.text.NumberFormat
import java.util.*

class TrendingAdapter(
    context: Context,
    private var items: List<GithubRepo>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val elevationHeight = 4 * context.resources.displayMetrics.density;
    private val avatarPlaceHolder = ContextCompat.getDrawable(context, R.drawable.placeholder_avatar)
    private val numberFormat = NumberFormat.getNumberInstance(Locale.ENGLISH)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = TrendingItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )

        return Holder(binding)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]

        (holder as Holder).let {
            if (!item.avatar.isNullOrBlank()) { it.avatar.loadImage(item.avatar, avatarPlaceHolder) }
            it.author.text = item.author ?: ""
            it.name.text = item.name ?: ""
            it.description.text = item.description ?: ""
            it.language.text = item.language ?: ""
            it.star.text = numberFormat.format(item.stars)
            it.fork.text = numberFormat.format(item.forks)
            it.content.visibility = if (item.expanded) View.VISIBLE else View.GONE
            it.itemView.setThrottleListener {
                toggle(item, it.content, it.name.measuredWidth)
                it.itemView.elevation = if (item.expanded) elevationHeight else 0f
            }
            it.itemView.elevation = if (item.expanded) elevationHeight else 0f

            val param = it.content.layoutParams
            if (item.expanded) {
                it.name.post {
                    measure(it.content, it.name.measuredWidth)
                    param.height = it.content.measuredHeight
                    it.content.layoutParams = param
                }
            } else {
                param.height = 0
                it.content.layoutParams = param
            }
        }
    }

    private fun toggle(
        item: GithubRepo,
        container: ViewGroup,
        maxWidth: Int
    ) {
        if (item.expanded) {
            animateCollapse(container)
            item.expanded = false
        } else {
            collapseAllExpandedItems()
            animateExpand(container, maxWidth, item)
            item.expanded = true
        }
    }

    private fun collapseAllExpandedItems() {
        items.forEachIndexed { index, item ->
            if (item.expanded) {
                item.expanded = false
                notifyItemChanged(index)
            }
        }
    }

    private fun animateExpand(view: View, maxWidth: Int, item: GithubRepo) {
        measure(view, maxWidth)
        animate(view, 0, view.measuredHeight)
    }

    private fun measure(view: View, maxWidth: Int) {
        view.measure(
            View.MeasureSpec.makeMeasureSpec(maxWidth, View.MeasureSpec.EXACTLY),
            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        )
    }

    private fun animateCollapse(view: View) {
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

    fun updateList(items: List<GithubRepo>) {
        this.items = items
        notifyDataSetChanged()
        items.forEachIndexed { index, item -> if (item.expanded) notifyItemChanged(index) }
    }

    private class Holder(
        itemBinding: TrendingItemBinding
    ) : RecyclerView.ViewHolder(itemBinding.root) {

        val avatar = itemBinding.imageAvatar
        val author = itemBinding.textAuthor
        val name = itemBinding.textName
        val description = itemBinding.textDescription
        val language = itemBinding.textLanguage
        val star = itemBinding.textStar
        val fork = itemBinding.textFork
        val content = itemBinding.containerContent
    }
}