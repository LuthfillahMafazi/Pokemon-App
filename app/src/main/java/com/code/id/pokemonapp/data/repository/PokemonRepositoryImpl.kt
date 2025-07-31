package com.code.id.pokemonapp.data.repository

import com.code.id.pokemonapp.data.local.PokemonDbSqLite
import com.code.id.pokemonapp.data.remote.PokemonApiService
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val remoteService: PokemonApiService,
    private val localService: PokemonDbSqLite
): IPokemonRepository {
}