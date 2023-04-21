package com.example.githubuser.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.R
import com.example.githubuser.User
import com.example.githubuser.databinding.ActivityMainBinding
import com.example.githubuser.utils.SettingPreferences
import com.example.githubuser.utils.ViewModelFactory

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pref = SettingPreferences.getInstance(dataStore)
        val viewModelFactory = ViewModelFactory(this@MainActivity.application, "", pref)
        mainViewModel = ViewModelProvider(this@MainActivity, viewModelFactory)[MainViewModel::class.java]
        val layoutManager = LinearLayoutManager(this@MainActivity)
        binding.listUser.layoutManager = layoutManager
        binding.listUser.setHasFixedSize(true)
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.listUser.addItemDecoration(itemDecoration)

        binding.searchUser.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                mainViewModel.findUser(query.toString())
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean = false
        })
        mainViewModel.user.observe(this) {user ->
            setUser(user)
        }
        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        mainViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }

    private fun setUser(user: List<User>){
        val listData = ArrayList<User>()

        for (userData in user){
            val dataName = userData.login
            val dataAvatar = userData.avatarUrl

            val listUsers = User(dataName, dataAvatar)
            listData.add(listUsers)
        }
        val adapter = UserAdapter(listData)
        binding.listUser.adapter = adapter
        adapter.setOnItemClickListener(object : UserAdapter.OnItemClickListener {
            override fun onItemClick(data: User) {
                val intentDetail = Intent(this@MainActivity, DetailUserActivity::class.java)
                    intentDetail.putExtra(DetailUserActivity.EXTRA_USERNAME, data.login)
                    intentDetail.putExtra(DetailUserActivity.EXTRA_AVATAR, data.avatarUrl)
                    startActivity(intentDetail)
                }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)!!
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.btn_favorite -> {
                val intentToFavorite = Intent(this@MainActivity, FavoriteActivity::class.java)
                startActivity(intentToFavorite)
                true
            }
            R.id.settings -> {
                val intentToSetting = Intent(this@MainActivity, SettingActivity::class.java)
                startActivity(intentToSetting)
                true
            }
            else -> true
        }
    }
}
