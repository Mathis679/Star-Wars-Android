package com.example.data.repository.film.api

import com.example.data.retrofitutils.GET_ALL_FILMS
import com.example.data.retrofitutils.GET_FILM
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface FilmService {

    @GET(GET_ALL_FILMS)
    suspend fun getAllFilms(): Response<FilmListDto>

    @GET(GET_FILM)
    suspend fun getFilm(@Path("id") id: String): Response<FilmDto>
}