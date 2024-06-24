package com.uvarenko.lb_3

data class BlogPost(
    val id: Int,
    val Title: String,
    val Author: String,
    val Text: String,
    val category: String,
    val comments: List<String>?
)