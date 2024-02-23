package com.example.artapp

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target

object ImageLoader {

    @SuppressLint("CheckResult")
    fun load(context: Context, imageUrl: String, imageView: ImageView, progressBar: ProgressBar, isFavorite: Boolean) {
        val requestOptions = RequestOptions()
            .transform(FitCenter()).downsample(DownsampleStrategy.CENTER_INSIDE).override(800)
        if (isFavorite) {
            requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL)
        } else {
            requestOptions.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
        }

        Glide.with(context)
            .load(imageUrl)
            .apply(requestOptions)
            .addListener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>,
                    isFirstResource: Boolean
                ): Boolean {
                    imageView.setImageResource(R.drawable.ic_error)
                    progressBar.visibility = View.GONE
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable,
                    model: Any,
                    target: Target<Drawable>?,
                    dataSource: DataSource,
                    isFirstResource: Boolean
                ): Boolean {
                    progressBar.visibility = View.GONE
                    return false
                }
            })
            .into(imageView)
    }
}