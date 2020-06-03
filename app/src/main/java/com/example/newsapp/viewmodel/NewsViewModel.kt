package com.example.newsapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.model.Article
import com.example.newsapp.model.NewsResponse
import com.example.newsapp.repository.NewsRepository
import com.example.newsapp.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel(
    val newsRepository: NewsRepository
) : ViewModel()  {

    val breakingNews : MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var brekingPageNumebr = 1
    var breakingNewsResponse : NewsResponse? = null

    val searchNews : MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var searchPageNumber = 1
    var searchNewsResponse : NewsResponse? = null

    init {
        getBreakingNews("in")
    }

    fun getBreakingNews(countryCode : String) = viewModelScope.launch {
        breakingNews.postValue(Resource.Loading())
        val response = newsRepository.getBreakingNews(countryCode, brekingPageNumebr)
        breakingNews.postValue(handleBreakingNewsResponse(response))
    }

    fun getSearchedNews(queryString : String) = viewModelScope.launch {
        searchNews.postValue(Resource.Loading())
        val searchNewsResponce = newsRepository.getSearchNews(queryString, searchPageNumber)
        searchNews.postValue(handleSearchNewsResponse(searchNewsResponce))
    }

    fun handleBreakingNewsResponse(response : Response<NewsResponse>) : Resource<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                brekingPageNumebr++
                if (breakingNewsResponse == null) {
                    breakingNewsResponse = resultResponse
                } else {
                    val oldArticles = breakingNewsResponse?.articles
                    val newArticles = resultResponse.articles
                    oldArticles?.addAll(newArticles)
                }
                return Resource.Success(breakingNewsResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    fun handleSearchNewsResponse(response : Response<NewsResponse>) : Resource<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                searchPageNumber++
                if (searchNewsResponse == null) {
                    searchNewsResponse = resultResponse
                } else {
                    val oldArticles = searchNewsResponse?.articles
                    val newArticles = resultResponse.articles
                    oldArticles?.addAll(newArticles)
                }
                return Resource.Success(searchNewsResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    fun insertArticle(article: Article) = viewModelScope.launch {
        newsRepository.upsert(article)
    }

    fun getSavedArticles() = newsRepository.getAllArticles()

    fun deleteArticle(article: Article) = viewModelScope.launch {
        newsRepository.delete(article)
    }
}