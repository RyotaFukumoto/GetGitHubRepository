package com.example.getgithubrepository

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide


class UserListRecyclerViewAdapter(
    private val context: Context,
    private val values: List<com.example.getgithubrepository.model.UserData>
) : RecyclerView.Adapter<UserListRecyclerViewAdapter.UserDataListRecyclerViewHolder>() {
    private lateinit var listener: OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(userData:com.example.getgithubrepository.model.UserData)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    class UserDataListRecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView: ImageView = itemView.findViewById(R.id.image)
        var textView: TextView = itemView.findViewById(R.id.name)

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
            listener.onItemClick(userData)
        }
    }

    override fun getItemCount(): Int = values.size
}
