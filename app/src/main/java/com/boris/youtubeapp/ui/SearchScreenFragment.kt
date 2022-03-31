package com.boris.youtubeapp.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView

import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.boris.youtubeapp.R
import com.boris.youtubeapp.adapter.SearchResultsAdapter
import com.boris.youtubeapp.adapter.SearchResultsAdapter.OnRVItemClickListener
import com.boris.youtubeapp.model.SearchResult
import com.boris.youtubeapp.repository.YoutubeRepository
import com.boris.youtubeapp.viewmodel.YoutubeViewModel


class SearchScreenFragment : Fragment(), OnRVItemClickListener {

    // TODO:
    // 1.Add dividers to RV

    private val youtubeViewModel: YoutubeViewModel by activityViewModels()
    private lateinit var searchResultsRV: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var searchResultsAdapter: SearchResultsAdapter
    private val TAG = SearchScreenFragment::class.java.simpleName

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_screen_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeLiveData(youtubeViewModel)
        setRecyclerView(view)
        setSearchView(view)
    }

    private fun observeLiveData(youtubeViewModel: YoutubeViewModel) {

        youtubeViewModel.searchResultsListLD.observe(viewLifecycleOwner) {
            searchResultsAdapter.searchResultList = it
            searchResultsAdapter.notifyDataSetChanged()
        }

    }

    private fun setRecyclerView(view: View) {
        searchResultsAdapter = SearchResultsAdapter(this)
        searchResultsRV = view.findViewById(R.id.search_results_rv)
        searchResultsRV.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(view.context)
            adapter = searchResultsAdapter
        }
    }

    private fun setSearchView(view: View) {
        searchView = view.findViewById(R.id.youtube_sv)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { youtubeViewModel.searchVideosByKeyword(it) }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // TODO: add functionality
                Log.d(TAG, "textChanged")
                return true
            }

        })
    }


    override fun onRVItemClick(searchResult: SearchResult) {

    }


}