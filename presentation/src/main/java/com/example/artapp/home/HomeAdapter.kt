package com.example.artapp.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.artapp.ImageLoader
import com.example.artapp.R
import com.example.artapp.databinding.ArtItemBinding
import com.example.domain.entity.ArtObject

class HomeAdapter : ListAdapter<ArtObject, HomeAdapter.Holder>(Comparator()) {
    class Comparator: DiffUtil.ItemCallback<ArtObject>() {
        override fun areItemsTheSame(oldItem: ArtObject, newItem: ArtObject): Boolean {
            return oldItem.id == newItem.id
        }
        override fun areContentsTheSame(oldItem: ArtObject, newItem: ArtObject): Boolean {
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

        fun bind(art: ArtObject) = with(binding) {
            titleTv.text = art.title

//            artIm.apply {
//                Glide.with(this)
//                    .load(art.webImage.url)
//                    .diskCacheStrategy(DiskCacheStrategy.ALL)
//                    .into(this)
//            }

            ImageLoader.load(artIm.context, art.webImage.url, artIm)
        }
    }




}