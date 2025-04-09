package com.saifur.gamezone.view.modulenotfound

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.saifur.gamezone.databinding.FragmentModuleNotFoundBinding


class ModuleNotFoundFragment : Fragment() {
    private lateinit var binding : FragmentModuleNotFoundBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentModuleNotFoundBinding.inflate(inflater, container, false)
        return binding.root
    }
}