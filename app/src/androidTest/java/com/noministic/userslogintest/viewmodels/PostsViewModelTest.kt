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
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@RunWith(AndroidJUnit4::class)
class PostsViewModelTest : TestCase() {

    lateinit var usersDao: UsersDAO
    lateinit var apiRequestInterface: ApiRequestInterface
    lateinit var defaultRepository: DefaultRepository

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    lateinit var postsViewModel: PostsViewModel

    @Before
    fun setup() {
        val context: Context = ApplicationProvider.getApplicationContext()
        usersDao =
            Room.inMemoryDatabaseBuilder(context, UsersDatabase::class.java).build().usersDao()
        apiRequestInterface = Retrofit.Builder().baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(ApiRequestInterface::class.java)
        defaultRepository = DefaultRepository(apiRequestInterface, usersDao)
        postsViewModel =
            PostsViewModel(defaultRepository, ApplicationProvider.getApplicationContext())
    }

    @Test
    fun users_posts_must_load() {
        postsViewModel.loadPosts(1)
        val result = postsViewModel.posts.getOrAwaitValue()
        assertTrue(result.size > 0)
        assertTrue(postsViewModel.loading.value == false)
        assertTrue(postsViewModel.errorMessage.value == "")
    }

    @After
    fun closeEverything() {

    }
}