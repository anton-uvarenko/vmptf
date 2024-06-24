const express = require('express');
const cors = require('cors');
const bodyParser = require('body-parser');
const fs = require('fs');
const path = require('path');
const { get } = require('express/lib/response');

const app = express();
const port = 8080;

app.use(cors());
app.use(bodyParser.json());

const blogDataPath = path.join(__dirname, 'data.json');

const getBlogData = () => {
  const data = fs.readFileSync(blogDataPath);
  return JSON.parse(data);
};

const saveBlogData = (data) => {
  fs.writeFileSync(blogDataPath, JSON.stringify(data, null, 2));
};

app.get('/', (req, res) => {
  const blogData = getBlogData();
  res.send(JSON.stringify(blogData));
});

app.post('/addPost', (req, res) => {
  const newPost = req.body;
  const blogData = getBlogData();
  newPost.id = blogData.length ? blogData[blogData.length - 1].id + 1 : 1; // Assign a new unique ID
  blogData.push(newPost);
  saveBlogData(blogData);
  res.status(201).send(newPost);
});

app.post('/addComment', (req, res) => {
  const { postId, comment } = req.body;
  const blogData = getBlogData();
  const post = blogData.find(p => p.id === postId);
  if (post) {
    post.comments = post.comments || [];
    post.comments.push(comment);
    saveBlogData(blogData);
    res.status(201).send('Comment added successfully');
  } else {
    res.status(404).send('Post not found');
  }
});

app.put("/updatei", (req, res) => {
  const newPostData = req.body;
  const blogData = getBlogData();

  const index = blogData.indexOf(blogData.find(b => b.id === newPostData.id));

  blogData[index] = newPostData;
  saveBlogData(blogData);
  res.status(200).send("updated successfully")

})

app.listen(port, () => {
  console.log(`Example app listening on port ${port}`);
});
