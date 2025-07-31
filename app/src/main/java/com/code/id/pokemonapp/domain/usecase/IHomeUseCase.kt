package com.code.id.pokemonapp.domain.usecase

import com.code.id.pokemonapp.domain.model.PokemonResponse
import com.code.id.pokemonapp.utils.Resource
import kotlinx.coroutines.flow.Flow

interface IHomeUseCase {
    suspend fun getPokemonList(offset: String, limit: String): Flow<Resource<PokemonResponse>>
}