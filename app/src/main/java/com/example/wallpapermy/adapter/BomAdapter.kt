package com.example.wallpapermy.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.wallpapermy.R
import com.example.wallpapermy.WallpaperView
import com.example.wallpapermy.model.BomModel

class BomAdapter(val context: Context,val listBestOfMonth: ArrayList<BomModel>) : RecyclerView.Adapter<BomAdapter.BomViewHolder>() {


    inner class BomViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)
    {
        val imageBom = itemView.findViewById<ImageView>(R.id.bom_image);
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BomViewHolder {
       return BomViewHolder(
           LayoutInflater.from(context).inflate(R.layout.item_bom, parent, false)
       )
    }

    override fun onBindViewHolder(holder: BomViewHolder, position: Int) {

        Glide.with(context).load(listBestOfMonth[position].link).into(holder.imageBom)
        holder.itemView.setOnClickListener {
            val i = Intent(context,WallpaperView::class.java)
            i.putExtra("link",listBestOfMonth[position].link)
            context.startActivity(i)
        }
    }

    override fun getItemCount()= listBestOfMonth.size
}