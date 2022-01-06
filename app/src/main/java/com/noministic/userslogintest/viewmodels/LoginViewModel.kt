package com.noministic.userslogintest.viewmodels

import android.app.Application
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.noministic.userslogintest.R
import com.noministic.userslogintest.data.DefaultRepository
import com.noministic.userslogintest.data.models.User.UsersItem
import com.noministic.userslogintest.others.Status
import com.noministic.userslogintest.ui.MainActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val defaultRepository: DefaultRepository,
    private val application: Application
) :
    ViewModel() {
    private val _user = MutableLiveData<UsersItem>()
    val user: LiveData<UsersItem> = _user
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun getUser() {
        viewModelScope.launch {
            val response = defaultRepository.getLoggedInUser()
            if (response?.status == Status.SUCCESS) {
                response.data.let { _user.value = it }
                _loading.value = false
            }
        }
    }

    fun login(username: String, userEmail: String) {
        viewModelScope.launch {
            val response = defaultRepository.getUsers()
            Log.e("NOMI", response.toString())
            when (response.status) {
                Status.ERROR -> {
                    _loading.value = false
                    response.message?.let { _errorMessage.value = it }
                }
                Status.LOADING -> {
                    _loading.value = true
                }
                Status.SUCCESS -> {
                    _loading.value = false
                    response.data?.let { data ->
                        val list = data.filter {
                            it.username == username && it.email == userEmail
                        }
                        if (list.isNotEmpty()) {
                            storeUserLocally(data[0])
                            _user.value = data[0]
                            Log.e("NOMI", "USER FOUND")
                        } else {
                            _errorMessage.value = "User Not found"
                        }
                    }
                }
            }
        }

    }

    private fun storeUserLocally(usersItem: UsersItem) {
        insertUserToDB(usersItem)
        storeUserIdToPrefs(usersItem.id)

    }


    private fun insertUserToDB(usersItem: UsersItem) {
        viewModelScope.launch {
            usersItem.loggedIn = true
            defaultRepository.insertUser(usersItem)
        }
    }

    private fun storeUserIdToPrefs(userId: Int) {
        val context = application.applicationContext
        val sharedPref = context.getSharedPreferences(
            context.getString(R.string.preference_file_key),
            Context.MODE_PRIVATE
        ) ?: return
        with(sharedPref.edit()) {
            putInt(context.getString(R.string.saved_user_id_key), userId)
            apply()
        }
    }

    fun gotoMainAcitivity() {
        val intent = Intent(application.applicationContext, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        }
        application.applicationContext.startActivity(intent)
    }
}