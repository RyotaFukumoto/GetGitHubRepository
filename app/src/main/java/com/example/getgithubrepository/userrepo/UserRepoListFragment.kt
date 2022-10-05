package com.example.getgithubrepository.userrepo

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.getgithubrepository.Constants
import com.example.getgithubrepository.R
import com.example.getgithubrepository.databinding.FragmentUserRepoListBinding
import com.example.getgithubrepository.model.API.APIClient
import com.example.getgithubrepository.model.userdata.UserDataViewModel
import com.example.getgithubrepository.model.userrepo.UserRepo
import com.example.getgithubrepository.model.userrepo.UserRepoViewModel


class UserRepoListFragment : Fragment(), OnUserRepoItemClickListener {
    private val userDataViewModel: UserDataViewModel by activityViewModels()

    private lateinit var binding: FragmentUserRepoListBinding

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: UserRepoRecyclerViewAdapter

    private var pageCount = 1
    private var upDateCheck: Boolean = true
    private var mContext: Context? = null

    private val responseBody: UserRepoViewModel = UserRepoViewModel()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserRepoListBinding.inflate(inflater, container, false)

        val imageView = binding.userDataImage
        val userNameTextView = binding.userDataName
        val userFullNameTextView = binding.userDataFullName
        val followers = binding.followers
        val following = binding.following
        val userName = userDataViewModel.get().login
        val userProcess = binding.userProgress
        val repoProcess = binding.repoProgress
        mContext = inflater.context

        userProcess.visibility = View.VISIBLE
        repoProcess.visibility = View.VISIBLE
        APIClient().getUserData(userName) { userData ->
            Glide.with(inflater.context).load(userData.avatar_url).into(imageView)
            userNameTextView.text = getString(R.string.user_name,userData.login)
            userFullNameTextView.text = getString(R.string.user_full_name,userData.name)
            followers.text = getString(R.string.user_followers,userData.followers.toString())
            following.text = getString(R.string.user_following,userData.following.toString())
            userProcess.visibility = View.INVISIBLE
        }
        if (upDateCheck) {
            APIClient().getUserReposList(userName, pageCount) { userRepoList ->
                responseBody.initUserRepoParameter(userRepoList as MutableList)
                recyclerView.adapter = UserRepoRecyclerViewAdapter(
                    inflater.context,
                    responseBody.get(),
                    this@UserRepoListFragment
                )
                if (userRepoList.size == Constants.PER_PAGE_HUNDRED) {
                    pageCount++
                } else {
                    upDateCheck = false
                }
                repoProcess.visibility = View.INVISIBLE
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dividerItemDecoration =
            DividerItemDecoration(view.context , LinearLayoutManager(view.context).orientation)
        val userName = userDataViewModel.get().login

        adapter = UserRepoRecyclerViewAdapter(view.context, mutableListOf(),this)

        recyclerView = binding.userRepoList
        recyclerView.addItemDecoration(dividerItemDecoration)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(view.context)
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    if (upDateCheck) {
                        APIClient().getUserReposList(userName, pageCount) { userRepoList ->
                            responseBody.add(userRepoList as MutableList)
                            recyclerView.adapter = UserRepoRecyclerViewAdapter(
                                view.context,
                                responseBody.get(),
                                this@UserRepoListFragment
                            )
                            if (userRepoList.size == Constants.PER_PAGE_HUNDRED) {
                                pageCount++
                            } else {
                                upDateCheck = false
                            }
                        }
                    }
                }
            }
        })
    }

    override fun onUserRepoItemClick(repo: UserRepo) {
        val url = repo.html_url
        val builder = CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        mContext?.let { customTabsIntent.launchUrl(it, Uri.parse(url)) }
    }

}