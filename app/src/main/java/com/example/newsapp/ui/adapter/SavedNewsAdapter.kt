package com.example.newsapp.ui.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.newsapp.R
import com.example.newsapp.databinding.ItemSavedNewsBinding
import com.example.newsapp.model.Article
import kotlinx.android.synthetic.main.item_article.view.*

class SavedNewsAdapter : RecyclerView.Adapter<SavedNewsAdapter.SavedNewsViewHolder>() {

    inner class SavedNewsViewHolder(var view: ItemSavedNewsBinding) : RecyclerView.ViewHolder(view.root)

    private val diffUtilCallback = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article) =
            oldItem.url == newItem.url

        override fun areContentsTheSame(oldItem: Article, newItem: Article) =
            oldItem.id == newItem.id
    }

    val differ = AsyncListDiffer(this, diffUtilCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : SavedNewsViewHolder{
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ItemSavedNewsBinding>(
            inflater, R.layout.item_saved_news, parent, false
        )
        return SavedNewsViewHolder(view)
    }

    override fun getItemCount() = differ.currentList.size

    override fun onBindViewHolder(holder: SavedNewsViewHolder, position: Int) {
        var article = differ.currentList[position]
        holder.view.article = article
        // Item CLick Listener
        //Bind these click listeners later

        holder.itemView.setOnClickListener {
            onItemClickListener?.let {
                article.let { article ->
                    it(article)
                }
            }
        }

        holder.view.ivShare.setOnClickListener {
            onShareNewsClick?.let {
                article?.let { it1 -> it(it1) }
            }
        }
    }

    private var onItemClickListener: ((Article) -> Unit)? = null

    private var onShareNewsClick: ((Article) -> Unit)? = null

    fun setOnItemCLickListener(listener: ((Article) -> Unit)) {
        onItemClickListener = listener
    }

    fun onShareNewsClick(listener: (Article) -> Unit) {
        onShareNewsClick = listener
    }
}