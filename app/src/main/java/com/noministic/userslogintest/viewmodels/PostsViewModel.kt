package com.noministic.userslogintest.viewmodels

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.noministic.userslogintest.R
import com.noministic.userslogintest.data.DefaultRepository
import com.noministic.userslogintest.data.models.Post.Posts
import com.noministic.userslogintest.data.models.User.UsersItem
import com.noministic.userslogintest.others.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostsViewModel @Inject constructor(
    private val defaultRepository: DefaultRepository,
    val application: Application
) :
    ViewModel() {

    private val _posts = MutableLiveData<Posts>()
    val posts: LiveData<Posts> = _posts

    private val _user = MutableLiveData<UsersItem>()
    val user: LiveData<UsersItem> = _user

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun loadPosts(userId: Int) {
        viewModelScope.launch {
            val response = defaultRepository.getUserPosts(userId)
            when (response.status) {
                Status.ERROR -> {
                    _loading.value = false
                    response.message?.let { _errorMessage.value = it }
                }
                Status.LOADING -> {
                    _loading.value = true
                }
                Status.SUCCESS -> {
                    _errorMessage.value = ""
                    _loading.value = false
                    response.data?.let {
                        _posts.value = it
                    }
                }
            }
        }
    }

    fun getUserIdFromPrefs(): Int {
        val context = application.applicationContext
        val sharedPref = context.getSharedPreferences(
            context.getString(R.string.preference_file_key),
            Context.MODE_PRIVATE
        )
        return sharedPref.getInt(context.getString(R.string.saved_user_id_key), 0)
    }

}