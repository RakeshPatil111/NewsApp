package com.example.newsapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.newsapp.repository.NewsRepository

class NewsViewModelFactory (val newsRepository: NewsRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NewsViewModel(newsRepository) as T
    }
}