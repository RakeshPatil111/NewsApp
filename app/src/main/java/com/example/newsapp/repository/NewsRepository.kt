package com.example.newsapp.repository

import com.example.newsapp.model.Article
import com.example.newsapp.repository.db.ArticleDatabase
import com.example.newsapp.repository.service.NewsApi
import com.example.newsapp.repository.service.RetrofitClient

class NewsRepository(
    val db : ArticleDatabase
) {

    suspend fun getBreakingNews(countryCode : String, pageNumber : Int) =
        RetrofitClient.api.getBreakingNews(countryCode, pageNumber)

    suspend fun getSearchNews(q : String, pageNumber: Int) =
        RetrofitClient.api.getSearchedNews(q, pageNumber)

    suspend fun upsert(article: Article) = db.getArticleDao().insert(article)

    suspend fun delete(article: Article) = db.getArticleDao().deleteArticle(article)

    fun getAllArticles() = db.getArticleDao().getArticles()
}