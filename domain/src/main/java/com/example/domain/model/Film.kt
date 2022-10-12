package com.example.domain.model

import java.util.*

data class Film(
    val id: Int,
    val title: String,
    val openingCrawl: String,
    val director: String,
    val producer: String,
    val releaseDate: Date,
    val characters: List<String>,
    val planets: List<String>,
    val starships: List<String>,
    val vehicles: List<String>,
    val species: List<String>
)