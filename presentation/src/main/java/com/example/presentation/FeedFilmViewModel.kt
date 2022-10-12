package com.example.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.domain.exception.Failure
import com.example.domain.functional.Either
import com.example.domain.interactor.GetAllFilms
import com.example.domain.model.Film

abstract class FeedFilmViewModel(
        protected val getAllFilms: GetAllFilms
) : BaseViewModel(getAllFilms){

    abstract fun loadAllFilms(): LiveData<Either<Failure, List<Film>>>
}

class FeedFilmViewModelImpl(
        getAllFilms: GetAllFilms
): FeedFilmViewModel(getAllFilms){

    override fun loadAllFilms(): LiveData<Either<Failure, List<Film>>> {
        val obsResult = MutableLiveData<Either<Failure, List<Film>>>()
        getAllFilms(Any()){
            obsResult.postValue(it)
        }
        return obsResult
    }
}

