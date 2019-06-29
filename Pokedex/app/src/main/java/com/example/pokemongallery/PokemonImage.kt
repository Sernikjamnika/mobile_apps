package com.example.pokemongallery

import java.io.Serializable

data class PokemonImage (
    val url: String,
    val description: String,
    var name: String,
    var rating: Float
): Serializable