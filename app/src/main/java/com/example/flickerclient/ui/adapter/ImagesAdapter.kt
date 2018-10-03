package com.example.flickerclient.ui.adapter

import android.arch.paging.PagedListAdapter
import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.flickerclient.R
import com.example.flickerclient.data.Image
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_image.view.*
import java.util.*

class ImagesAdapter : PagedListAdapter<Image, ImagesAdapter.ImageViewHolder>(
        object : DiffUtil.ItemCallback<Image>() {
            override fun areItemsTheSame(p0: Image, p1: Image): Boolean {
                return p0.nid == p1.nid
            }

            override fun areContentsTheSame(p0: Image, p1: Image): Boolean {
                return p0 == p1
            }
        }) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(
                LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_image, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) = holder.bind(getItem(position)!!)

    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Image) = with(itemView) {
            Log.d("ImageViewHolder", "[bind] imageUrl= ${item.getUrl()}")
            Picasso.get()
                    .load(item.getUrl())
                    .placeholder(R.drawable.ic_twotone_photo)
                    .into(imageImg)
        }
    }
}