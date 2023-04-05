package com.example.githubuser

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



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
        adapter.setOnItemClickListener(object : UserAdapter.OnItemClickListener{
            override fun onItemClick(data: User) {
                val intentDetail = Intent(this@MainActivity, DetailUserActivity::class.java)
                    intentDetail.putExtra(DetailUserActivity.EXTRA_USERNAME, data.login)
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
}
