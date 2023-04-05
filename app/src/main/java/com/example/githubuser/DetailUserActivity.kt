package com.example.githubuser

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.githubuser.databinding.ActivityDetailUserBinding
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserActivity : AppCompatActivity() {
    companion object{
        const val EXTRA_USERNAME = "extra_username"
        private val TAB_TITLES = intArrayOf(
            R.string.tab_1,
            R.string.tab_2,
        )
    }

    private lateinit var binding: ActivityDetailUserBinding
    private val viewModel by viewModels<DetailUserViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = "Detail GithubUser"

        val username = intent.getStringExtra(EXTRA_USERNAME)




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

    }
    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

}