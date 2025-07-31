package com.code.id.pokemonapp.domain.usecase

import com.code.id.pokemonapp.data.repository.IPokemonRepository
import javax.inject.Inject

class HomeUseCase @Inject constructor(
    private val repository: IPokemonRepository
): IHomeUseCase {
}