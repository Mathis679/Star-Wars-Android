package com.example.domain.di

import com.example.domain.interactor.GetAllFilms
import com.example.domain.interactor.GetFilm
import org.koin.dsl.module

val useCaseModule = module {
    factory { GetAllFilms(filmRepository = get(), schedulerProvider = get()) }
    factory { GetFilm(filmRepository = get(), schedulerProvider = get()) }
}