package com.example.getgithubrepository.userlist

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.getgithubrepository.R
import com.example.getgithubrepository.model.userdata.UserListData

interface OnUserItemClickListener {
    fun onUserItemClick(userListData: UserListData)
}

class UserListRecyclerViewAdapter(
    private val context: Context,
    private val values: List<UserListData>,
    private val listener: OnUserItemClickListener
) : RecyclerView.Adapter<UserListRecyclerViewAdapter.UserDataListRecyclerViewHolder>() {

    class UserDataListRecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView: ImageView
        var textView: TextView
        init {
            imageView = itemView.findViewById(R.id.user_data_image)
            textView = itemView.findViewById(R.id.user_data_name)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserDataListRecyclerViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.fragment_user_data, parent, false)
        return UserDataListRecyclerViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserDataListRecyclerViewHolder, position: Int) {
        val userData = values[position]
        holder.textView.text = userData.login
        Glide.with(context).load(userData.avatar_url).into(holder.imageView)
        holder.itemView.setOnClickListener {
            listener.onUserItemClick(userData)
        }
    }

    override fun getItemCount(): Int = values.size
}
