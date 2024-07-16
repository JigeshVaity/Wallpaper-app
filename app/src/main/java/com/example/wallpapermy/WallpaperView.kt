package com.example.wallpapermy

import android.app.WallpaperManager
import android.content.ContentValues
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.io.File
import java.io.OutputStream
import java.net.URL
import java.util.Objects
import kotlin.random.Random

class WallpaperView : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_wallpaper_view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val wallpaper = findViewById<ImageView>(R.id.finalwallpaperpage)
        val downloadwallpaper = findViewById<ImageView>(R.id.downloadarrow)
        val setwallpapaer = findViewById<ImageView>(R.id.setwallpaper)
        val sharebtn = findViewById<ImageView>(R.id.sharebtn)
        val backpress = findViewById<ImageView>(R.id.backarrow)


        val url = intent.getStringExtra("link")
        val urlImage = URL(url)

        Glide.with(this)
            .load(url)
            .into(wallpaper)

        downloadwallpaper.setOnClickListener {

            val result: Deferred<Bitmap?> = GlobalScope.async {
                urlImage.toBitmap()
            }
            GlobalScope.launch(Dispatchers.Main) {
                saveImage(result.await())
            }
        }


        setwallpapaer.setOnClickListener {

            val result: Deferred<Bitmap?> = GlobalScope.async {
                urlImage.toBitmap()
            }

            GlobalScope.launch(Dispatchers.Main) {
                val wallpapermanager = WallpaperManager.getInstance(applicationContext)
                wallpapermanager.setBitmap(result.await())
                Toast.makeText(this@WallpaperView, "Wallpaper set", Toast.LENGTH_SHORT).show()
            }
        }

    }

        fun URL.toBitmap(): Bitmap? {
            return try {
                BitmapFactory.decodeStream(openStream())
            } catch (e: Exception) {
                null
            }
        }

    private fun saveImage(bitmap: Bitmap?) {
        val random1 = Random.nextInt(520985)
        val random2 = Random.nextInt(952663)
        val name = "Wallpaper_${random1 + random2}.jpg"

        try {
            val resolver = contentResolver
            val contentValues = ContentValues().apply {
                put(MediaStore.Images.Media.DISPLAY_NAME, name)
                put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
                put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + File.separator + "Wallpapers")
            }

            val imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
            imageUri?.let { uri ->
                resolver.openOutputStream(uri)?.use { outputStream ->
                    bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                    Toast.makeText(this, "Wallpaper saved", Toast.LENGTH_SHORT).show()
                }
            } ?: run {
                Toast.makeText(this, "Failed to save wallpaper", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Toast.makeText(this, "An error occurred: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }



}