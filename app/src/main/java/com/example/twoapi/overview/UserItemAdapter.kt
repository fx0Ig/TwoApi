package com.example.twoapi.overview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.twoapi.R
import com.example.twoapi.databinding.UserItemBinding
import com.example.twoapi.domain.User

class UserItemAdapter(private val listener: UserClickListener) :
    RecyclerView.Adapter<UserItemAdapter.UserItemHolder>() {

    var userList: List<User> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserItemHolder {
        return UserItemHolder.create(parent, listener)
    }

    override fun onBindViewHolder(holder: UserItemHolder, position: Int) {
        holder.bind(userList[position], position)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    class UserItemHolder private constructor(
        private val binding: UserItemBinding,
        private val listener: UserClickListener
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: User, position: Int) {
            binding.userName.text = item.name
            binding.userSource.text = item.source
            Glide.with(binding.root).load(item.picture).diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.image_broken)
                .fallback(R.drawable.image_loading)
                .into(binding.profilePic)
            binding.root.setOnClickListener {
                listener.onItemClick(item.id)
            }
        }

        companion object {

            fun create(parent: ViewGroup, listener: UserClickListener): UserItemHolder = UserItemHolder(
                UserItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ), listener
            )

        }


    }

}

interface UserClickListener {

    fun onItemClick(userId : Int)
}