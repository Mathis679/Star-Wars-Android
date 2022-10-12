package com.example.presentation.di

import com.example.presentation.FeedFilmViewModel
import com.example.presentation.FeedFilmViewModelImpl
import com.example.utils.constants.FEED_FILMS_SCOPE_NAME
import org.koin.core.qualifier.named
import org.koin.dsl.module

val presentationModule = module {

    scope(named(FEED_FILMS_SCOPE_NAME)) {
        scoped<FeedFilmViewModel> {
            FeedFilmViewModelImpl(
                    getAllFilms = get()
            )
        }
    }
}