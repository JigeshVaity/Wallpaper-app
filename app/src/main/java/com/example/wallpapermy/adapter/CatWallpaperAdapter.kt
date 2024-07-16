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
    import com.example.wallpapermy.R
    import com.example.wallpapermy.WallpaperView
    import com.example.wallpapermy.model.BomModel
    import com.example.wallpapermy.model.CatWallpaperModel
    import com.example.wallpapermy.model.CategoryModel
    import com.example.wallpapermy.model.PopularModel
    import com.makeramen.roundedimageview.RoundedImageView

    class CatWallpaperAdapter(private val context: Context, private val listCategory: ArrayList<CatWallpaperModel>) : RecyclerView.Adapter<CatWallpaperAdapter.CategoryViewHolder>() {

        inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val imageCat = itemView.findViewById<RoundedImageView>(R.id.image_category)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
            return CategoryViewHolder(LayoutInflater.from(context).inflate(R.layout.item_wallpaper, parent, false))
        }

        override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
            val wallpaper = listCategory[position]
            Glide.with(context).load(wallpaper.link).into(holder.imageCat)
            holder.itemView.setOnClickListener {
                val intent = Intent(context, WallpaperView::class.java)
                intent.putExtra("link", wallpaper.link)
                context.startActivity(intent)
            }

        }

        override fun getItemCount() = listCategory.size

        fun updateData(newData: List<CatWallpaperModel>) {
            listCategory.clear()
            listCategory.addAll(newData)
            notifyDataSetChanged()
        }

    }
