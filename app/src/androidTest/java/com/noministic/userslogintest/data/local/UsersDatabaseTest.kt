package com.noministic.userslogintest.data.local

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.noministic.userslogintest.data.models.User.UsersItem
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UsersDatabaseTest {

    lateinit var db: UsersDatabase
    lateinit var usersDao: UsersDAO

    @Before
    fun setup() {
        val context: Context = ApplicationProvider.getApplicationContext()
        db = Room.inMemoryDatabaseBuilder(context, UsersDatabase::class.java).build()
        usersDao = db.usersDao()
    }

    @Test
    fun testIfItemInserted() {
        runBlocking {
            val usersItem = UsersItem(
                "rescue.nomi@gmail.com", 23, "Noman",
                "02934823490", "nomi1", "www.abc.com", false
            )
            usersDao.insertUser(usersItem)
            val getuser = usersDao.getLoggedInUser()
            assertTrue(getuser.id == usersItem.id)
        }
    }

    @After
    fun tearDown() {

    }
}