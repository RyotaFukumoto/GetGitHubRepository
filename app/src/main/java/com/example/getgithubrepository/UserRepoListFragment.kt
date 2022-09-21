package com.example.getgithubrepository

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.getgithubrepository.databinding.FragmentUserRepoListBinding
import com.example.getgithubrepository.model.GitHubService
import com.example.getgithubrepository.model.UserDataViewModel
import com.example.getgithubrepository.model.UserRepo
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import com.bumptech.glide.Glide


class UserRepoListFragment : Fragment(),OnUserRepoItemClickListener {
    private val userDataViewModel: UserDataViewModel by activityViewModels()
    private lateinit var binding: FragmentUserRepoListBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: UserRepoRecyclerViewAdapter
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.github.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val service = retrofit.create(GitHubService::class.java)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val userName = userDataViewModel.userData.login
        val request = service.getUserReposList(userName)
        request.enqueue(object : Callback<List<UserRepo>> {
            override fun onResponse(
                call: retrofit2.Call<List<UserRepo>>,
                response: Response<List<UserRepo>>
            ) {
                try{
                    if (response.body() != null) {
                        val arr: List<UserRepo> = response.body()!!
                        recyclerView.adapter = UserRepoRecyclerViewAdapter(inflater.context,arr,this@UserRepoListFragment)
                        Log.d("onResponse", arr.toString())
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
        val textView = binding.userDataName
        val imageView = binding.userDataImage
        textView.text = userDataViewModel.userData.login
        recyclerView = binding.userRepoList
        Glide.with(this).load(userDataViewModel.userData.avatar_url).into(imageView)
        val dividerItemDecoration =
            DividerItemDecoration(view.context , LinearLayoutManager(view.context).orientation)
        recyclerView.addItemDecoration(dividerItemDecoration)
        adapter = UserRepoRecyclerViewAdapter(view.context, listOf(),this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(view.context)
    }

    override fun onUserRepoItemClick(repo: UserRepo) {
        TODO("Not yet implemented")
    }
}