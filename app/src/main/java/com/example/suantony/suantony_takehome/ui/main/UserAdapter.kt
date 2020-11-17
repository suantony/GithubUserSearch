package com.example.suantony.suantony_takehome.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.suantony.suantony_takehome.R
import com.example.suantony.suantony_takehome.data.source.local.entity.UserEntity
import kotlinx.android.synthetic.main.item_user.view.*

class UserAdapter : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    private var listUsers = ArrayList<UserEntity>()

    fun setUsers(users: List<UserEntity>?) {
        if (users == null) return
        listUsers.addAll(users)
    }

    fun clearUsers() {
        listUsers.clear()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAdapter.UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return UserViewHolder(view)
    }

    override fun getItemCount(): Int = listUsers.size

    override fun onBindViewHolder(holder: UserAdapter.UserViewHolder, position: Int) {
        val user = listUsers[position]
        holder.bind(user)
    }

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(user: UserEntity) {
            with(itemView) {
                Glide.with(context)
                    .load(user.avatarUrl)
                    .apply(
                        RequestOptions.placeholderOf(R.drawable.ic_loading)
                            .error(R.drawable.ic_error)
                    )
                    .into(img_profile_image)

                tv_profile_name.text = user.login
            }
        }
    }

}