package com.example.flickerclient.ui.adapter

import android.arch.paging.PagedListAdapter
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

class ImagesAdapter : PagedListAdapter<Image, ImagesAdapter.ImageViewHolder>(IMAGE_COMPARATOR) {

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
            imageId.text = item.id
            Picasso.get()
                    .load(item.getUrl())
                    .resize(400, 200)
                    .centerCrop()
                    .placeholder(R.drawable.img_placeholder)
                    .into(imageImg)
        }
    }

    override fun onViewRecycled(holder: ImageViewHolder) {
        holder.itemView.imageImg.setImageBitmap(null)
    }

    companion object {
        val IMAGE_COMPARATOR = object : DiffUtil.ItemCallback<Image>() {
            override fun areItemsTheSame(p0: Image, p1: Image): Boolean {
                return p0.id == p1.id
            }

            override fun areContentsTheSame(p0: Image, p1: Image): Boolean {
                return p0 == p1
            }
        }
    }
}