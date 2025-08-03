package com.code.id.pokemonapp.presentation.view.home

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.code.id.pokemonapp.data.local.PreferenceManager
import com.code.id.pokemonapp.databinding.FragmentHomeBinding
import com.code.id.pokemonapp.domain.model.PokemonItem
import com.code.id.pokemonapp.presentation.view.adapter.PokemonAdapter
import com.code.id.pokemonapp.presentation.viewmodel.HomeViewModel
import com.code.id.pokemonapp.utils.NetworkUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding

    private var offset = 0
    private var isLoading = false

    var dataList: List<PokemonItem>? = null

    @Inject
    lateinit var preferenceManager: PreferenceManager

    private val adapter: PokemonAdapter by lazy {
        PokemonAdapter {
            if (NetworkUtil.isConnected(requireContext())) {
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToDetailFragment(it)
                )
            } else {
                Toast.makeText(requireContext(), "Check your connection", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private val viewModel: HomeViewModel by activityViewModels()

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
        viewModel.getPokemonList(offset.toString(), "10")
        setupAdapter()
        initSearch()
        observeSearch()
        observeListPokemon()

    }

    private fun observeSearch() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.searchResults.filterNotNull().collect {
                    adapter.submitList(it.toMutableList())
                }
            }
        }
    }

    private fun initSearch() {
        binding?.apply {
            etSearch.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    p0: CharSequence?, p1: Int, p2: Int, p3: Int
                ) {
                }

                override fun onTextChanged(
                    p0: CharSequence?, p1: Int, p2: Int, p3: Int
                ) {
                    viewModel.searchPokemon(p0.toString())
                }

                override fun afterTextChanged(p0: Editable?) {
                }

            })
        }
    }

    private fun checkUserLogin() {
        if (preferenceManager.isLoggedIn() == false) {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToNavAuth())
        } else {
            viewModel.checkStatusLogin()
        }
    }

    private fun observeListPokemon() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.pokemonResponse.collect {
                    if (it?.results != dataList) {
                        isLoading = false
                        adapter.submitList(it?.results?.toMutableList())
                        dataList = it?.results
                    }
                }
            }
        }
    }

    private fun setupAdapter() {
        binding?.apply {
            rvPokemon.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            rvPokemon.adapter = adapter

            rvPokemon.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    if (dy <= 0 || isLoading) return

                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val visibleItemCount = layoutManager.childCount
                    val totalItemCount = layoutManager.itemCount
                    val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount - 2 && NetworkUtil.isConnected(
                            requireContext()
                        )
                    ) {
                        isLoading = true
                        offset += 10
                        viewModel.getPokemonList(offset.toString(), "10")
                    }
                }
            })
        }
    }
}