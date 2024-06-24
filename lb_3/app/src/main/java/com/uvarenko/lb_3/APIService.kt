package com.uvarenko.lb_3

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT


interface ApiService {
    @GET("/")
    suspend fun getBlogPosts(): List<BlogPost>

    @POST("/addPost")
    suspend fun addPost(@Body post: BlogPost)

    @PUT("/updatei")
    suspend fun updatePost(@Body post: BlogPost)
}