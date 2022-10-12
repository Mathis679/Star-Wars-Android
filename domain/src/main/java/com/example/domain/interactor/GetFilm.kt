package com.example.domain.interactor

import com.example.domain.exception.Failure
import com.example.domain.executor.SchedulerProvider
import com.example.domain.functional.Either
import com.example.domain.model.Film
import com.example.domain.repository.FilmRepository
import io.reactivex.Observable

class GetFilm(
        private val filmRepository: FilmRepository,
        private val schedulerProvider: SchedulerProvider
) : UseCase<Film, GetFilm.Params>(schedulerProvider) {

    override fun buildObservable(params: Params): Observable<Either<Failure, Film>> {
        return filmRepository.getFilm(params.id)
    }

    data class Params(
            val id: Int
    )
}