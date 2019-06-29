package com.example.pokemongallery

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.image_item.view.*


class GalleryImageAdapter(private val itemList: List<PokemonImage>) : RecyclerView.Adapter<GalleryImageAdapter.ViewHolder>() {
    private var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryImageAdapter.ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(parent.context).inflate(R.layout.image_item, parent,
            false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: GalleryImageAdapter.ViewHolder, position: Int) {
        holder.bind()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind() {
            val image = itemList[adapterPosition]
            // load image
            Picasso.get().load(image.url).into(itemView.previewImage)
            itemView.name.text = image.name

            // adding click or tap handler for our image layout
            itemView.setOnClickListener {
                (context as MainActivity).startDetailActivity(adapterPosition)
            }
        }
    }
}