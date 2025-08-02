package com.code.id.pokemonapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.code.id.pokemonapp.domain.model.PokemonDetailResponse
import com.code.id.pokemonapp.domain.usecase.IDetailUseCase
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
class DetailViewModel @Inject constructor(
    private val useCase: IDetailUseCase
): ViewModel() {

    private val _pokemonDetailResponse = MutableStateFlow<PokemonDetailResponse?>(null)
    val pokemonDetailResponse get() = _pokemonDetailResponse.asStateFlow()

    private val _errorResult = MutableSharedFlow<String?>(replay = 0)
    val errorResult get() = _errorResult.asSharedFlow()

    fun getDetailPokemon(url: String) {
        CoroutineScope(Dispatchers.IO).launch {
            useCase.getDetailPokemon(url).collect {
                when (it) {
                    is Resource.Success -> {
                        _pokemonDetailResponse.emit(it.data)
                    }

                    is Resource.Error -> {
                        _errorResult.emit(it.message)
                    }

                    else -> Unit
                }
            }
        }
    }

}