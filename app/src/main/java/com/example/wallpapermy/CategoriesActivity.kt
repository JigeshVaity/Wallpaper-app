package com.example.wallpapermy

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.wallpapermy.adapter.BomAdapter
import com.example.wallpapermy.adapter.CatWallpaperAdapter
import com.example.wallpapermy.model.BomModel
import com.example.wallpapermy.model.CatWallpaperModel
import com.google.firebase.firestore.FirebaseFirestore

class CategoriesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_categories)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        var db: FirebaseFirestore
        var uid = intent.getStringExtra("uid")
        var name = intent.getStringExtra("name")
        var rec = findViewById<RecyclerView>(R.id.rcv_category)
        var txt_available = findViewById<TextView>(R.id.txt_available)
        var txt_cat = findViewById<TextView>(R.id.txt_cat)
        db = FirebaseFirestore.getInstance()
        db.collection("categories").document(uid!!).collection("wallpaper")
            .addSnapshotListener { value, error ->
            val listofwallpaper = arrayListOf<CatWallpaperModel>()
            val data = value?.toObjects(CatWallpaperModel::class.java)
            listofwallpaper.addAll(data!!)

                txt_cat.text = name.toString()
                txt_available.text = "${listofwallpaper.size} Wallpaper Available"
            rec.layoutManager = GridLayoutManager(this,3)
            rec.adapter = CatWallpaperAdapter(this,listofwallpaper)

        }


    }
}