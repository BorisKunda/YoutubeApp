package com.boris.youtubeapp

import android.app.Application
import com.boris.youtubeapp.network.YoutubeController

class YoutubeRepository private constructor(application: Application) {



    companion object {

        fun getRepository(application: Application): YoutubeRepository {
            val instance: YoutubeRepository by lazy { YoutubeRepository(application) }
            return instance
        }

    }

    init {
YoutubeController.getController().getFoo()
    }

}