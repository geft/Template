package com.gerryjuans.template.trending

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gerryjuans.template.R
import com.gerryjuans.template.api.GithubRepo
import com.gerryjuans.template.databinding.TrendingItemBinding
import com.gerryjuans.template.util.loadImage
import java.text.NumberFormat
import java.util.*

class TrendingAdapter(
    private var items: List<GithubRepo>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

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
            if (!item.avatar.isNullOrBlank()) {
                it.avatar.loadImage(item.avatar, R.drawable.placeholder_avatar)
            }
            it.author.text = item.author ?: ""
            it.name.text = item.name ?: ""
            it.description.text = item.description ?: ""
            it.language.text = item.language ?: ""
            it.star.text = numberFormat.format(item.stars)
            it.fork.text = numberFormat.format(item.forks)
        }
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
    }
}