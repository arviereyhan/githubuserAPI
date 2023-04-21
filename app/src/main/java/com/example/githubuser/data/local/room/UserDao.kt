package com.example.githubuser.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.githubuser.data.local.entity.UserEntity

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addFavorite(userEntity: UserEntity)

    @Query("SELECT EXISTS(SELECT * FROM user WHERE username = :username)")
    fun getFavoriteUserByUsername(username: String) : LiveData<Boolean>

    @Delete
    fun delete(userEntity: UserEntity)

    @Query("SELECT * FROM user ORDER BY username ASC")
    fun getAllFavoriteUsers() : LiveData<List<UserEntity>>
}