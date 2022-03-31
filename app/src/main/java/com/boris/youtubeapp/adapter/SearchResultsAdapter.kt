package com.boris.youtubeapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.boris.youtubeapp.R
import com.boris.youtubeapp.model.SearchResult
import com.bumptech.glide.Glide

class SearchResultsAdapter(private val onRVItemClickListener: OnRVItemClickListener) :
    RecyclerView.Adapter<SearchResultsAdapter.SearchResultsViewHolder>() {

    var searchResultList: List<SearchResult> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return SearchResultsViewHolder(
            inflater.inflate(
                R.layout.search_result_item_layout,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SearchResultsViewHolder, position: Int) {
        val searchResult = searchResultList[position]
        holder.apply {
            searchResult.snippet.let {

                titleTv.text = it.title
                descTv.text = it.description

                Glide.with(posterIv.context).load(it.thumbnailsContainer.highThumbnail.url).into(posterIv)
                //placeholder
                // TODO: take care of error flow
                //TODO:  progress bar
            }

            itemView.setOnClickListener {
                onRVItemClickListener.onRVItemClick(searchResult)
            }
        }
    }

    override fun getItemCount(): Int = searchResultList.size

    class SearchResultsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTv: TextView = itemView.findViewById(R.id.title_tv)
        val descTv: TextView = itemView.findViewById(R.id.desc_tv)
        val posterIv: ImageView = itemView.findViewById(R.id.thumbnail_iv)
    }

    interface OnRVItemClickListener {
        fun onRVItemClick(searchResult: SearchResult)
    }

}