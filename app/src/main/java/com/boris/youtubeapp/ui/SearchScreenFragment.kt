package com.boris.youtubeapp.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.boris.youtubeapp.R
import com.boris.youtubeapp.repository.YoutubeRepository
import com.boris.youtubeapp.viewmodel.YoutubeViewModel


class SearchScreenFragment : Fragment() {

    private val youtubeViewModel: YoutubeViewModel by activityViewModels()
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

        youtubeViewModel.searchVideosByKeyword("Eminem")
        observeLiveData(youtubeViewModel)

    }

    private fun observeLiveData(youtubeViewModel: YoutubeViewModel) {

        youtubeViewModel.searchResultsListLD.observe(viewLifecycleOwner) {
            Log.i(TAG, it.toString())
        }

    }

}