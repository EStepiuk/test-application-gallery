package com.yevhen_stepiuk.testgallery.presentation.main.main

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.yevhen_stepiuk.testgallery.R
import icepick.Icepick
import icepick.State
import kotlinx.android.synthetic.main.photo_cell.view.*
import javax.inject.Inject


class PhotosAdapter @Inject
internal constructor() : RecyclerView.Adapter<PhotosAdapter.PhotoVH>() {

    @JvmField
    @State
    internal var items = arrayListOf<PhotoVM>()

    fun addItems(newItems: List<PhotoVM>, position: Int = 0) {
        items.addAll(position, newItems)
        notifyItemRangeInserted(position, newItems.size)
    }

    fun saveInstanceState(outState: Bundle?) {
        Icepick.saveInstanceState(this, outState)
    }

    fun restoreInstanceState(savedState: Bundle?) {
        Icepick.restoreInstanceState(this, savedState)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int) =
            PhotoVH(LayoutInflater.from(parent?.context).inflate(R.layout.photo_cell, parent, false))

    override fun onBindViewHolder(holder: PhotoVH?, position: Int) {
        if (holder == null) throw IllegalArgumentException("ViewHolder must not be null")
        val item = items[position]
        with(holder) {
            Glide.with(holder.itemView.context).load(item.photoUriString).into(imgPhoto)
            tvWeather.text = item.weather
            tvAddress.text = item.address
        }
    }

    override fun getItemCount() = items.size

    class PhotoVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal val imgPhoto = itemView.imgPhoto
        internal val tvWeather = itemView.tvWeather
        internal val tvAddress = itemView.tvAddress
    }
}