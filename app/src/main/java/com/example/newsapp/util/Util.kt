package com.example.newsapp.util

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.core.content.ContextCompat.startActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.Request
import com.bumptech.glide.request.target.SizeReadyCallback
import com.bumptech.glide.request.transition.Transition
import com.example.newsapp.model.Article
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

fun shareNews(context: Context?, article: Article) {
    val intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, article.urlToImage)
        putExtra(Intent.EXTRA_STREAM, article.urlToImage)
        putExtra(Intent.EXTRA_TITLE, article.title)
        type = "image/*"
    }
    context?.startActivity(Intent.createChooser(intent, "Share News on"))
}

fun getBitmapFromView(context: Context?, bmp: Bitmap?): Uri? {
    var bmpUri: Uri? = null
    try {
        val file = File(context?.externalCacheDir, System.currentTimeMillis().toString() + ".jpg")

        val out = FileOutputStream(file)
        bmp?.compress(Bitmap.CompressFormat.JPEG, 90, out)
        out.close()
        bmpUri = Uri.fromFile(file)

    } catch (e: IOException) {
        e.printStackTrace()
    }
    return bmpUri
}