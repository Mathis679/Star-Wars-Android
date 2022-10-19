package com.example.presentation

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.domain.model.Film
import com.example.utils.extension.getViewModelScope
import org.koin.core.scope.Scope


@Composable
fun FeedFilms(viewModelScope: Scope) {
    val feedFilmViewModel =
        viewModelScope.getViewModelScope<FeedFilmViewModel>()
    val films by feedFilmViewModel?.films?.collectAsState() as State<List<Film>>

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

@Composable
fun FilmItem(film: Film){
    Text(
        text = film.title
    )
}

@Composable
fun LoadingView(){
    val imageAnimatable = remember {
        Animatable(0f)
    }
    LaunchedEffect(key1 = imageAnimatable){
        imageAnimatable.animateTo(
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = keyframes {
                    durationMillis = 1000
                    1.00f at 800 with LinearOutSlowInEasing
                },
                repeatMode = RepeatMode.Restart
            )
        )
    }
    Box(modifier = Modifier
        .fillMaxSize()
        .graphicsLayer {
            rotationZ = imageAnimatable.value * 360
        },
        contentAlignment = Alignment.Center
    ){
        LoadingImage()
    }
}

@Composable
fun LoadingImage(){
    Image(
        painterResource(R.drawable.ic_stormtrooper),
        contentDescription = "stormtrooper loading",
        contentScale = ContentScale.Crop,
        modifier = Modifier.size(150.dp)
    )
}