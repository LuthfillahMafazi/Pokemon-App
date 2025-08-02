package com.code.id.pokemonapp.domain.usecase

import com.code.id.pokemonapp.domain.model.PokemonDetailResponse
import com.code.id.pokemonapp.utils.Resource
import kotlinx.coroutines.flow.Flow

interface IDetailUseCase {
    suspend fun getDetailPokemon(url: String): Flow<Resource<PokemonDetailResponse>>
}