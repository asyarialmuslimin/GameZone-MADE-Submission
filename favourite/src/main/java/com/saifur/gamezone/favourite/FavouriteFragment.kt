package com.saifur.gamezone.favourite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.saifur.gamezone.core.utils.FavouriteNavigator
import com.saifur.gamezone.favourite.databinding.FragmentFavouriteBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavouriteFragment : Fragment() {
    private var _binding: FragmentFavouriteBinding? = null
    private val binding get() = _binding!!
    private lateinit var favouriteAdapter: FavouriteAdapter
    private val viewModel : FavouriteViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavouriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        viewModel.getFavouriteGames()
        viewModel.favouriteList.observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                binding.noDataPlaceholder.visibility = View.VISIBLE
                binding.rvFavourite.visibility = View.GONE
            } else {
                binding.noDataPlaceholder.visibility = View.GONE
                binding.rvFavourite.visibility = View.VISIBLE
                favouriteAdapter.setData(it)
            }
        }
    }

    private fun setupRecyclerView() {
        favouriteAdapter = FavouriteAdapter { gameId ->
            (activity as? FavouriteNavigator)?.navigateToDetail(gameId)
        }

        binding.rvFavourite.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = favouriteAdapter
        }
    }

    override fun onDestroyView() {
        binding.rvFavourite.adapter = null
        _binding = null
        super.onDestroyView()
    }
}