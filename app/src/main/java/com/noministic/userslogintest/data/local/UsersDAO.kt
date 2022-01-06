package com.noministic.userslogintest.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.noministic.userslogintest.data.models.User.UsersItem

@Dao
interface UsersDAO {
    @Insert(onConflict = REPLACE)
    suspend fun insertUser(usersItem: UsersItem)

    @Query("select * from USERS limit 1")
    suspend fun getLoggedInUser(): UsersItem?

    @Query("delete from USERS where id=:userId")
    suspend fun removeUser(userId: Int): Int
}