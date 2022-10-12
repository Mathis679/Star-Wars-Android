package com.example.data.repository.film.api

import com.google.gson.annotations.SerializedName
import java.util.*

data class FilmDto (
        @SerializedName("url")
        val url: String?,

        @SerializedName("title")
        val title: String?,

        @SerializedName("opening_crawl")
        val openingCrawl: String?,

        @SerializedName("director")
        val director: String?,

        @SerializedName("producer")
        val producer: String?,

        @SerializedName("release_date")
        val releaseDate: String?,

        @SerializedName("characters")
        val characters: List<String>?,

        @SerializedName("planets")
        val planets: List<String>?,

        @SerializedName("starships")
        val starships: List<String>?,

        @SerializedName("vehicles")
        val vehicles: List<String>?,

        @SerializedName("species")
        val species: List<String>?
        )