package com.example.getgithubrepository

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.getgithubrepository.databinding.ActivityMainBinding
import com.example.getgithubrepository.userlist.UserDataListFragment


class MainActivity : AppCompatActivity(){
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportFragmentManager.beginTransaction().replace(R.id.fl_activity_main,
            UserDataListFragment()
        ).commit()
    }

}