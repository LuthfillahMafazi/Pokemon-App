package com.code.id.pokemonapp.domain.usecase

import com.code.id.pokemonapp.data.repository.IPokemonRepository
import com.code.id.pokemonapp.domain.model.PokemonResponse
import com.code.id.pokemonapp.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HomeUseCase @Inject constructor(
    private val repository: IPokemonRepository
): IHomeUseCase {
    override suspend fun getPokemonList(
        offset: String,
        limit: String
    ): Flow<Resource<PokemonResponse>> {
        return repository.getPokemonList(offset, limit)
    }
}