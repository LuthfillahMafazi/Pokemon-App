package com.code.id.pokemonapp.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PokemonResponse (
    val count: Int? = 0,
    val next: String? = null,
    val results: List<PokemonItem>? = null
): Parcelable

@Parcelize
data class PokemonItem (
    val name: String? = null,
    val url: String? = null
): Parcelable