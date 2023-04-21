package com.example.githubuser.ui

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.githubuser.data.FavoriteRepository
import com.example.githubuser.data.local.entity.UserEntity

class FavoriteViewModel(application: Application): ViewModel() {
    private val favoriteRepository : FavoriteRepository = FavoriteRepository(application)
    val getAllFavorite: LiveData<List<UserEntity>> = favoriteRepository.getAllFavorite()
}