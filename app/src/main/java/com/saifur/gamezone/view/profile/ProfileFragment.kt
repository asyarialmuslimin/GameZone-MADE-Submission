package com.saifur.gamezone.view.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.shape.RelativeCornerSize
import com.saifur.gamezone.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.profilePhoto.shapeAppearanceModel = binding.profilePhoto.shapeAppearanceModel
            .toBuilder()
            .setAllCornerSizes(RelativeCornerSize(0.5f))
            .build()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}