package com.example.homework14.ui.user

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.homework14.databinding.UserItemBinding
import com.example.homework14.extensions.setImage
import com.example.homework14.model.User

class UsersPagingAdapter : PagingDataAdapter<User, UsersPagingAdapter.UserViewHolder>(DiffCallBack()) {


    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = UserViewHolder(
        UserItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    inner class UserViewHolder(private val binding: UserItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind() {
            val curUser = getItem(bindingAdapterPosition)!!
            binding.avatarImgView.setImage(curUser.avatar)
            binding.emailTxtView.text = curUser.email
            binding.nameTxtView.text = curUser.firstName
            binding.lastNameTxtView.text = curUser.lastName
        }
    }

    class DiffCallBack : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User) = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: User, newItem: User) = oldItem == newItem
    }


}