package com.gerryjuans.template.trending

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.gerryjuans.template.R
import com.gerryjuans.template.api.GithubRepo
import com.gerryjuans.template.databinding.TrendingItemBinding
import com.gerryjuans.template.util.loadImage
import java.text.NumberFormat
import java.util.*

class TrendingAdapter(
    context: Context,
    private var items: List<GithubRepo>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var isLoading = false
    private val primaryColor = ContextCompat.getColor(context, R.color.colorPrimary)
    private val greyColor = ContextCompat.getColor(context, R.color.grey)
    private val avatarPlaceholder = ContextCompat.getDrawable(context, R.drawable.avatar_placeholder)
    private val numberFormat = NumberFormat.getNumberInstance(Locale.ENGLISH)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = TrendingItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )

        return Holder(binding)
    }

    override fun getItemCount() = if (isLoading) 20 else items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = if (isLoading) GithubRepo() else items[position]

        (holder as Holder).let {
            if (isLoading) it.container.startShimmer() else it.container.hideShimmer()
            if (!item.avatar.isNullOrBlank()) it.avatar.loadImage(item.avatar)
            it.avatar.background = if (isLoading) avatarPlaceholder else null
            it.author.text = item.author ?: ""
            it.author.setBackgroundColor(if (isLoading) greyColor else primaryColor)
            it.name.text = item.name ?: ""
            it.name.setBackgroundColor(if (isLoading) greyColor else primaryColor)
            it.description.text = item.description ?: ""
            it.language.text = item.language ?: ""
            it.star.text = numberFormat.format(item.stars)
            it.fork.text = numberFormat.format(item.forks)
        }
    }

    fun setLoading() {
        isLoading = true
        notifyDataSetChanged()
    }

    fun setItems(items: List<GithubRepo>) {
        isLoading = false
        this.items = items
        notifyDataSetChanged()
    }

    private class Holder(
        itemBinding: TrendingItemBinding
    ) : RecyclerView.ViewHolder(itemBinding.root) {

        val container = itemBinding.root
        val avatar = itemBinding.imageAvatar
        val author = itemBinding.textAuthor
        val name = itemBinding.textName
        val description = itemBinding.textDescription
        val language = itemBinding.textLanguage
        val star = itemBinding.textStar
        val fork = itemBinding.textFork
    }
}