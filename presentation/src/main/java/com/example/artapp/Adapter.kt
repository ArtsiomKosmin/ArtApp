package com.example.artapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.artapp.databinding.ArtItemBinding
import com.example.domain.models.ArtEntity

typealias FavoriteListener = (String, Boolean) -> Unit

class Adapter(
    private val favoriteListener: FavoriteListener
) : ListAdapter<ArtEntity, Adapter.Holder>(ArtDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ArtItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return Holder(binding, favoriteListener)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position))
    }

    private var onItemClickListener: ((ArtEntity) -> Unit)? = null

    override fun onBindViewHolder(
        holder: Holder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            if (payloads[0] == true) {
                holder.bindFavoriteState(getItem(position).isFavorite)
            }
        }

        holder.itemView.setOnClickListener {
            onItemClickListener?.let {
                it(getItem(position))
            }
        }
    }

    fun setOnItemClickListener(listener: (ArtEntity) -> Unit) {
        onItemClickListener = listener
    }

    class Holder(
        private val binding: ArtItemBinding,
        private val favoriteListener: FavoriteListener
    ) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var art: ArtEntity

        init {
            binding.favoriteIcon.setOnClickListener {
                favoriteListener(art.id, !it.isSelected)
            }
        }

        fun bind(art: ArtEntity) {
            this.art = art

            ImageLoader.load(binding.artIm.context, art.webImage.url, binding.artIm, binding.itemProgressBar)
            binding.titleTv.text = art.title
            binding.favoriteIcon.isSelected = art.isFavorite
        }

        fun bindFavoriteState(isFavorite: Boolean) {
            binding.favoriteIcon.isSelected = isFavorite
        }
    }

    class ArtDiffCallback : DiffUtil.ItemCallback<ArtEntity>() {
        override fun areItemsTheSame(oldItem: ArtEntity, newItem: ArtEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ArtEntity, newItem: ArtEntity): Boolean {
            return oldItem == newItem
        }

        override fun getChangePayload(oldItem: ArtEntity, newItem: ArtEntity): Any? {
            return if (oldItem.isFavorite != newItem.isFavorite) true else null
        }
    }
}