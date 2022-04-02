package com.boris.youtubeapp.adapter

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.boris.youtubeapp.R
import com.boris.youtubeapp.model.SearchResult
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class SearchResultsAdapter(private val onRVItemClickListener: OnRVItemClickListener) :
    RecyclerView.Adapter<SearchResultsAdapter.SearchResultsViewHolder>() {

    var searchResultList: List<SearchResult> = mutableListOf()
    private val TAG = SearchResultsAdapter::class.java.simpleName

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
                Glide.with(posterIv.context)
                    .load(it.thumbnailsContainer.mediumThumbnail.url)
                    .listener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(
                            p0: GlideException?,
                            p1: Any?,
                            p2: Target<Drawable>?,
                            p3: Boolean
                        ): Boolean {
                            Log.e(TAG, "onLoadFailed")
                            posterIv.visibility = GONE
                            return false
                        }

                        override fun onResourceReady(
                            p0: Drawable?,
                            p1: Any?,
                            p2: Target<Drawable>?,
                            p3: DataSource?,
                            p4: Boolean
                        ): Boolean {
                            Log.i(TAG, "OnResourceReady")
                            posterIv.visibility = VISIBLE
                            return false
                        }
                    })
                    .into(posterIv)
            }
            itemView.setOnClickListener {
                onRVItemClickListener.onRVItemClick(searchResult.id.videoId)
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
        fun onRVItemClick(videoId: String)
    }

}