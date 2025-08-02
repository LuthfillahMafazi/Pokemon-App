package com.code.id.pokemonapp.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PokemonDetailResponse (
    val abilities: List<AbilitiesData>? = null,
    val sprites: SpritesItem? = null
): Parcelable

@Parcelize
data class AbilitiesData (
    val ability: AbilityItem? = null
): Parcelable

@Parcelize
data class AbilityItem (
    val name: String? = null,
    val url: String? = null
): Parcelable

@Parcelize
data class SpritesItem (
    val front_default: String? = null
): Parcelable