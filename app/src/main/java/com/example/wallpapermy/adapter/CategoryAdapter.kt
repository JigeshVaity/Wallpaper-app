package com.example.wallpapermy.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.wallpapermy.CategoriesActivity
import com.example.wallpapermy.R
import com.example.wallpapermy.WallpaperView
import com.example.wallpapermy.model.BomModel
import com.example.wallpapermy.model.CategoryModel
import com.example.wallpapermy.model.PopularModel

class CategoryAdapter(val context: Context, val listCategory: ArrayList<CategoryModel>) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {


    inner class CategoryViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)
    {
        val imagecat = itemView.findViewById<ImageView>(R.id.image_cat);
        val textcat = itemView.findViewById<TextView>(R.id.text_cat);
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
       return CategoryViewHolder(
           LayoutInflater.from(context).inflate(R.layout.item_category, parent, false)
       )
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {

        holder.textcat.text= listCategory[position].name
        Glide.with(context).load(listCategory[position].link).into(holder.imagecat)
        holder.itemView.setOnClickListener {
            val i = Intent(context, CategoriesActivity::class.java)
            i.putExtra("uid",listCategory[position].id)
            i.putExtra("name",listCategory[position].name)
            context.startActivity(i)
        }
    }

    override fun getItemCount()= listCategory.size
}