package com.example.getgithubrepository.userrepo

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
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
import com.example.getgithubrepository.R
import com.example.getgithubrepository.databinding.FragmentUserRepoListBinding
import com.example.getgithubrepository.model.*
import com.example.getgithubrepository.model.userdata.UserDataViewModel
import com.example.getgithubrepository.model.userdatalist.UserData
import com.example.getgithubrepository.model.userrepo.UserRepo
import com.example.getgithubrepository.model.userrepo.UserRepoViewModel
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException


class UserRepoListFragment : Fragment(), OnUserRepoItemClickListener {
    private val userDataViewModel: UserDataViewModel by activityViewModels()
    private lateinit var binding: FragmentUserRepoListBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: UserRepoRecyclerViewAdapter
    private var pageCount = 1
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.github.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val service = retrofit.create(GitHubService::class.java)
    private var mContext: Context? = null

    private val responseBody: UserRepoViewModel = UserRepoViewModel()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val userName = userDataViewModel.get().login
        mContext = inflater.context
        val userRequest = service.getUserData("",userName)
        userRequest.enqueue(object : Callback<UserData> {
            override fun onResponse(
                call: retrofit2.Call<UserData>,
                response: Response<UserData>
            ) {
                try{
                    if (response.body() != null) {
                        val arr: UserData = response.body()!!
                        val imageView = binding.userDataImage
                        val userNameTextView = binding.userDataName
                        val userFullNameTextView = binding.userDataFullName
                        val followers = binding.followers
                        val following = binding.following
                        Glide.with(inflater.context).load(arr.avatar_url).into(imageView)
                        userNameTextView.text = getString(R.string.user_name,arr.login)
                        userFullNameTextView.text = getString(R.string.user_full_name,arr.name)
                        followers.text = getString(R.string.user_followers,arr.followers.toString())
                        following.text = getString(R.string.user_following,arr.following.toString())
                        Log.d("onResponse", arr.toString())
                    }
                }catch (e: IOException){
                    Log.d("onResponse", "IOException")
                }
            }

            override fun onFailure(call: retrofit2.Call<UserData>, t: Throwable) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })

        val repoRequest = service.getUserReposList("",userName,"100",pageCount.toString())
        repoRequest.enqueue(object : Callback<List<UserRepo>> {
            override fun onResponse(
                call: retrofit2.Call<List<UserRepo>>,
                response: Response<List<UserRepo>>
            ) {
                try{
                    if (response.body() != null) {
                        val userRepoList: List<UserRepo>? = response.body()
                        responseBody.initUserRepoParameter(userRepoList as MutableList)
                        recyclerView.adapter = UserRepoRecyclerViewAdapter(inflater.context,responseBody.get(),this@UserRepoListFragment)
                        Log.d("onResponse", responseBody.toString())
                        pageCount++
                    }
                }catch (e: IOException){
                    Log.d("onResponse", "IOException")
                }
            }

            override fun onFailure(call: retrofit2.Call<List<UserRepo>>, t: Throwable) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })
        binding = FragmentUserRepoListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = binding.userRepoList
        val dividerItemDecoration =
            DividerItemDecoration(view.context , LinearLayoutManager(view.context).orientation)
        recyclerView.addItemDecoration(dividerItemDecoration)
        adapter = UserRepoRecyclerViewAdapter(view.context, mutableListOf(),this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(view.context)
        val userName = userDataViewModel.get().login
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    val repoRequest = service.getUserReposList("",userName,"100",pageCount.toString())
                    repoRequest.enqueue(object : Callback<List<UserRepo>> {
                        override fun onResponse(
                            call: retrofit2.Call<List<UserRepo>>,
                            response: Response<List<UserRepo>>
                        ) {
                            try{
                                if (response.body() != null && response.body()!!.isNotEmpty()) {
                                    responseBody.add(response.body() as MutableList)
                                    recyclerView.adapter = UserRepoRecyclerViewAdapter(view.context,responseBody.get(),this@UserRepoListFragment)
                                    Log.d("onResponse", responseBody.toString())
                                    pageCount++

                                }
                            }catch (e: IOException){
                                Log.d("onResponse", "IOException")
                            }
                        }

                        override fun onFailure(call: retrofit2.Call<List<UserRepo>>, t: Throwable) {
                            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                        }
                    })
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