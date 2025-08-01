package com.code.id.pokemonapp.presentation.view.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.code.id.pokemonapp.R
import com.code.id.pokemonapp.data.local.PreferenceManager
import com.code.id.pokemonapp.databinding.FragmentHomeBinding
import com.code.id.pokemonapp.presentation.view.adapter.PokemonAdapter
import com.code.id.pokemonapp.presentation.viewmodel.HomeViewModel
import com.code.id.pokemonapp.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding

    @Inject
    lateinit var preferenceManager: PreferenceManager

    private val adapter: PokemonAdapter by lazy {
        PokemonAdapter {

        }
    }

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkUserLogin()
        viewModel.getPokemonList("0", "20")
        setupAdapter()
        observeListPokemon()

    }

    private fun checkUserLogin() {
        if (preferenceManager.isLoggedIn() == false) {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToNavAuth())
        }
    }

    private fun observeListPokemon() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.pokemonResponse.collect {
                    adapter.submitList(it?.results?.toMutableList())
                }
            }
        }
    }

    private fun setupAdapter() {
        binding?.apply {
            rvPokemon.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            rvPokemon.adapter = adapter
        }
    }
}