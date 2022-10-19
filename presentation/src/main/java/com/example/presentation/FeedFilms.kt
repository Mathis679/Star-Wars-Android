package com.example.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.domain.model.Film

@Composable
fun FeedFilms(films: List<Film>) {
    Surface(
        color = MaterialTheme.colors.primary,
        modifier = Modifier.fillMaxSize()
    ) {
        if(films.isEmpty()){
            LoadingView()
        }
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
    Text(
        text = film.title
    )
}

@Composable
fun LoadingView(){
    Image(
        painterResource(R.drawable.ic_stormtrooper),
        contentDescription = "stormtrooper loading",
        contentScale = ContentScale.Crop,
        modifier = Modifier.size(50.dp)
    )
}