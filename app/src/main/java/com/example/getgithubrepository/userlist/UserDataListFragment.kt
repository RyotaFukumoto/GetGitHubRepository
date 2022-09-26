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
import com.example.getgithubrepository.userrepo.UserRepoListFragment
import com.example.getgithubrepository.databinding.FragmentUserDataListBinding
import com.example.getgithubrepository.model.*
import com.example.getgithubrepository.model.userdata.UserDataViewModel
import com.example.getgithubrepository.model.userdata.UserListData
import com.example.getgithubrepository.model.userdatalist.UserDataList
import com.example.getgithubrepository.model.userdatalist.UserDataListViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException


class UserDataListFragment : Fragment(), View.OnClickListener, OnUserItemClickListener {
    private lateinit var binding: FragmentUserDataListBinding

    private val userDataViewModel: UserDataViewModel by activityViewModels()
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: UserListRecyclerViewAdapter
    private var pageCount = 1
    private var userDataListViewModel: UserDataListViewModel = UserDataListViewModel()

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
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    val userNameTextView = binding.userSearchText
                    val userName = userNameTextView.text.toString()
                     APIClient().getUserNameList(userName, pageCount.toString()) { response ->
                         userDataListViewModel.initUserDataListParameter(response)
                         recyclerView.adapter = UserListRecyclerViewAdapter(
                             view.context,
                             userDataListViewModel.get().items,
                             this@UserDataListFragment
                         )
                         pageCount++
                     }
                }
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        binding = FragmentUserDataListBinding.inflate(inflater, container, false)
        return binding.root
    }

    // 検索ボタンクリック
    override fun onClick(v: View?) {

        val userNameTextView = binding.userSearchText
        val userName = userNameTextView.text.toString()
        // 通信用のクラスに分ける
        APIClient().getUserNameList(userName, pageCount.toString()) { response ->
            if (response != null) {
                userDataListViewModel.initUserDataListParameter(response)
                if (v != null) {
                    recyclerView.adapter = UserListRecyclerViewAdapter(
                        v.context,
                        userDataListViewModel.get().items,
                        this@UserDataListFragment
                    )
                }
                pageCount++
            }
        }
    }
    
    override fun onUserItemClick(userListData: UserListData) {
        userDataViewModel.initUserDataParameter(userListData)
        val userFragment = UserRepoListFragment()
        val fragmentManager: FragmentManager = parentFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.container, userFragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

}