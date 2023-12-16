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
import com.faltenreich.skeletonlayout.Skeleton
import com.faltenreich.skeletonlayout.applySkeleton

class ArticleFragment : Fragment() {
    private lateinit var binding: FragmentArticleBinding
    private lateinit var articleAdapter: ArticleAdapter
    private lateinit var skeleton: Skeleton
    private lateinit var articleViewModel: ArticleViewModel

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
        initUI()
        observeViewModel()
    }

    private fun initUI() {
        articleViewModel = ViewModelProvider(this).get(ArticleViewModel::class.java)

        binding.arrowBack.setOnClickListener {
            requireActivity().onBackPressed()
        }

        articleAdapter = ArticleAdapter()
        binding.rvArticle.layoutManager = LinearLayoutManager(requireContext())
        binding.rvArticle.adapter = articleAdapter

        articleAdapter.setOnItemClickCallBack(object : ArticleAdapter.OnItemClickCallback {
            override fun onItemClicked(user: DataItem) {
                customTab(requireContext(), user.url!!)
            }
        })

        articleViewModel.setArticle()

        skeleton = binding.rvArticle.applySkeleton(R.layout.item_list_article)
        skeleton.showSkeleton()
    }

    private fun observeViewModel() {
        articleViewModel.getArticle().observe(viewLifecycleOwner) { response ->
            if (response != null) {
                val articles = response.data
                if (articles != null) {
                    articleAdapter.setList(articles)
                    skeleton.showOriginal()
                } else {
                    Log.e(this::class.java.simpleName, "Data not found")
                }
            } else {
                Log.e(this::class.java.simpleName, "Response is null")
            }
        }
    }
}
