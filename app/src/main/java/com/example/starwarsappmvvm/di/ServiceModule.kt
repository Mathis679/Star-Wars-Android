package com.example.starwarsappmvvm.di

import com.example.data.repository.film.api.FilmService
import org.koin.dsl.module
import retrofit2.Retrofit

val serviceModule = module {

    single { provideFilmService(retrofit = get()) }

}

private fun provideFilmService(retrofit: Retrofit): FilmService{
    return retrofit.create(FilmService::class.java)
}