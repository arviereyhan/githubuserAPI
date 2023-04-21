package com.example.githubuser.utils

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.githubuser.data.FavoriteRepository
import com.example.githubuser.ui.DetailUserViewModel
import com.example.githubuser.ui.FavoriteViewModel
import com.example.githubuser.ui.MainViewModel

class ViewModelFactory(private val application: Application, private val username: String, private val pref: SettingPreferences) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            return FavoriteViewModel(application) as T
        }else if (modelClass.isAssignableFrom(DetailUserViewModel::class.java)){
            return DetailUserViewModel(application, username) as T
        }else if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(pref) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class: ${modelClass.name}")
    }

}