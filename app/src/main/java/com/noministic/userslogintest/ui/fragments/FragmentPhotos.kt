package com.noministic.userslogintest.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.noministic.userslogintest.databinding.FragmentPhotosLayoutBinding

class FragmentPhotos : Fragment() {
    private lateinit var binding: FragmentPhotosLayoutBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPhotosLayoutBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}