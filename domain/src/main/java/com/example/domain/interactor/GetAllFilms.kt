package com.example.domain.interactor

import com.example.domain.exception.Failure
import com.example.domain.functional.Either
import com.example.domain.model.Film
import com.example.domain.repository.FilmRepository

class GetAllFilms(
        private val filmRepository: FilmRepository
) : UseCase<List<Film>, Any>() {

    override suspend fun call(params: Any): Either<Failure, List<Film>> {
        return filmRepository.getAllFilms()
    }

}