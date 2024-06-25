package com.uvarenko.lb_3

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.uvarenko.lb_3.ui.theme.Lb_3Theme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Lb_3Theme {
                val viewModel = viewModel<MainViewModel>()

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val data by viewModel.blogPosts.observeAsState()
                    BlogApp(data ?: emptyList(), {
                        viewModel.addBlogPost(it)
                    }, {
                        viewModel.updatePost(it)
                    })
                }
            }
        }
    }
}

@Composable
fun BlogApp(
    blogs: List<BlogPost>,
    addBlogPost: (BlogPost) -> Unit,
    updateBlogPost: (BlogPost) -> Unit
) {
    var selectedCategory by remember { mutableStateOf("All") }
    var showAddUpdateScreen by remember { mutableStateOf(false) }
    var currentPost by remember { mutableStateOf<BlogPost?>(null) }

    if (showAddUpdateScreen) {
        AddUpdatePostScreen(
            post = currentPost,
            addBlogPost = {
                showAddUpdateScreen = false
                addBlogPost(it)
            },
            updateBlogPost = {
                showAddUpdateScreen = false
                updateBlogPost(it)
            }
        )
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            CategoryDropdownMenu(selectedCategory) { newCategory ->
                selectedCategory = newCategory
            }
            Button(onClick = {
                currentPost = null
                showAddUpdateScreen = true
            }) {
                Text("Add Post")
            }
            BlogList(
                blogs = if (selectedCategory == "All") blogs else blogs.filter { it.category == selectedCategory },
                showUpdateScreen = { post ->
                    currentPost = post
                    showAddUpdateScreen = true
                }
            )
        }
    }
}

@Composable
fun BlogList(blogs: List<BlogPost>, showUpdateScreen: (BlogPost) -> Unit) {
    LazyColumn {
        items(blogs.size) { blog ->
            BlogItem(blogs[blog], showUpdateScreen)
        }
    }
}

@Composable
fun BlogItem(blog: BlogPost, showUpdateScreen: (BlogPost) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(8.dp)
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = blog.Title, style = MaterialTheme.typography.headlineMedium)
            Text(text = "Author: ${blog.Author}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Category: ${blog.category}", style = MaterialTheme.typography.bodySmall)
            Text(text = blog.Text, style = MaterialTheme.typography.bodySmall)
            Spacer(modifier = Modifier.height(8.dp))
            Divider()
        }
        Button(
            modifier = Modifier.align(Alignment.CenterVertically),
            onClick = { showUpdateScreen(blog) }) {
            Text("Update post")
        }
    }
}

@Composable
fun CategoryDropdownMenu(
    selectedCategory: String,
    onCategorySelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val categories = listOf("All", "Tech", "Health", "Education", "Finance")

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = !expanded },
                color = Color.LightGray
            ) {
                BasicText(
                    text = selectedCategory,
                    modifier = Modifier.padding(16.dp)
                )
            }
            if (expanded) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    categories.forEach { category ->
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    onCategorySelected(category)
                                    expanded = false
                                },
                            color = Color.White
                        ) {
                            BasicText(
                                text = category,
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AddUpdatePostScreen(
    post: BlogPost? = null,
    addBlogPost: (BlogPost) -> Unit,
    updateBlogPost: (BlogPost) -> Unit
) {
    var title by remember { mutableStateOf(post?.Title ?: "") }
    var author by remember { mutableStateOf(post?.Author ?: "") }
    var text by remember { mutableStateOf(post?.Text ?: "") }
    var category by remember { mutableStateOf(post?.category ?: "") }

    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            label = { Text("title") },
            singleLine = true
        )
        OutlinedTextField(
            value = author,
            onValueChange = { author = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            label = { Text("author") },
            singleLine = true
        )
        OutlinedTextField(
            value = category,
            onValueChange = { category = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            label = { Text("category") },
            singleLine = true
        )
        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .height(200.dp),
            label = { Text("text") },
        )
        Button(onClick = {
            if (post == null) {
                addBlogPost(BlogPost(0, title, author, text, category, emptyList()))
            } else {
                updateBlogPost(BlogPost(post.id, title, author, text, category, emptyList()))
            }
        }) {
            Text("Save")
        }
    }
}