package com.code.id.pokemonapp.presentation.view.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.code.id.pokemonapp.databinding.FragmentDetailBinding
import com.code.id.pokemonapp.domain.model.PokemonDetailResponse
import com.code.id.pokemonapp.presentation.view.adapter.PokemonAbilityAdapter
import com.code.id.pokemonapp.presentation.viewmodel.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding

    private val viewModel: DetailViewModel by viewModels()

    private val args by navArgs<DetailFragmentArgs>()

    private val adapter: PokemonAbilityAdapter by lazy {
        PokemonAbilityAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getDetailPokemon(args.pokemonItem.url ?: "")
        setupAdapter()
        observeDetailPokemon()
        observeError()
    }

    private fun observeError() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.errorResult.collect {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                }
            }
        }    }

    private fun setupAdapter() {
        binding?.apply {
            rvAbility.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            rvAbility.adapter = adapter
        }
    }

    private fun observeDetailPokemon() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.pokemonDetailResponse.collect {
                    initView(it)
                    adapter.submitList(it?.abilities?.toMutableList())
                }
            }
        }
    }

    private fun initView(response: PokemonDetailResponse?) {
        binding?.apply {
            Glide.with(requireContext()).load(response?.sprites?.front_default).into(imgPokemon)
            tvName.text = args.pokemonItem.name
        }
    }

}