package com.example.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.domain.model.Film

@Composable
fun FeedFilms(films: List<Film>) {
    Surface(Modifier.fillMaxSize()) {
        LazyColumn{
            items(
                films.size,
                key = {
                    films[it].id
                }
            ) {
                FilmItem(film = films[it])
            }
        }
    }
}

@Composable
fun FilmItem(film: Film){
    Text(text = film.title)
}