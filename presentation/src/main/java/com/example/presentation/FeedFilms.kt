package com.example.presentation

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import com.example.domain.model.Film
import com.example.utils.extension.getViewModelScope
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import org.koin.core.scope.Scope


@OptIn(ExperimentalPagerApi::class)
@Composable
fun FeedFilms(viewModelScope: Scope) {
    val feedFilmViewModel =
        viewModelScope.getViewModelScope<FeedFilmViewModel>()
    val films by feedFilmViewModel?.films?.collectAsState() as State<List<Film>>

    if(films.isEmpty()){
        LoadingView()
    }

    HorizontalPager(
        count = films.size
    ) {
        FilmItem(film = films[it], Modifier
            .fillMaxWidth()
            .padding(32.dp)
            .graphicsLayer {
                // Calculate the absolute offset for the current page from the
                // scroll position. We use the absolute value which allows us to mirror
                // any effects for both directions
                val pageOffset = calculateCurrentOffsetForPage(it)

                // We animate the scaleX + scaleY, between 85% and 100%
                lerp(
                    start = 0.85f,
                    stop = 1f,
                    fraction = 1f - pageOffset.coerceIn(0f, 1f)
                ).also { scale ->
                    scaleX = scale
                    scaleY = scale
                }

                // We animate the alpha, between 50% and 100%
                alpha = lerp(
                    start = 0.5f,
                    stop = 1f,
                    fraction = 1f - pageOffset.coerceIn(0f, 1f)
                )
            }
        )
    }
}

@Composable
fun FilmItem(film: Film, modifier: Modifier){
    LazyColumn (modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally){
        item {
            TitleFilm(film = film)
        }
        item {
            ContentFilm(film = film)
        }
    }

}

@Composable
fun TitleFilm(film: Film){
    Surface(
        color = MaterialTheme.colors.primaryVariant,
        shape = MaterialTheme.shapes.medium,
        elevation = 8.dp
    ) {
        Text(
            text = film.title,
            modifier = Modifier
                .padding(16.dp),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun ContentFilm(film: Film){
    Text(
        text = film.openingCrawl,
        modifier = Modifier
            .padding(top = 16.dp),
        fontSize = 16.sp,
        textAlign = TextAlign.Center
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