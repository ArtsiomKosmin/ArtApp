package com.example.artapp.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.artapp.R
import com.example.artapp.databinding.ArtItemBinding
import com.example.domain.entity.ArtEntity

class ArtAdapter(private val arts: ArrayList<ArtEntity>) : RecyclerView.Adapter<ArtAdapter.ArtHolder>() {
    class ArtHolder(item: View) : RecyclerView.ViewHolder(item) {
        val binding = ArtItemBinding.bind(item)
        fun bind(art: ArtEntity) = with(binding) {
            titleTv.text = art.title
            // image view
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.art_item, parent, false)
        return ArtHolder(view)
    }

    override fun getItemCount(): Int = arts.size

    override fun onBindViewHolder(holder: ArtHolder, position: Int) {
        holder.bind(arts[position])
    }
}