package com.example.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.domain.exception.Failure
import com.example.domain.functional.Either
import com.example.domain.interactor.GetAllFilms
import com.example.domain.model.Film
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

abstract class FeedFilmViewModel(
        protected val getAllFilms: GetAllFilms
) : BaseViewModel(getAllFilms){

    abstract val films: StateFlow<List<Film>>
}

class FeedFilmViewModelImpl(
        getAllFilms: GetAllFilms
): FeedFilmViewModel(getAllFilms){

    private val _films = MutableStateFlow<List<Film>>(emptyList())
    override val films: StateFlow<List<Film>> = _films

    init {
        loadAllFilms()
    }

    private fun loadAllFilms() {
        getAllFilms(Any(), viewModelScope){
            it.fold({

            }, {
                _films.value = it
                Any()
            })
        }
    }
}

