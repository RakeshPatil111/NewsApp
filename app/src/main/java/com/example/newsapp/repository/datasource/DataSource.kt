package com.example.newsapp.repository.datasource

import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.paging.PageKeyedDataSource
import com.example.newsapp.model.Article
import com.example.newsapp.repository.service.RetrofitClient
import com.example.newsapp.util.Constants
import com.example.newsapp.viewmodel.NewsViewModel
import kotlinx.coroutines.launch

class DataSource(val viewModel: NewsViewModel) : PageKeyedDataSource<String, Article>(){
    override fun loadInitial(
        params: LoadInitialParams<String>,
        callback: LoadInitialCallback<String, Article>
    ) {

    }

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<String, Article>) {
    }

    override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<String, Article>) {
    }
}