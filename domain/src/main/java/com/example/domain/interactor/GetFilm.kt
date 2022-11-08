package com.example.domain.interactor

import com.example.domain.exception.Failure
import com.example.domain.functional.Either
import com.example.domain.model.Film
import com.example.domain.repository.FilmRepository

class GetFilm(
        private val filmRepository: FilmRepository
) : UseCase<Film, GetFilm.Params>() {

    override suspend fun call(params: Params): Either<Failure, Film> {
        return filmRepository.getFilm(params.id)
    }

    data class Params(
            val id: Int
    )
}