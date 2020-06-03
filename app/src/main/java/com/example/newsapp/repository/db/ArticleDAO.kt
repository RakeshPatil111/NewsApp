package com.example.newsapp.repository.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.newsapp.model.Article

@Dao
interface ArticleDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(article: Article) : Long

    @Query("SELECT * FROM articles")
    fun getArticles() : LiveData<List<Article>>

    @Delete
    suspend fun deleteArticle(article: Article)
}