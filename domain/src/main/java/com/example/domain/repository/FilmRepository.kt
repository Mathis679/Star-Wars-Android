package com.example.domain.repository

import com.example.domain.exception.Failure
import com.example.domain.functional.Either
import com.example.domain.model.Film
import io.reactivex.Observable

interface FilmRepository {

    fun getAllFilms(): Observable<Either<Failure, List<Film>>>

    fun getFilm(id: Int): Observable<Either<Failure, Film>>

}