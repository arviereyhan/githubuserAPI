package com.example.githubuser.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.data.local.entity.UserEntity
import com.example.githubuser.databinding.ActivityFavoriteBinding
import com.example.githubuser.utils.SettingPreferences
import com.example.githubuser.utils.ViewModelFactory

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var viewModel: FavoriteViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = "Favorite"

        val pref = SettingPreferences.getInstance(dataStore)
        val viewModelFactory = ViewModelFactory(this@FavoriteActivity.application, "", pref)
        viewModel = ViewModelProvider(this@FavoriteActivity, viewModelFactory)[FavoriteViewModel::class.java]

        val layoutManager = LinearLayoutManager(this@FavoriteActivity)
        binding.listFavorite.layoutManager = layoutManager
        binding.listFavorite.setHasFixedSize(true)
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.listFavorite.addItemDecoration(itemDecoration)
        viewModel.getAllFavorite.observe(this){getAllFavorite ->
            setFavorite(getAllFavorite)
        }


    }

    override fun onResume() {
        viewModel.getAllFavorite.observe(this){getAllFavorite ->
            setFavorite(getAllFavorite)
        }
        super.onResume()
    }

    private fun setFavorite(listFavorite: List<UserEntity>){
        val listData = ArrayList<UserEntity>()
        for (userData in listFavorite){
            val dataName = userData.username
            val dataAvatar = userData.avatarUrl

            val listUsers = UserEntity(dataName, dataAvatar)
            listData.add(listUsers)
        }
        val adapter = FavoriteAdapter(listData)
        adapter.updateUserList(listFavorite)
        binding.listFavorite.adapter = adapter
        adapter.setOnItemClickListener(object : FavoriteAdapter.OnItemClickListener {
            override fun onItemClick(data: UserEntity) {
                val intentDetail = Intent(this@FavoriteActivity, DetailUserActivity::class.java)
                intentDetail.putExtra(DetailUserActivity.EXTRA_USERNAME, data.username)
                intentDetail.putExtra(DetailUserActivity.EXTRA_AVATAR, data.avatarUrl)
                startActivity(intentDetail)
            }
        })
    }
}