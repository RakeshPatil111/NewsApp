package com.example.newsapp.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.R
import com.example.newsapp.ui.MainActivity
import com.example.newsapp.ui.adapter.ArticleAdapter
import com.example.newsapp.util.Resource
import com.example.newsapp.util.shareNews
import com.example.newsapp.viewmodel.NewsViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_breaking_news.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BreakingNewsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BreakingNewsFragment : Fragment(R.layout.fragment_breaking_news) {

    lateinit var viewModel: NewsViewModel
    lateinit var newsAdapter : ArticleAdapter
    val TAG = "BreakingNewsFragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private fun setupRecyclerView() {
        newsAdapter = ArticleAdapter()
        rvBreakingNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel

        setupRecyclerView()

        newsAdapter.setOnItemCLickListener {
            val bundle = Bundle().apply {
                putSerializable("article", it)
            }
            findNavController().navigate(R.id.action_breakingNewsFragment_to_articleFragment, bundle)
        }

        newsAdapter.onSaveClickListener {
            viewModel.insertArticle(it)
            Snackbar.make(view, "Saved", Snackbar.LENGTH_SHORT).show()
        }

        newsAdapter.onDeleteClickListener {
            viewModel.deleteArticle(it)
            Snackbar.make(view, "Removed", Snackbar.LENGTH_SHORT).show()
        }

        newsAdapter.onShareNewsClick {
            shareNews( context, it)
        }

        viewModel.breakingNews.observe(viewLifecycleOwner, Observer {newsResponse ->
            when (newsResponse) {
                is Resource.Success -> {
                    shimmerFrameLayout.stopShimmerAnimation()
                    shimmerFrameLayout.visibility = View.GONE
                    //hideProgressBar()
                    newsResponse.data?.let { news ->
                        rvBreakingNews.visibility = View.VISIBLE
                        newsAdapter.differ.submitList(news.articles)
                    }
                }

                is Resource.Error -> {
                    shimmerFrameLayout.visibility = View.GONE
                    //hideProgressBar()
                    newsResponse.message?.let { message ->
                        Log.e(TAG, "Error :: $message")
                    }
                }

                is Resource.Loading -> {
                    shimmerFrameLayout.startShimmerAnimation()
                    //showProgressBar()
                }
            }
        })
    }
}
