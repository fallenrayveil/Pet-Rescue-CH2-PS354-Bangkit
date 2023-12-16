package com.example.petrescuecapstone.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.petrescuecapstone.R
import com.example.petrescuecapstone.adapter.ArticleAdapter
import com.example.petrescuecapstone.databinding.FragmentArticleBinding
import com.example.petrescuecapstone.response.DataItem
import com.example.petrescuecapstone.ui.customTab
import com.example.petrescuecapstone.viewmodel.ArticleViewModel

class ArticleFragment : Fragment() {
    private lateinit var binding: FragmentArticleBinding
    private lateinit var articleAdapter: ArticleAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentArticleBinding.inflate(layoutInflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentArticleBinding.inflate(layoutInflater)
        val homeViewModel = ViewModelProvider(this).get(ArticleViewModel::class.java)
        binding.apply {
            arrowBack.setOnClickListener {
            }
            //apply recycleView
            articleAdapter = ArticleAdapter()
            binding.rvArticle.layoutManager = LinearLayoutManager(context)
            binding.rvArticle.adapter = articleAdapter
            articleAdapter.setOnItemClickCallBack(object : ArticleAdapter.OnItemClickCallback {
                override fun onItemClicked(user: DataItem) {
                    customTab(context!!, user.url!!)
                    if (user.url != null) {
                        customTab(context!!, user.url!!)
                    }
                }
            })

            homeViewModel.setArticle()
            homeViewModel.getArticle().observe(viewLifecycleOwner) {
                binding.apply {
                    val article = it.data
                    if (article != null) {
                        articleAdapter.setList(article)
                        Log.e(context!!::class.java.simpleName, "adapter : $articleAdapter ")
                    } else {
                        Log.e(context!!::class.java.simpleName, "data not found")
                    }
                }
            }
        }
    }
}