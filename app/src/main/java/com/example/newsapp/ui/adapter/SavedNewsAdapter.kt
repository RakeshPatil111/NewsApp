package com.example.newsapp.ui.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.newsapp.R
import com.example.newsapp.model.Article
import kotlinx.android.synthetic.main.item_article.view.*

class SavedNewsAdapter : RecyclerView.Adapter<SavedNewsAdapter.SavedNewsViewHolder>() {
    inner class SavedNewsViewHolder(view: View) : RecyclerView.ViewHolder(view)

    private val diffUtilCallback = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article) =
            oldItem.url == newItem.url

        override fun areContentsTheSame(oldItem: Article, newItem: Article) =
            oldItem.id == newItem.id
    }

    val differ = AsyncListDiffer(this, diffUtilCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        SavedNewsViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_saved_news, parent, false
            )
        )

    override fun getItemCount() = differ.currentList.size

    override fun onBindViewHolder(holder: SavedNewsViewHolder, position: Int) {
        var article = differ.currentList[position]
        holder.itemView.apply {
            Glide.with(context)
                .load(article.urlToImage)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        holder.itemView.ivLoading.visibility = View.VISIBLE
                        return true
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        holder.itemView.ivLoading.visibility = View.GONE
                        return false
                    }

                }
                )
                .into(ivArticle)
            tvArtiCleTitle.text = article.title
            tvSource.text = article.source?.name
            tvPublished.text = article.publishedAt
            setOnClickListener { onItemClickListener?.let { it(article) } }
        }
    }

    private var onItemClickListener: ((Article) -> Unit)? = null


    fun setOnItemCLickListener(listener: ((Article) -> Unit)) {
        onItemClickListener = listener
    }
}