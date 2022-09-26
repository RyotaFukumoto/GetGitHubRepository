package com.example.getgithubrepository.userrepo

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.getgithubrepository.R

import com.example.getgithubrepository.model.userrepo.UserRepo

interface OnUserRepoItemClickListener {
    fun onUserRepoItemClick(repo: UserRepo)
}

class UserRepoRecyclerViewAdapter(
    private val context: Context,
    private val values: MutableList<UserRepo>,
    private val listener: OnUserRepoItemClickListener
) : RecyclerView.Adapter<UserRepoRecyclerViewAdapter.UserRepoListRecycleViewHolder>() {

    class UserRepoListRecycleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var repoTextView: TextView
        var languageTextView: TextView
        var stargazersCountTextView: TextView
        var descriptionTextView: TextView
        init {
            repoTextView = itemView.findViewById(R.id.item_text)
            languageTextView = itemView.findViewById(R.id.language)
            stargazersCountTextView = itemView.findViewById(R.id.stargazers_count)
            descriptionTextView = itemView.findViewById(R.id.description)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserRepoListRecycleViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.fragment_user_repo, parent, false)
        return UserRepoListRecycleViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserRepoListRecycleViewHolder, position: Int) {
        val item = values[position]
        holder.repoTextView.text = item.name
        val language = if (item.language.isNullOrEmpty()) "" else item.language
        holder.languageTextView.text = context.getString(R.string.repo_language,language)
        holder.stargazersCountTextView.text = context.getString(R.string.repo_stargazers_count, item.stargazers_count.toString())
        val description = if (item.description.isNullOrEmpty()) "" else item.description
        holder.descriptionTextView.text = context.getString(R.string.repo_description, description)
        holder.itemView.setOnClickListener {
            listener.onUserRepoItemClick(item)
        }
    }

    override fun getItemCount(): Int {
        val itemList :MutableList<UserRepo> = mutableListOf()
        if(values.size != 0) {
            for (item in values) {
                if (!item.fork) {
                    itemList.add(item)
                }
            }
            values.clear()
            values.addAll(itemList)
        }
        return values.size
    }

}