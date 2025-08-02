package com.code.id.pokemonapp.data.repository

import com.code.id.pokemonapp.domain.model.PokemonDetailResponse
import com.code.id.pokemonapp.domain.model.PokemonResponse
import com.code.id.pokemonapp.utils.Resource
import kotlinx.coroutines.flow.Flow

interface IPokemonRepository {
    suspend fun getPokemonList(offset: String, limit: String): Flow<Resource<PokemonResponse>>
    suspend fun getPokemonDetail(url: String): Flow<Resource<PokemonDetailResponse>>
}