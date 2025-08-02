package com.code.id.pokemonapp.domain.usecase

import com.code.id.pokemonapp.data.repository.IPokemonRepository
import com.code.id.pokemonapp.domain.model.PokemonDetailResponse
import com.code.id.pokemonapp.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DetailUseCase @Inject constructor(
    private val repository: IPokemonRepository
): IDetailUseCase {
    override suspend fun getDetailPokemon(url: String): Flow<Resource<PokemonDetailResponse>> {
        return repository.getPokemonDetail(url)
    }
}