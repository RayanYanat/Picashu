package com.example.picashu.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.picashu.R
import com.example.picashu.databinding.UserProfilFragmentBinding

class ProfilUserFragment: Fragment(R.layout.user_profil_fragment) {

    private lateinit var binding : UserProfilFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = UserProfilFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }
}