package com.noministic.userslogintest.viewmodels

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.noministic.userslogintest.data.DefaultRepository
import com.noministic.userslogintest.data.local.UsersDAO
import com.noministic.userslogintest.data.local.UsersDatabase
import com.noministic.userslogintest.data.remote.ApiRequestInterface
import com.noministic.userslogintest.getOrAwaitValue
import com.noministic.userslogintest.others.Constants
import junit.framework.TestCase
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@RunWith(AndroidJUnit4::class)
class LoginViewModelTest : TestCase() {
    lateinit var usersDao: UsersDAO
    lateinit var apiRequestInterface: ApiRequestInterface
    lateinit var defaultRepository: DefaultRepository

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    lateinit var loginViewModel: LoginViewModel

    @Before
    fun setup() {
        val context: Context = ApplicationProvider.getApplicationContext()
        usersDao =
            Room.inMemoryDatabaseBuilder(context, UsersDatabase::class.java)
                .allowMainThreadQueries()
                .build().usersDao()
        apiRequestInterface = Retrofit.Builder().baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(ApiRequestInterface::class.java)
        defaultRepository = DefaultRepository(apiRequestInterface, usersDao)
        loginViewModel =
            LoginViewModel(defaultRepository, ApplicationProvider.getApplicationContext())
    }

    @Test
    fun login_must_be_successful() {
        loginViewModel.login("Bret", "Sincere@april.biz")
        val result = loginViewModel.user.getOrAwaitValue()
        assertTrue(loginViewModel.loading.value == false)
        assertEquals(result.username, "Bret")
    }
}