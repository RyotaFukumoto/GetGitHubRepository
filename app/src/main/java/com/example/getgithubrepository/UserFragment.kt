package com.example.getgithubrepository

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.example.getgithubrepository.model.UserDataViewModel

class UserFragment : Fragment() {
    private lateinit var textView: TextView
    private lateinit var userDataViewModel: UserDataViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        userDataViewModel = ViewModelProvider(this)[UserDataViewModel::class.java]
        return inflater.inflate(R.layout.fragment_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        textView = view.findViewById(R.id.userText)
        textView.text = userDataViewModel.userData.login
    }
}