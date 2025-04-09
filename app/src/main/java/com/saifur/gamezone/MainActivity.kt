package com.saifur.gamezone

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import com.saifur.gamezone.core.utils.FavouriteNavigator
import com.saifur.gamezone.databinding.ActivityMainBinding
import com.saifur.gamezone.di.GameZoneApp
import androidx.core.net.toUri
import androidx.core.view.isVisible

class MainActivity : AppCompatActivity(), FavouriteNavigator {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    private lateinit var destinationListener: NavController.OnDestinationChangedListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        (application as GameZoneApp).injectDynamicModules()

        val navView: BottomNavigationView = binding.navView
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        navView.setupWithNavController(navController)
        bottomNavItemChangeListener(navView)
        setupNavControllerDestinationChangeListener()
    }

    private fun bottomNavItemChangeListener(navView: BottomNavigationView) {
        navView.setOnItemSelectedListener { item ->
            if (item.itemId != navView.selectedItemId) {
                navController.popBackStack(item.itemId, inclusive = true, saveState = false)
                if (item.itemId == R.id.navigation_favourite) {
                    openFavouriteFragment()
                } else {
                    navController.navigate(item.itemId)
                }
            }
            true
        }
    }

    private fun setupNavControllerDestinationChangeListener() {
        destinationListener = NavController.OnDestinationChangedListener { _, destination, _ ->
            binding.navView.isVisible = destination.id != R.id.detailFragment
        }
        navController.addOnDestinationChangedListener(destinationListener)
    }

    override fun navigateToDetail(id: Int) {
        val uri = "gamezone://detail/$id".toUri()
        navController.navigate(uri)
    }

    @SuppressLint("DiscouragedApi")
    private fun openFavouriteFragment() {
        val graphResId = resources.getIdentifier(
            "favourite_nav_graph", // nama file nav_graph di dynamic module
            "navigation",
            "com.saifur.gamezone.favourite"
        )

        val splitInstallManager = SplitInstallManagerFactory.create(this)

        val request = SplitInstallRequest.newBuilder()
            .addModule("favourite")
            .build()

        splitInstallManager.startInstall(request)
            .addOnSuccessListener {
                if (graphResId != 0) {
                    val graph = navController.navInflater.inflate(graphResId)
                    navController.graph.addAll(graph)
                    val uri = "gamezone://favourite".toUri()
                    navController.navigate(uri)
                } else {
                    Toast.makeText(this, "Module Not Found", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener {
                navController.navigate(R.id.moduleNotFoundFragment)
            }
    }

    override fun onDestroy() {
        navController.removeOnDestinationChangedListener(destinationListener)
        super.onDestroy()
    }
}