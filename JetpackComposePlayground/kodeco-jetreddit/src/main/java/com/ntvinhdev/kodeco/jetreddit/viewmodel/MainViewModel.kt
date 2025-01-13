package com.ntvinhdev.kodeco.jetreddit.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ntvinhdev.kodeco.jetreddit.domain.model.PostModel
import com.yourcompany.android.jetreddit.data.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository) : ViewModel() {

  val allPosts by lazy { repository.getAllPosts() }

  val myPosts by lazy { repository.getAllOwnedPosts() }

  val subreddits by lazy { MutableLiveData<List<String>>() }

  val selectedCommunity: MutableLiveData<String> by lazy { MutableLiveData<String>() }

  fun searchCommunities(searchedText: String) {
    viewModelScope.launch(Dispatchers.Default) {
      subreddits.postValue(repository.getAllSubreddits(searchedText))
    }
  }

  fun savePost(post: PostModel) {
    viewModelScope.launch(Dispatchers.Default) {
      repository.insert(post.copy(subreddit = selectedCommunity.value ?: ""))
    }
  }
}