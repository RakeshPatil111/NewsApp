package com.example.newsapp.ui.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.paging.PagedListAdapter
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

class ArticleAdapter : RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {

    companion object {
        private val diffUtilCallback = object : DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem.url == newItem.url
            }

            override fun areContentsTheSame(oldItem: Article, newItem: Article) : Boolean {
                return newItem.title == oldItem.title
            }
        }
    }

    inner class ArticleViewHolder(view: View) : RecyclerView.ViewHolder(view)

    val differ = AsyncListDiffer(this, diffUtilCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ArticleViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_article, parent, false
            )
        )

    override fun getItemCount() = differ.currentList.size

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        var article = differ.currentList[position]
        holder.itemView.apply {
            Glide.with(context)
                .load(article?.urlToImage)
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
            tvArtiCleTitle.text = article?.title
            tvSource.text = article?.source?.name
            tvPublished.text = article?.publishedAt
            setOnClickListener { onItemClickListener?.let { article?.let { it1 -> it(it1) } } }
            ivSave.setOnClickListener {

                if (ivSave.tag.toString().toInt() == 0) {
                    ivSave.tag = 1
                    Toast.makeText(context, "save", Toast.LENGTH_SHORT).show()
                    ivSave.setImageDrawable(resources.getDrawable(R.drawable.ic_saved))
                    onArticleSaveClick?.let {
                        if (article != null) {
                            it(article)
                        }
                    }
                }
                else {
                    ivSave.tag = 0
                    ivSave.setImageDrawable(resources.getDrawable(R.drawable.ic_save))
                    onArticleDeleteClick?.let {
                        if (article != null) {
                            it(article)
                        }
                    }
                }
            }

            ivShare.setOnClickListener {
                onShareNewsClick?.let {
                    article?.let { it1 -> it(it1) }
                }
            }
        }
    }

    override fun getItemId(position: Int) = position.toLong()

    private var onItemClickListener: ((Article) -> Unit)? = null

    private var onArticleSaveClick: ((Article) -> Unit)? = null

    private var onArticleDeleteClick: ((Article) -> Unit)? = null

    private var onShareNewsClick: ((Article) -> Unit)? = null

    private var isSaved = false

    fun setOnItemCLickListener(listener: ((Article) -> Unit)) {
        onItemClickListener = listener
    }

    fun onSaveClickListener(listener: (Article) -> Unit) {
        onArticleSaveClick = listener
    }

    fun onDeleteClickListener(listener: (Article) -> Unit) {
        onArticleDeleteClick = listener
    }

    fun onShareNewsClick(listener: (Article) -> Unit) {
        onShareNewsClick = listener
    }
}