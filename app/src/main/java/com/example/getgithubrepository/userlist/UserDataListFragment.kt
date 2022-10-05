package com.example.getgithubrepository.userlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.getgithubrepository.Constants
import com.example.getgithubrepository.R
import com.example.getgithubrepository.databinding.FragmentUserDataListBinding
import com.example.getgithubrepository.model.API.APIClient
import com.example.getgithubrepository.model.userdata.UserDataViewModel
import com.example.getgithubrepository.model.userdata.UserListData
import com.example.getgithubrepository.model.userdatalist.UserDataListViewModel
import com.example.getgithubrepository.userrepo.UserRepoListFragment


class UserDataListFragment : Fragment(), View.OnClickListener, OnUserItemClickListener {
    private val userDataViewModel: UserDataViewModel by activityViewModels()
    private lateinit var binding: FragmentUserDataListBinding

    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar

    private lateinit var adapter: UserListRecyclerViewAdapter

    private var pageCount = 1
    private var userDataListViewModel: UserDataListViewModel = UserDataListViewModel()
    private var upDateCheck: Boolean = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dividerItemDecoration =
            DividerItemDecoration(view.context , LinearLayoutManager(view.context).orientation)

        binding.button.setOnClickListener(this)
        adapter = UserListRecyclerViewAdapter(view.context, listOf(),this)

        recyclerView = binding.list
        recyclerView.addItemDecoration(dividerItemDecoration)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(view.context)
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    val userNameTextView = binding.userSearchText
                    val userName = userNameTextView.text.toString()
                    if (upDateCheck) {
                        APIClient().getUserNameList(userName, pageCount.toString()) { response ->
                            userDataListViewModel.initUserDataListParameter(response)
                            recyclerView.adapter = UserListRecyclerViewAdapter(
                                view.context,
                                userDataListViewModel.get().items,
                                this@UserDataListFragment
                            )
                            if (response.items.size == Constants.PER_PAGE_HUNDRED) {
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        binding = FragmentUserDataListBinding.inflate(inflater, container, false)
        progressBar = binding.progress
        progressBar.visibility = View.INVISIBLE
        return binding.root
    }

    // 検索ボタンクリック
    override fun onClick(v: View?) {
        val userNameTextView = binding.userSearchText
        val userName = userNameTextView.text.toString()
        if (!userName.isEmpty()) {
            progressBar.visibility = View.VISIBLE
        }
        if (upDateCheck) {
            APIClient().getUserNameList(userName, pageCount.toString()) { response ->
                userDataListViewModel.initUserDataListParameter(response)
                if (v != null) {
                    recyclerView.adapter = UserListRecyclerViewAdapter(
                        v.context,
                        userDataListViewModel.get().items,
                        this@UserDataListFragment
                    )
                    if (response.items.size == Constants.PER_PAGE_HUNDRED) {
                        pageCount++
                    } else {
                        upDateCheck = false
                    }
                }
                progressBar.visibility = View.INVISIBLE
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