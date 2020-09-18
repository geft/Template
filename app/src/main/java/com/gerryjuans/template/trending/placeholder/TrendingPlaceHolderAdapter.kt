package com.gerryjuans.template.trending.placeholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gerryjuans.template.databinding.TrendingItemPlaceholderBinding

class TrendingPlaceHolderAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = TrendingItemPlaceholderBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        
        return Holder(binding)
    }

    override fun getItemCount() = 20

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {}
    
    private class Holder(binding: TrendingItemPlaceholderBinding) : RecyclerView.ViewHolder(binding.root)
}