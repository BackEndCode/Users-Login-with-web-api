package com.noministic.userslogintest.data.remote

import androidx.lifecycle.LiveData
import com.noministic.userslogintest.data.models.Post.Posts
import com.noministic.userslogintest.data.models.User.Users
import com.noministic.userslogintest.data.models.User.UsersItem
import com.noministic.userslogintest.others.Resource
interface Repository {

    suspend fun getUsers(): Resource<Users>
    suspend fun getUserPosts(userId: Int): Resource<Posts>
    suspend fun insertUser(usersItem: UsersItem)
    suspend fun getLoggedInUser(): Resource<UsersItem>

}