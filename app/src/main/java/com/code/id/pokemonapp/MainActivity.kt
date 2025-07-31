package com.code.id.pokemonapp

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.code.id.pokemonapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding

    private val navGraphIds = setOf(
        R.navigation.nav_home,
        R.navigation.nav_profile
    )

    private val bottomNavDestinations = setOf(
        R.id.homeFragment,
        R.id.profileFragment
    )

    private var backPressedOnce = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        _binding = ActivityMainBinding.inflate(layoutInflater)
        val view: View? = binding?.root
        setContentView(view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val navGraphIdMap = navGraphIds.associateWith { navGraphId ->
            val fragmentTag = "bottom_nav_$navGraphId"
            val navHostFragment = supportFragmentManager.findFragmentByTag(fragmentTag)
                    as? NavHostFragment ?: NavHostFragment.create(navGraphId).also {
                supportFragmentManager.beginTransaction()
                    .add(R.id.nav_host_fragment, it, fragmentTag)
                    .hide(it)
                    .commitNow()
            }
            navHostFragment
        }

        var currentNavController: NavController? = null

        binding?.bottomNavigation?.setOnItemSelectedListener { item ->
            val selectedGraphId = when (item.itemId) {
                R.id.nav_home -> R.navigation.nav_home
                R.id.nav_profile -> R.navigation.nav_profile
                else -> return@setOnItemSelectedListener false
            }

            navGraphIdMap.forEach { (id, navHostFragment) ->
                supportFragmentManager.beginTransaction().hide(navHostFragment).commit()
            }

            val selectedNavHostFragment =
                navGraphIdMap[selectedGraphId] ?: return@setOnItemSelectedListener false
            supportFragmentManager.beginTransaction().show(selectedNavHostFragment).commit()
            currentNavController = selectedNavHostFragment.navController

            navGraphIdMap.values.forEach { navHostFragment ->
                navHostFragment.navController.addOnDestinationChangedListener { _, destination, _ ->
                    if (destination.id in bottomNavDestinations) {
                        binding?.bottomNavigation?.visibility = View.VISIBLE
                    } else {
                        binding?.bottomNavigation?.visibility = View.GONE
                    }
                }
            }
            true
        }

        val defaultGraphId = navGraphIds.first()
        binding?.bottomNavigation?.selectedItemId = R.id.nav_home
        supportFragmentManager.beginTransaction().show(navGraphIdMap[defaultGraphId]!!).commit()
        currentNavController = navGraphIdMap[defaultGraphId]?.navController

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val currentDestination = currentNavController?.currentDestination?.id

                if (currentDestination in bottomNavDestinations) {
                    if (backPressedOnce) {
                        finish()
                    } else {
                        backPressedOnce = true
                        Toast.makeText(
                            this@MainActivity,
                            "Press back again to exit",
                            Toast.LENGTH_SHORT
                        ).show()
                        Handler(Looper.getMainLooper()).postDelayed(
                            { backPressedOnce = false },
                            2000
                        )
                    }
                } else {
                    currentNavController?.navigateUp()
                }
            }
        })
    }
}