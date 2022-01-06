package com.noministic.userslogintest.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.noministic.userslogintest.databinding.FragmentTodoLayoutBinding

class FragmentTodos : Fragment() {
    private lateinit var binding: FragmentTodoLayoutBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTodoLayoutBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}