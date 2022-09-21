package com.example.getgithubrepository.userlist

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.getgithubrepository.R
import com.example.getgithubrepository.UserRepoListFragment
import com.example.getgithubrepository.databinding.FragmentUserDataListBinding
import com.example.getgithubrepository.model.GitHubService
import com.example.getgithubrepository.model.UserData
import com.example.getgithubrepository.model.UserDataList
import com.example.getgithubrepository.model.UserDataViewModel
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException


class UserDataListFragment : Fragment(), View.OnClickListener, OnUserItemClickListener {
    private lateinit var binding: FragmentUserDataListBinding

    private val userDataViewModel:UserDataViewModel by activityViewModels()
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: UserListRecyclerViewAdapter
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.github.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val service = retrofit.create(GitHubService::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.button.setOnClickListener(this)
        recyclerView = binding.list
        val dividerItemDecoration =
            DividerItemDecoration(view.context , LinearLayoutManager(view.context).orientation)
        recyclerView.addItemDecoration(dividerItemDecoration)
        adapter = UserListRecyclerViewAdapter(view.context, listOf(),this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(view.context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        binding = FragmentUserDataListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onClick(v: View?) {

        val userNameTextView = binding.userSearchText
        val text = userNameTextView.text.toString()
        // 通信用のクラスに分ける

        val request = service.getUserNameList(text)
        request.enqueue(object : Callback<UserDataList> {
            override fun onResponse(
                call: retrofit2.Call<UserDataList>,
                response: Response<UserDataList>
            ) {
                try{
                    if (response.body() != null) {
                        val arr: UserDataList = response.body()!!
                        if (v != null) {
                            ///　値の更新方法がないか調べる
                            recyclerView.adapter = UserListRecyclerViewAdapter(v.context,arr.items,this@UserDataListFragment)
                        }
                        Log.d("onResponse", arr.toString())
                    }
                }catch (e: IOException){
                    Log.d("onResponse", "IOException")
                }
            }

            override fun onFailure(call: retrofit2.Call<UserDataList>, t: Throwable) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })
    }

    override fun onUserItemClick(userData: UserData) {
        userDataViewModel.initUserDataParameter(userData)
        val userFragment = UserRepoListFragment()
        val fragmentManager: FragmentManager = parentFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.container, userFragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}