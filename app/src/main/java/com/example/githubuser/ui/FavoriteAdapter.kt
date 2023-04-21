package com.example.githubuser.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuser.User
import com.example.githubuser.data.local.entity.UserEntity
import com.example.githubuser.databinding.ItemRowUserBinding
import com.example.githubuser.utils.DiffItemCallback

class FavoriteAdapter(private val listFavorite: List<UserEntity>) : RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {

    private var userList = emptyList<UserEntity>()

    fun updateUserList(listFavorite: List<UserEntity>) {
        val diff = DiffUtil.calculateDiff(DiffItemCallback(userList, listFavorite))
        this.userList = listFavorite
        diff.dispatchUpdatesTo(this)
    }


    private var onItemClickListener: OnItemClickListener? = null
    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): FavoriteAdapter.ViewHolder {
        val view = ItemRowUserBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder((view))
    }

    override fun onBindViewHolder(viewHolder: FavoriteAdapter.ViewHolder, position: Int) {
        viewHolder.bind(listFavorite[position])
    }

    override fun getItemCount() = listFavorite.size

    inner class ViewHolder (private val binding: ItemRowUserBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(favorite: UserEntity){
            binding.root.setOnClickListener {
                onItemClickListener?.onItemClick(favorite)
            }
            Glide.with(itemView).load(favorite.avatarUrl).into(binding.profileImage)
            binding.username.text = favorite.username
        }
    }

    interface OnItemClickListener {
        fun onItemClick(data: UserEntity)
    }

}