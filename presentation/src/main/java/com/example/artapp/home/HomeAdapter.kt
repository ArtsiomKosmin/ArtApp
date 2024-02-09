package com.example.artapp.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.artapp.ImageLoader
import com.example.artapp.R
import com.example.artapp.databinding.ArtItemBinding
import com.example.domain.models.ArtEntity

class HomeAdapter : ListAdapter<ArtEntity, HomeAdapter.Holder>(Comparator()) {
    class Comparator: DiffUtil.ItemCallback<ArtEntity>() {
        override fun areItemsTheSame(oldItem: ArtEntity, newItem: ArtEntity): Boolean {
            return oldItem.id == newItem.id
        }
        override fun areContentsTheSame(oldItem: ArtEntity, newItem: ArtEntity): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.art_item, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position))
    }

    class Holder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ArtItemBinding.bind(view)

        fun bind(art: ArtEntity) = with(binding) {
            titleTv.text = art.title

//            ImageLoader.load(artIm.context, art.headerImage.url, artIm)
            ImageLoader.load(artIm.context, art.webImage.url, artIm, itemProgressBar)
        }
    }




}