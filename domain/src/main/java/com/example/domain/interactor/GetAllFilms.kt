package com.example.domain.interactor

import com.example.domain.exception.Failure
import com.example.domain.executor.SchedulerProvider
import com.example.domain.functional.Either
import com.example.domain.model.Film
import com.example.domain.repository.FilmRepository
import io.reactivex.Observable

class GetAllFilms(
        private val filmRepository: FilmRepository,
        private val schedulerProvider: SchedulerProvider
) : UseCase<List<Film>, Any>(schedulerProvider) {

    override fun buildObservable(params: Any): Observable<Either<Failure, List<Film>>> {
        return filmRepository.getAllFilms()
    }

}