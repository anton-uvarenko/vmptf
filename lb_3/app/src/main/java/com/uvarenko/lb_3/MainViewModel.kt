package com.uvarenko.lb_3

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class MainViewModel : ViewModel()  {
    val blogPosts = MutableLiveData<List<BlogPost>>(emptyList())

    init {
        viewModelScope.launch {
            blogPosts.postValue(api.getBlogPosts())
        }
    }

    fun fetchBlogPosts() {
        viewModelScope.launch {
            blogPosts.postValue(api.getBlogPosts())
        }
    }

    fun addBlogPost(post: BlogPost) {
        viewModelScope.launch {
            api.addPost(post)
            blogPosts.postValue(api.getBlogPosts())
        }
    }

    fun updatePost(post: BlogPost) {
        viewModelScope.launch {
            api.updatePost(post)
            blogPosts.postValue(api.getBlogPosts())
        }
    }
}