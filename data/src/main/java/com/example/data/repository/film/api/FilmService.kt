package com.example.data.repository.film.api

import com.example.data.retrofitutils.GET_ALL_FILMS
import com.example.data.retrofitutils.GET_FILM
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface FilmService {

    @GET(GET_ALL_FILMS)
    fun getAllFilms(): Observable<Response<FilmListDto>>

    @GET(GET_FILM)
    fun getFilm(@Path("id") id: String): Observable<Response<FilmDto>>
}