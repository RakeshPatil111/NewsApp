package com.example.newsapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.newsapp.R
import com.example.newsapp.repository.NewsRepository
import com.example.newsapp.repository.db.ArticleDatabase
import com.example.newsapp.repository.service.NewsApi
import com.example.newsapp.viewmodel.NewsViewModel
import com.example.newsapp.viewmodel.NewsViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var viewModel : NewsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val newsRepository = NewsRepository(ArticleDatabase(this))
        val viewModelProvider = NewsViewModelFactory(newsRepository)
        viewModel = ViewModelProvider(this, viewModelProvider).get(NewsViewModel::class.java)

        bottomNavigationView.setupWithNavController(newsFragment.findNavController())
    }
}
