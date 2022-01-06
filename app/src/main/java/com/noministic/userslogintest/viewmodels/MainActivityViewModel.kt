package com.noministic.userslogintest.viewmodels

import android.app.Application
import android.content.Context
import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.noministic.userslogintest.R
import com.noministic.userslogintest.data.DefaultRepository
import com.noministic.userslogintest.others.Status
import com.noministic.userslogintest.ui.LoginActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    val defaultRepository: DefaultRepository,
    val application: Application
) : ViewModel() {
    private val _userLoggedOut = MutableLiveData<Boolean>()
    val userLoggedOut: LiveData<Boolean> = _userLoggedOut

    fun logOut() {
        viewModelScope.launch {
            val userId = getUserIdFromPrefs()
            if (userId > 0) {
                val result = defaultRepository.getLoggedInUser()
                if (result?.status == Status.SUCCESS) {
                    result.data?.let {
                        removeUserFromPrefs()
                        defaultRepository.removeUser(it.id)
                        _userLoggedOut.value = true
                    }
                } else
                    _userLoggedOut.value = true
            } else
                _userLoggedOut.value = true
        }
    }

    private fun getUserIdFromPrefs(): Int {
        val context = application.applicationContext
        val sharedPref = context.getSharedPreferences(
            context.getString(R.string.preference_file_key),
            Context.MODE_PRIVATE
        )
        return sharedPref.getInt(context.getString(R.string.saved_user_id_key), 0)
    }

    private fun removeUserFromPrefs() {
        val context = application.applicationContext
        val sharedPref = context.getSharedPreferences(
            context.getString(R.string.preference_file_key),
            Context.MODE_PRIVATE
        )
        sharedPref.edit().remove(application.getString(R.string.preference_file_key)).apply()
    }

    fun gotoLoginActivity() {
        val intent = Intent(application.applicationContext, LoginActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        }
        application.applicationContext.startActivity(intent)
    }
}