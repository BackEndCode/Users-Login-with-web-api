package com.noministic.userslogintest.di

import android.content.Context
import androidx.room.Room
import com.noministic.userslogintest.data.DefaultRepository
import com.noministic.userslogintest.data.local.UsersDAO
import com.noministic.userslogintest.data.local.UsersDatabase
import com.noministic.userslogintest.data.remote.ApiRequestInterface
import com.noministic.userslogintest.others.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun providesApiRequestInterface(): ApiRequestInterface =
        Retrofit.Builder().baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(ApiRequestInterface::class.java)

    @Provides
    @Singleton
    fun providesUsersDao(@ApplicationContext context: Context): UsersDAO = Room.databaseBuilder(
        context,
        UsersDatabase::class.java, Constants.USERS_DATABASE_NAME
    ).build().usersDao()

    @Provides
    @Singleton
    fun providesDefaultRepository(apiRequestInterface: ApiRequestInterface, usersDAO: UsersDAO):
            DefaultRepository = DefaultRepository(apiRequestInterface, usersDAO)
}