package com.example.flickerclient.ui.main.adapter

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

class ImagesAdapter(val callback: ImageClickedCallback) :
        PagedListAdapter<Image, ImagesAdapter.ImageViewHolder>(IMAGE_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(
                LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_image, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) = holder.bind(getItem(position)!!)

    inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        init { itemView.setOnClickListener(this) }

        fun bind(item: Image) = with(itemView) {
            Log.d("ImageViewHolder", "[bind] imageUrl= ${item.getUrl()}")
            Picasso.get()
                    .load(item.getUrl())
                    .resize(400, 200)
                    .centerCrop()
                    .placeholder(R.drawable.img_placeholder)
                    .into(imageImg)
        }

        override fun onClick(view: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION)
                callback.onImageClicked(getItem(position)!!)
        }
    }

    override fun onViewRecycled(holder: ImageViewHolder) {
        holder.itemView.imageImg.setImageBitmap(null)
    }

    interface ImageClickedCallback {
        fun onImageClicked(image: Image)
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