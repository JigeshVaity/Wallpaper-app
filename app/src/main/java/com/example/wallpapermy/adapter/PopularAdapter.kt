package com.example.wallpapermy.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.wallpapermy.R
import com.example.wallpapermy.WallpaperView
import com.example.wallpapermy.model.BomModel
import com.example.wallpapermy.model.PopularModel

class PopularAdapter(val context: Context, val listPopularMonth: ArrayList<PopularModel>) : RecyclerView.Adapter<PopularAdapter.BomViewHolder>() {


    inner class BomViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)
    {
        val imagePopular = itemView.findViewById<ImageView>(R.id.pol_image);
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BomViewHolder {
       return BomViewHolder(
           LayoutInflater.from(context).inflate(R.layout.item_popular, parent, false)
       )
    }

    override fun onBindViewHolder(holder: BomViewHolder, position: Int) {

        Glide.with(context).load(listPopularMonth[position].link).into(holder.imagePopular)
        holder.itemView.setOnClickListener {
            val i = Intent(context, WallpaperView::class.java)
            i.putExtra("link",listPopularMonth[position].link)
            context.startActivity(i)
        }
    }

    override fun getItemCount()= listPopularMonth.size
}