package com.code.id.pokemonapp.data.repository

import com.code.id.pokemonapp.data.local.PokemonDbSqLite
import com.code.id.pokemonapp.data.remote.PokemonApiService
import com.code.id.pokemonapp.domain.model.PokemonDetailResponse
import com.code.id.pokemonapp.domain.model.PokemonResponse
import com.code.id.pokemonapp.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val remoteService: PokemonApiService,
    private val localService: PokemonDbSqLite
) : IPokemonRepository {
    override suspend fun getPokemonList(
        offset: String,
        limit: String
    ): Flow<Resource<PokemonResponse>> = flow {
        try {
            val response = remoteService.getPokemonList(offset, limit)
            if (response.isSuccessful) {
                localService.insertAll(response.body()?.results)
                emit(Resource.Success(response.body()))
            }
        } catch (e: Exception) {
            var dataLocal = PokemonResponse(results = localService.getAllPokemon())
            emit(Resource.Success(dataLocal))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getPokemonDetail(url: String): Flow<Resource<PokemonDetailResponse>> = flow {
        try {
            val response = remoteService.getDetailPokemon(url)
            if (response.isSuccessful) {
                emit(Resource.Success(response.body()))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.message.toString()))
        }
    }.flowOn(Dispatchers.IO)
}
