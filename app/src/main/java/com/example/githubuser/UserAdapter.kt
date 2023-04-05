package com.example.githubuser


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuser.databinding.ItemRowUserBinding

class UserAdapter(private val listUser : List<User>) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    private var onItemClickListener: OnItemClickListener? = null
    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemRowUserBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder((view))
    }

    override fun getItemCount() = listUser.size

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(listUser[position])

    }



    inner class ViewHolder (private val binding: ItemRowUserBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User){
            binding.root.setOnClickListener {
                onItemClickListener?.onItemClick(user)
            }
                Glide.with(itemView).load(user.avatarUrl).into(binding.profileImage)
                binding.username.text = user.login
        }
    }
    interface OnItemClickListener {
        fun onItemClick(data: User)
    }

}