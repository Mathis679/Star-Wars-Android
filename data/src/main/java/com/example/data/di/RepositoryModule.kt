package com.example.data.di

import com.example.data.repository.film.FilmRepositoryImpl
import com.example.domain.repository.FilmRepository
import org.koin.dsl.module

val repositoryModule = module {

    single<FilmRepository> { FilmRepositoryImpl(filmService = get()) }
}
