package com.code.id.pokemonapp.data.remote

import com.code.id.pokemonapp.domain.model.PokemonResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PokemonApiService {
    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("offset") offset: String? = "0",
        @Query("limit") limit: String? = "20"
    ): Response<PokemonResponse>
}