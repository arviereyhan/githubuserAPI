package com.example.githubuser.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.githubuser.R
import com.example.githubuser.data.local.entity.UserEntity
import com.example.githubuser.databinding.ActivityDetailUserBinding
import com.example.githubuser.utils.SettingPreferences
import com.example.githubuser.utils.ViewModelFactory
import com.google.android.material.tabs.TabLayoutMediator

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class DetailUserActivity : AppCompatActivity() {
    companion object{
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_AVATAR = "extra_avatar"
        private val TAB_TITLES = intArrayOf(
            R.string.tab_1,
            R.string.tab_2,
        )
    }

    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var viewModel: DetailUserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = "Detail GithubUser"

        val username = intent.getStringExtra(EXTRA_USERNAME)
        val avatarUrl = intent.getStringExtra(EXTRA_AVATAR)
        val userFavorite = intent.getParcelableExtra<UserEntity?>("asda")

        val pref = SettingPreferences.getInstance(dataStore)
        val viewModelFactory = ViewModelFactory(this@DetailUserActivity.application, username.toString(), pref)
        viewModel = ViewModelProvider(this@DetailUserActivity, viewModelFactory)[DetailUserViewModel::class.java]
        viewModel.setUserDetail(username.toString())
        viewModel.user.observe(this) {
            binding.usernameDetail.text = it.login
            binding.nameDetail.text = it.name
            binding.followersDetail.text = "Followers: ${it.followers}"
            binding.followingDetail.text = "Following: ${it.following}"
            Glide.with(this@DetailUserActivity).load(it.avatarUrl).into(binding.imgDetail)
            val sectionsPagerAdapter = SectionsPagerAdapter(this)
            binding.viewPager.adapter = sectionsPagerAdapter
            TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
                tab.text = resources.getString(TAB_TITLES[position])
            }.attach()
        }

        viewModel.isLoading.observe(this){
            showLoading(it)
        }


        viewModel.favoriteUserExist.observe(this) { favoriteUserExist ->
            if (favoriteUserExist) {
                binding.fabFavorite.setImageDrawable(ContextCompat.getDrawable(baseContext, R.drawable.ic_favorite_24))
            } else {
                binding.fabFavorite.setImageDrawable(ContextCompat.getDrawable(baseContext, R.drawable.ic_24))
            }

            binding.fabFavorite.setOnClickListener {
                val favUser = userFavorite
                    ?: UserEntity(
                        avatarUrl = avatarUrl,
                        username = username.toString()
                    )
                if (viewModel.checkFavoriteIsExist()!!) {
                    viewModel.deleteFavorite(favUser)

                } else {
                    viewModel.addFavorite(favUser)
                }
            }
        }


    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }



}