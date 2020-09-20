package com.gerryjuans.template.trending.list

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gerryjuans.template.api.GithubRepo
import com.gerryjuans.template.databinding.TrendingItemBinding
import com.gerryjuans.template.util.loadImage
import com.gerryjuans.template.util.setThrottleListener
import java.text.NumberFormat
import java.util.*

class TrendingListAdapter(
    context: Context,
    private var items: List<GithubRepo>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val animHelper = TrendingListAnimHelper()
    private val elevationHeight = 4 * context.resources.displayMetrics.density;
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
            if (!item.avatar.isNullOrBlank()) { it.avatar.loadImage(item.avatar) }
            it.author.text = item.author ?: ""
            it.name.text = item.name ?: ""
            it.description.text = item.description ?: ""
            it.language.text = item.language ?: ""
            it.star.text = numberFormat.format(item.stars)
            it.fork.text = numberFormat.format(item.forks)
            it.content.visibility = if (item.expanded) View.VISIBLE else View.GONE
            adjustElevation(it, item)
            adjustHeight(it, item)
            it.itemView.setThrottleListener {
                toggle(item, it.content, it.name.measuredWidth)
                adjustElevation(it, item)
            }
        }
    }

    private fun adjustElevation(holder: Holder, item: GithubRepo) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            holder.itemView.elevation = if (item.expanded) elevationHeight else 0f
        }
    }

    private fun adjustHeight(holder: Holder, item: GithubRepo) {
        val param = holder.content.layoutParams
        if (item.expanded) {
            holder.name.post {
                animHelper.measure(holder.content, holder.name.measuredWidth)
                param.height = holder.content.measuredHeight
                holder.content.layoutParams = param
            }
        } else {
            param.height = 0
            holder.content.layoutParams = param
        }
    }

    private fun toggle(item: GithubRepo, container: ViewGroup, maxWidth: Int) {
        if (item.expanded) {
            animHelper.animateCollapse(container)
            item.expanded = false
        } else {
            collapseAllExpandedItems()
            animHelper.animateExpand(container, maxWidth, item)
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

    fun updateList(items: List<GithubRepo>) {
        this.items = items
        notifyDataSetChanged()
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