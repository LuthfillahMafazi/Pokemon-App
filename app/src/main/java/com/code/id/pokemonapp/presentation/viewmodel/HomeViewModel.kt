package com.code.id.pokemonapp.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.code.id.pokemonapp.data.local.PreferenceManager
import com.code.id.pokemonapp.domain.model.PokemonItem
import com.code.id.pokemonapp.domain.model.PokemonResponse
import com.code.id.pokemonapp.domain.usecase.IHomeUseCase
import com.code.id.pokemonapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val useCase: IHomeUseCase,
    private val preferenceManager: PreferenceManager
) : ViewModel() {

    private val _pokemonResponse = MutableStateFlow<PokemonResponse?>(null)
    val pokemonResponse get() = _pokemonResponse.asStateFlow()

    private val _searchResults = MutableStateFlow<List<PokemonItem>>(emptyList())
    val searchResults get() = _searchResults.asStateFlow()

    private val _isLogin = MutableStateFlow<Boolean>(false)
    val isLogin get() = _isLogin.asStateFlow()

    fun checkStatusLogin() {
        viewModelScope.launch(Dispatchers.IO) {
            _isLogin.emit(preferenceManager.isLoggedIn())
        }
    }

    fun getPokemonList(offset: String, limit: String) {
        CoroutineScope(Dispatchers.IO).launch {
            useCase.getPokemonList(offset, limit).collect {
                when (it) {
                    is Resource.Success -> {
                        val newData = it.data

                        val oldList =
                            _pokemonResponse.value?.results ?: emptyList()
                        val updatedList = oldList + (newData?.results ?: emptyList())

                        _pokemonResponse.emit(newData?.copy(results = updatedList))
                    }

                    else -> Unit
                }
            }
        }
    }

    fun searchPokemon(searchText: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val dataList = _pokemonResponse.value?.results.orEmpty()

            val searchList = if (searchText.isNotBlank()) {
                dataList.filter {
                    it.name?.contains(searchText.trim(), ignoreCase = true) == true
                }
            } else {
                dataList
            }

            _searchResults.emit(searchList)
        }
    }
}