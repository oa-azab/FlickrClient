package com.example.flickerclient.ui.adapter

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

class ImagesAdapter : RecyclerView.Adapter<ImagesAdapter.ImageViewHolder>() {

    private var data: List<Image> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(
                LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_image, parent, false)
        )
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) = holder.bind(data[position])

    fun swapData(data: List<Image>) {
        this.data = data
        notifyDataSetChanged()
    }

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