package com.example.data.repository.film

import com.example.data.extension.toEither
import com.example.data.mapper.FilmMapper.Companion.toFilm
import com.example.data.mapper.FilmMapper.Companion.toFilmList
import com.example.data.repository.film.api.FilmService
import com.example.domain.exception.Failure
import com.example.domain.functional.Either
import com.example.domain.model.Film
import com.example.domain.repository.FilmRepository

class FilmRepositoryImpl(private val filmService: FilmService): FilmRepository {

    override suspend fun getAllFilms(): Either<Failure, List<Film>> {
        return filmService.getAllFilms().toEither(::toFilmList)
    }

    override suspend fun getFilm(id: Int): Either<Failure, Film> {
        return filmService.getFilm(id.toString()).toEither(::toFilm)
    }
}