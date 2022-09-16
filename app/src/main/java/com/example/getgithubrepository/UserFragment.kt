package com.example.getgithubrepository

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.example.getgithubrepository.databinding.FragmentUserBinding
import com.example.getgithubrepository.databinding.FragmentUserDataListBinding
import com.example.getgithubrepository.model.GitHubService
import com.example.getgithubrepository.model.UserDataList
import com.example.getgithubrepository.model.UserDataViewModel
import com.example.getgithubrepository.model.UserRepo
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

class UserFragment : Fragment() {
    private val userDataViewModel: UserDataViewModel by activityViewModels()
    private lateinit var binding: FragmentUserBinding

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.github.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val service = retrofit.create(GitHubService::class.java)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
        binding = FragmentUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val textView = binding.userDataName
        val imageView = binding.userDataImage
        textView.text = userDataViewModel.userData.login
        Glide.with(this).load(userDataViewModel.userData.avatar_url).into(imageView)

    }
}