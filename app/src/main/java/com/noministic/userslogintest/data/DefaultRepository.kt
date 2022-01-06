package com.noministic.userslogintest.data

import android.util.Log
import com.noministic.userslogintest.data.local.UsersDAO
import com.noministic.userslogintest.data.models.Post.Posts
import com.noministic.userslogintest.data.models.User.Users
import com.noministic.userslogintest.data.models.User.UsersItem
import com.noministic.userslogintest.data.remote.ApiRequestInterface
import com.noministic.userslogintest.data.remote.Repository
import com.noministic.userslogintest.others.Resource

class DefaultRepository(
    private val apiRequestInterface: ApiRequestInterface,
    private val usersDAO: UsersDAO
) : Repository {
    override suspend fun getUsers(): Resource<Users> {
        return try {
            val response = apiRequestInterface.getUsers()
            if (response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: return Resource.error("unknow error", null)
            } else {
                return Resource.error("unknow error", null)
            }
        } catch (e: Exception) {
            return Resource.error("an unknow error occured", null)
        }
    }

    override suspend fun getUserPosts(userId: Int): Resource<Posts> {
        return try {
            val response = apiRequestInterface.getUserPosts(userId)
            if (response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: return Resource.error("unknow error", null)
            } else {
                return Resource.error("unknow error", null)
            }
        } catch (e: Exception) {
            return Resource.error("an unknow error occured", null)
        }
    }

    override suspend fun insertUser(usersItem: UsersItem) {
        usersDAO.insertUser(usersItem)
    }

    override suspend fun getLoggedInUser(): Resource<UsersItem>? {
        return try {
            val response = usersDAO.getLoggedInUser()
            response?.let { return@let Resource.success(it) }
        } catch (e: Exception) {
            return Resource.error("an unknow error occured", null)
        }
    }

    override suspend fun removeUser(userId: Int): Resource<Int> {
        return try {
            val response = usersDAO.removeUser(userId)
            if (response > 0) {
                Resource.success(response)
            } else
                Resource.error("No user found to delete", null)
        } catch (e: Exception) {
            Resource.error("an unknow error occured", null)
        }
    }

}