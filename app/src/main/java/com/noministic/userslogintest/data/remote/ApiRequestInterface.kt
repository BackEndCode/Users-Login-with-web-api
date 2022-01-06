package com.noministic.userslogintest.data.remote

import com.noministic.userslogintest.data.models.Post.Posts
import com.noministic.userslogintest.data.models.User.Users
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiRequestInterface {
    @GET("/users")
    suspend fun getUsers(): Response<Users>

    @GET("/posts")
    suspend fun getUserPosts(@Query("userId") userId: Int): Response<Posts>
}