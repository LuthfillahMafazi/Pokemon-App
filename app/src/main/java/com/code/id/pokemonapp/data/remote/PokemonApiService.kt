package com.code.id.pokemonapp.data.remote

import com.code.id.pokemonapp.domain.model.PokemonDetailResponse
import com.code.id.pokemonapp.domain.model.PokemonResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface PokemonApiService {
    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("offset") offset: String? = "0",
        @Query("limit") limit: String? = "20"
    ): Response<PokemonResponse>

    @GET
    suspend fun getDetailPokemon(
        @Url url: String
    ): Response<PokemonDetailResponse>
}