package com.uvarenko.lb_3

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class MainViewModel:ViewModel(), CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + SupervisorJob()

    val blogPosts = MutableLiveData<List<BlogPost>>(emptyList())

    init {
        launch {
            blogPosts.postValue(api.getBlogPosts())
        }
    }

    fun fetchBlogPosts() {
        launch {
            blogPosts.postValue(api.getBlogPosts())
        }
    }

    fun addBlogPost(post: BlogPost) {
        launch {
            api.addPost(post)
            blogPosts.postValue(api.getBlogPosts())
        }
    }

    fun updatePost(post: BlogPost) {
        launch {
            api.updatePost(post)
            blogPosts.postValue(api.getBlogPosts())
        }
    }
}