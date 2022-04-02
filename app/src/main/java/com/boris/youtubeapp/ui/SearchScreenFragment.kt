package com.boris.youtubeapp.ui

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.boris.youtubeapp.R
import com.boris.youtubeapp.adapter.SearchResultsAdapter
import com.boris.youtubeapp.adapter.SearchResultsAdapter.OnRVItemClickListener
import com.boris.youtubeapp.utils.COUNT_DOWN_INTERVAL_MS
import com.boris.youtubeapp.utils.REQUEST_TIMEOUT_MS
import com.boris.youtubeapp.utils.TIMEOUT_ERROR_MESSAGE
import com.boris.youtubeapp.viewmodel.SearchScreenViewModel


class SearchScreenFragment : Fragment(), OnRVItemClickListener {


    private val searchScreenViewModel: SearchScreenViewModel by activityViewModels()
    private lateinit var searchResultsRV: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var searchResultsAdapter: SearchResultsAdapter
    private lateinit var searchLoaderLAV: LottieAnimationView
    private var searchStatusEnum: SearchStatusEnum = SearchStatusEnum.NON_EXISTING
    private lateinit var searchRequestTimeoutCountDownTimer: CountDownTimer
    private var isTimerRunning: Boolean = false


    private val TAG: String = SearchScreenFragment::class.java.simpleName


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search_screen_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeLiveData(searchScreenViewModel)
        setRecyclerView(view)
        setSearchView(view)
        setLottieAnimationView(view)
        setTimer()
    }

    override fun onResume() {
        super.onResume()
        if (searchStatusEnum == SearchStatusEnum.STARTED && !searchLoaderLAV.isAnimating) {
            searchLoaderLAV.playAnimation()
        } else if (searchStatusEnum == SearchStatusEnum.COMPLETED) {
            searchResultsRV.visibility = VISIBLE
        }

    }

    override fun onStop() {
        super.onStop()
        if (searchStatusEnum == SearchStatusEnum.STARTED && searchLoaderLAV.isAnimating) {
            searchLoaderLAV.pauseAnimation()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (isTimerRunning) {
            searchRequestTimeoutCountDownTimer.cancel()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        searchStatusEnum = SearchStatusEnum.NON_EXISTING
    }

    private fun observeLiveData(searchScreenViewModel: SearchScreenViewModel) {

        searchScreenViewModel.apply {
            searchResultsListLD.observe(viewLifecycleOwner) {
                searchStatusEnum = SearchStatusEnum.COMPLETED
                if (isTimerRunning) {
                    searchRequestTimeoutCountDownTimer.cancel()
                    isTimerRunning = false
                }
                searchLoaderLAV.apply {
                    pauseAnimation()
                    visibility = GONE
                }

                searchResultsRV.visibility = VISIBLE
                searchResultsAdapter.searchResultList = it
                searchResultsAdapter.notifyDataSetChanged()
            }
            searchRequestFailedSld.observe(
                viewLifecycleOwner
            ) {
                searchLoaderLAV.apply {
                    pauseAnimation()
                    visibility = GONE
                }
                if (isTimerRunning) {
                    searchRequestTimeoutCountDownTimer.cancel()
                    isTimerRunning = false
                }
                searchStatusEnum = SearchStatusEnum.NON_EXISTING
                searchResultsRV.visibility = VISIBLE
            }
            requestTimeoutSld.observe(viewLifecycleOwner) {
                Toast.makeText(context, TIMEOUT_ERROR_MESSAGE, Toast.LENGTH_SHORT).show()
                Log.e(TAG, TIMEOUT_ERROR_MESSAGE)
            }
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
                query?.let {
                    if (!isTimerRunning) {
                        searchRequestTimeoutCountDownTimer.start()
                        isTimerRunning = true
                    }
                    searchStatusEnum = SearchStatusEnum.STARTED
                    searchScreenViewModel.searchVideosByKeyword(it)
                    searchResultsRV.visibility = GONE
                    searchLoaderLAV.apply {
                        visibility = VISIBLE
                        playAnimation()
                    }
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })
    }

    private fun setLottieAnimationView(view: View) {
        searchLoaderLAV = view.findViewById(R.id.loader_lav)
    }

    private fun setTimer() {
        searchRequestTimeoutCountDownTimer =
            object : CountDownTimer(REQUEST_TIMEOUT_MS, COUNT_DOWN_INTERVAL_MS) {
                override fun onTick(millisUntilFinished: Long) {
                    isTimerRunning = true
                }

                override fun onFinish() {
                    if (searchStatusEnum == SearchStatusEnum.STARTED) {
                        searchScreenViewModel.requestTimeoutSld.call()
                    }
                    isTimerRunning = false
                }
            }
    }


    override fun onRVItemClick(videoId: String) {
        searchScreenViewModel.clickedSearchResultVideoIdSLD.value = videoId
    }

    enum class SearchStatusEnum {
        NON_EXISTING, STARTED,
        COMPLETED
    }

}