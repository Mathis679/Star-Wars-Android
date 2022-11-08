package com.example.domain.repository

import com.example.domain.exception.Failure
import com.example.domain.functional.Either
import com.example.domain.model.Film

interface FilmRepository {

    suspend fun getAllFilms(): Either<Failure, List<Film>>

    suspend fun getFilm(id: Int): Either<Failure, Film>

}