package com.saifur.gamezone.view.home

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.saifur.gamezone.core.utils.Resource
import com.saifur.gamezone.databinding.FragmentHomeBinding
import com.saifur.gamezone.extension.textChanges
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel : HomeViewModel by viewModel()

    private lateinit var gameListAdapter : GameListAdapter

    private lateinit var rvItemDecoration : RecyclerView.ItemDecoration

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeViewModel.getGames()
        setupRecyclerView()
        setupSearchBar()

        binding.btnClear.setOnClickListener {
            binding.etSearch.text.clear()
            homeViewModel.getGames()
        }

        homeViewModel.games.observe(viewLifecycleOwner) {
            when(it) {
                is Resource.Error -> {
                    Log.e("ErrorGetGame", it.message.toString())
                }
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.rvGame.visibility = View.GONE
                }
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.rvGame.visibility = View.VISIBLE
                    it.data?.let { games ->
                        gameListAdapter.setData(games)
                    }
                }
            }
        }
    }

    private fun setupRecyclerView() {
        val gridLayoutManager = GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)

        gameListAdapter = GameListAdapter { gameId ->
            val action = HomeFragmentDirections.actionNavigationHomeToDetailFragment(gameId)
            findNavController().navigate(action)
        }

        binding.rvGame.apply {
            layoutManager = gridLayoutManager
            adapter = gameListAdapter
        }

        rvItemDecoration = object : RecyclerView.ItemDecoration() {
            val spacing = 4.toPx(requireContext())
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                outRect.set(spacing, spacing, spacing, spacing)
            }
        }
        binding.rvGame.addItemDecoration(rvItemDecoration)
    }

    @OptIn(FlowPreview::class)
    private fun setupSearchBar() {
        val searchBar = binding.etSearch
        val clearButton = binding.btnClear

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                searchBar.textChanges()
                    .onEach { text ->
                        clearButton.isVisible = !text.isNullOrEmpty()
                    }
                    .debounce(500)
                    .collect { text ->
                        val query = text?.toString().orEmpty()
                        if (query.isBlank()) {
                            homeViewModel.getGames()
                        } else {
                            homeViewModel.searchGames(query)
                        }
                    }
            }
        }
    }

    override fun onDestroyView() {
        binding.rvGame.removeItemDecoration(rvItemDecoration)
        binding.rvGame.adapter = null
        _binding = null
        super.onDestroyView()
    }
}

fun Int.toPx(context: Context): Int =
    (this * context.resources.displayMetrics.density).toInt()