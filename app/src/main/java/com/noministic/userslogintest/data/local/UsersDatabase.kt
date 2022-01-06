package com.noministic.userslogintest.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.noministic.userslogintest.data.models.User.UsersItem

@Database(entities = [UsersItem::class], version = 1)
abstract class UsersDatabase : RoomDatabase() {
    abstract fun usersDao(): UsersDAO
}