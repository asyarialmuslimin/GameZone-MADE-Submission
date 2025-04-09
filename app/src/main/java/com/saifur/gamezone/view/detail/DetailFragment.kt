package com.saifur.gamezone.view.detail

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.saifur.gamezone.R
import com.saifur.gamezone.core.domain.model.GameDetail
import com.saifur.gamezone.core.utils.Resource
import com.saifur.gamezone.core.utils.StringUtil
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.saifur.gamezone.databinding.FragmentDetailBinding
import androidx.core.net.toUri

class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel : DetailViewModel by viewModel()

    private lateinit var gameDetail: GameDetail
    private var isFavourited = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args: DetailFragmentArgs by navArgs()
        val gameId = args.gameId
        viewModel.getGameDetail(gameId)
        viewModel.gameDetail.observe(viewLifecycleOwner) {
            when(it) {
                is Resource.Error -> {}
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.mainLayout.visibility = View.GONE
                }
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.mainLayout.visibility = View.VISIBLE
                    it.data?.let { game ->
                        Glide.with(requireContext())
                            .load(game.backgroundImage)
                            .into(binding.imgGame)

                        binding.txtGameName.text = game.name
                        binding.txtGameCategory.text = game.category
                        binding.txtGameRating.text = game.rating.toString()
                        binding.txtGameDescription.text = Html.fromHtml(game.description, Html.FROM_HTML_MODE_COMPACT)
                        binding.txtGameRelease.text = StringUtil.convertDate(game.releaseDate)
                        binding.txtGameUpdate.text = StringUtil.convertDate(game.lastUpdate)
                        binding.txtGameDeveloper.text = game.developers
                        binding.txtGamePlatform.text = game.platforms

                        isFavourited = game.isFavorite
                        gameDetail = game

                        if (game.website.isEmpty()) {
                            binding.btnWebsite.visibility = View.GONE
                        } else {
                            binding.btnWebsite.setOnClickListener {
                                goToWebsite(game.website)
                            }
                        }

                        if (game.reddit.isEmpty()) {
                            binding.btnReddit.visibility = View.GONE
                        } else {
                            binding.btnReddit.setOnClickListener {
                                goToWebsite(game.reddit)
                            }
                        }

                        if (game.metacritic.isEmpty()) {
                            binding.btnMetacritics.visibility = View.GONE
                        } else {
                            binding.btnMetacritics.setOnClickListener {
                                goToWebsite(game.metacritic)
                            }
                        }

                        setupToolbar()
                    }
                }
            }
        }
    }

    private fun setupToolbar() {
        binding.topAppBar.setNavigationIcon(R.drawable.ic_back)
        binding.topAppBar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        binding.topAppBar.menu.clear()

        val favouriteItem = binding.topAppBar.menu.add(Menu.NONE, R.id.action_favourite, Menu.NONE, "Favourite")
        if (isFavourited) {
            favouriteItem.setIcon(R.drawable.ic_button_fav)
        } else {
            favouriteItem.setIcon(R.drawable.ic_button_unfav)
        }
        favouriteItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM)

        val shareItem = binding.topAppBar.menu.add(Menu.NONE, R.id.action_share, Menu.NONE, "Share")
        shareItem.setIcon(R.drawable.ic_share)
        shareItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM)

        binding.topAppBar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.action_favourite -> {
                    isFavourited = !isFavourited
                    viewModel.editGameFavourite(isFavourited, gameDetail)
                    if (isFavourited) {
                        item.setIcon(R.drawable.ic_button_fav)
                        Toast.makeText(requireContext(), "Added to Favourite", Toast.LENGTH_SHORT).show()
                    } else {
                        item.setIcon(R.drawable.ic_button_unfav)
                        Toast.makeText(requireContext(), "Removed from Favourite", Toast.LENGTH_SHORT).show()
                    }
                    true
                }
                R.id.action_share -> {
                    shareGame()
                    true
                }
                else -> false
            }
        }
    }

    private fun goToWebsite(url:String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, url.toUri())
            startActivity(intent)
        } catch (e:Exception) {
            Toast.makeText(requireContext(), "Tidak ada browser yang tersedia", Toast.LENGTH_SHORT).show()
        }
     }

    private fun shareGame() {
        val shareText = "Cek game seru ini: ${gameDetail.name}\nWebsite resmi: ${gameDetail.website}"
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, shareText)
        }
        requireContext().startActivity(Intent.createChooser(shareIntent, "Bagikan via"))
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}