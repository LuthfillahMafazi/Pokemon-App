package com.code.id.pokemonapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.code.id.pokemonapp.domain.model.PokemonResponse
import com.code.id.pokemonapp.domain.usecase.IHomeUseCase
import com.code.id.pokemonapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val useCase: IHomeUseCase
): ViewModel() {

    private val _pokemonResponse = MutableSharedFlow<PokemonResponse?>(replay = 0)
    val pokemonResponse get() = _pokemonResponse.asSharedFlow()

    fun getPokemonList(offset: String, limit: String) {
        CoroutineScope(Dispatchers.IO).launch {
            useCase.getPokemonList(offset, limit).collect {
                when (it) {
                    is Resource.Success -> {
                        _pokemonResponse.emit(it.data)
                    }

                    else -> Unit
                }
            }
        }
    }
}