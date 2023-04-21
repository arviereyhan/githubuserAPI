package com.example.githubuser.data

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.githubuser.data.local.entity.UserEntity
import com.example.githubuser.data.local.room.UserDao
import com.example.githubuser.data.local.room.UserDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteRepository (application: Application) {
    private val userDao: UserDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()
    init {
        val db = UserDatabase.getInstance(application)
        userDao = db.userDao()
    }

    fun getAllFavorite(): LiveData<List<UserEntity>> = userDao.getAllFavoriteUsers()

    fun getFavoriteUserByUsername(username: String): LiveData<Boolean> = userDao.getFavoriteUserByUsername(username)

    fun insert(userEntity: UserEntity){
        executorService.execute { userDao.addFavorite(userEntity) }
    }

    fun delete(userEntity: UserEntity){
        executorService.execute { userDao.delete(userEntity) }
    }


}