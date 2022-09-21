package com.example.getgithubrepository

import android.content.Context
import android.net.wifi.p2p.WifiP2pManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.example.getgithubrepository.placeholder.PlaceholderContent.PlaceholderItem
import com.example.getgithubrepository.databinding.FragmentUserRepoBinding
import com.example.getgithubrepository.model.UserRepo

interface OnUserRepoItemClickListener {
    fun onUserRepoItemClick(repo: UserRepo)
}

class UserRepoRecyclerViewAdapter(
    private val context: Context,
    private val values: List<UserRepo>,
    private val listener: OnUserRepoItemClickListener
) : RecyclerView.Adapter<UserRepoRecyclerViewAdapter.UserRepoListRecycleViewHolder>() {

    class UserRepoListRecycleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textView: TextView
        init {
            textView = itemView.findViewById(R.id.item_text)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserRepoListRecycleViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.fragment_user_repo, parent, false)
        return UserRepoListRecycleViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserRepoListRecycleViewHolder, position: Int) {
        val item = values[position]
        holder.textView.text = item.name
    }

    override fun getItemCount(): Int = values.size

}