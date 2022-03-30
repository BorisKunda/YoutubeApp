package com.boris.youtubeapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel

class YoutubeViewModel(application: Application) : AndroidViewModel(application) {

    private val youtubeRepository: YoutubeRepository = YoutubeRepository.getRepository(application)

}