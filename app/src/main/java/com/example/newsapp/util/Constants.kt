package com.example.newsapp.util

import com.example.newsapp.BuildConfig


class Constants {
    companion object {
        val API_KEY: String = BuildConfig.NEWS_API_KEY
        const val BASE_URL = "https://newsapi.org/"
        const val SEARCH_TIME_DELAY = 500L
    }
}