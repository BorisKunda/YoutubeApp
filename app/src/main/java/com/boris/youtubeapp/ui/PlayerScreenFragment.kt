package com.boris.youtubeapp.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.boris.youtubeapp.R
import com.boris.youtubeapp.model.SearchResult
import com.boris.youtubeapp.viewmodel.YoutubeViewModel


class PlayerScreenFragment : Fragment() {

    private var searchResult: SearchResult? = null
    private val youtubeViewModel: YoutubeViewModel by activityViewModels()
    private val TAG: String = PlayerScreenFragment::class.java.simpleName

    companion object {


        private const val CLICKED_SEARCH_RESULT_KEY = "CLICKED_SEARCH_RESULT_KEY"

        @JvmStatic
        fun newInstance(searchResultAsJson: String) =
            PlayerScreenFragment().apply {
                arguments = Bundle().apply {
                    putString(CLICKED_SEARCH_RESULT_KEY, searchResultAsJson)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { bundle ->
            bundle.getString(CLICKED_SEARCH_RESULT_KEY)?.let {
                searchResult = youtubeViewModel.gson.fromJson(it, SearchResult::class.java)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_player_screen_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchResult?.snippet?.let { Log.d(TAG, it.title) }
      // searchResult?.snippet.let {
      //     Log.d(TAG, it.title)
      // }
    }

}