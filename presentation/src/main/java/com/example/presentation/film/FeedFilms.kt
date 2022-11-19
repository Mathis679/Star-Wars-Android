package com.example.presentation

import android.util.Log
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import com.example.domain.model.Film
import com.example.utils.DateUtil
import com.example.utils.extension.getViewModelScope
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.google.accompanist.pager.rememberPagerState
import org.koin.core.scope.Scope


@OptIn(ExperimentalPagerApi::class)
@Composable
fun FeedFilms(viewModelScope: Scope) {
    val feedFilmViewModel =
        viewModelScope.getViewModelScope<FeedFilmViewModel>()
    val films by feedFilmViewModel?.films?.collectAsState() as State<List<Film>>

    var filmPosition by remember { mutableStateOf(0) }

    if(films.isEmpty()){
        LoadingView()
    }

    Column {
        Text(
            text = "Film's list",
            fontSize = 28.sp,
            fontWeight = FontWeight.ExtraBold,
            textAlign = TextAlign.Start,
            color = Color.White,
            modifier = Modifier
                .padding(16.dp, 16.dp, 0.dp, 0.dp)
        )
        Text(
            text = "Here is the film's list...",
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Start,
            color = Color.White,
            modifier = Modifier
                .padding(16.dp, 0.dp, 0.dp, 0.dp)
                .alpha(0.7f)
        )
        FilmsIndicator(numberOfItems = films.size, currentPage = filmPosition)
        val pagerState = rememberPagerState()

        LaunchedEffect(pagerState) {
            // Collect from the pager state a snapshotFlow reading the currentPage
            snapshotFlow { pagerState.currentPage }.collect { page ->
                filmPosition = page
            }
        }
        HorizontalPager(
            count = films.size,
            state = pagerState
        ) {
            FilmItem(film = films[it], Modifier
                .fillMaxWidth()
                .padding(0.dp, 16.dp, 0.dp, 0.dp)
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


}

@Composable
fun FilmsIndicator(numberOfItems: Int, currentPage: Int){
    if(numberOfItems > 0){
        Row(modifier = Modifier.padding(16.dp, 16.dp, 16.dp, 8.dp)) {
            for (i in 0 until numberOfItems){
                Divider(modifier = Modifier.weight(1f).padding(4.dp), thickness = 2.dp, color = if(currentPage == i) Color.White else MaterialTheme.colors.primaryVariant)
            }
        }
    }

}

@Composable
fun FilmItem(film: Film, modifier: Modifier){
    Surface(modifier = modifier, shape = RoundedCornerShape(28.dp, 28.dp, 0.dp, 0.dp)) {
        Column (
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            TitleFilm(film = film)
            Divider(thickness = 2.dp, color = MaterialTheme.colors.primary)
            ContentFilm(film = film)
        }
    }
}

@Composable
fun TitleFilm(film: Film){
    Surface(
        color = MaterialTheme.colors.onPrimary
    ) {
        Text(
            text = film.title,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = Color.White,
            fontFamily = FontFamily.Monospace
        )
    }
}

@Composable
fun ContentFilm(film: Film){
    Surface(color = MaterialTheme.colors.onPrimary, modifier = Modifier.fillMaxWidth()) {
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.verticalScroll(rememberScrollState())
        ){

                Text(
                    text = DateUtil.displayDate(film.releaseDate),
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .alpha(0.7f),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = Color.White
                )


                Text(
                    text = film.openingCrawl,
                    modifier = Modifier
                        .padding(top = 16.dp),
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    color = Color.White
                )


                Row {
                    Text(
                        text = "director : ",
                        modifier = Modifier
                            .padding(top = 16.dp),
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = film.director,
                        modifier = Modifier
                            .padding(top = 16.dp),
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center,
                        color = Color.White
                    )
                }


                Row {
                    Text(
                        text = "producer : ",
                        modifier = Modifier
                            .padding(top = 16.dp),
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = film.producer,
                        modifier = Modifier
                            .padding(top = 16.dp),
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center,
                        color = Color.White
                    )
                }

                Button(
                    onClick = {

                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.background),
                    modifier = Modifier
                        .padding(20.dp, 12.dp),
                    shape = RoundedCornerShape(50)
                ) {
                    Text(
                        text = "View characters",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                }

                Button(
                    onClick = {

                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.background),
                    modifier = Modifier
                        .padding(20.dp, 12.dp),
                    shape = RoundedCornerShape(50)
                ) {
                    Text(
                        text = "View planets",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                }

                Button(
                    onClick = {

                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.background),
                    modifier = Modifier
                        .padding(20.dp, 12.dp),
                    shape = RoundedCornerShape(50)
                ) {
                    Text(
                        text = "View starships",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                }

                Button(
                    onClick = {

                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.background),
                    modifier = Modifier
                        .padding(20.dp, 12.dp),
                    shape = RoundedCornerShape(50)
                ) {
                    Text(
                        text = "View vehicles",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                }

                Button(
                    onClick = {

                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.background),
                    modifier = Modifier
                        .padding(20.dp, 12.dp),
                    shape = RoundedCornerShape(50)

                ) {
                    Text(
                        text = "View species",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                }

        }
    }
}

@Composable
fun LoadingView(){
    var width by remember {
        mutableStateOf(0)
    }
    val scaleImageAnimatable = remember {
        Animatable(0f)
    }
    LaunchedEffect(key1 = scaleImageAnimatable){
        scaleImageAnimatable.animateTo(
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
    val translateImageAnimatable = remember {
        Animatable(0f)
    }
    LaunchedEffect(key1 = translateImageAnimatable){
        translateImageAnimatable.animateTo(
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = keyframes {
                    durationMillis = 4000
                },
                repeatMode = RepeatMode.Restart
            )
        )
    }
    Box(modifier = Modifier
        .fillMaxSize()
        .graphicsLayer {
            val scaleOffset = 0.5f
            val widthToTranslate = width - 150.dp.toPx()
            scaleX = if(scaleImageAnimatable.value < scaleOffset) scaleX - (scaleImageAnimatable.value/2) else scaleX - ((1f-scaleImageAnimatable.value)/2)
            scaleY = if(scaleImageAnimatable.value < scaleOffset) scaleY - (scaleImageAnimatable.value/2) else scaleY - ((1f-scaleImageAnimatable.value)/2)
            translationX = if(translateImageAnimatable.value < 0.5f) widthToTranslate * translateImageAnimatable.value * 2 else widthToTranslate * (1f - translateImageAnimatable.value) * 2
        }
        .onGloballyPositioned {
            width = it.size.width
        },
        contentAlignment = Alignment.CenterStart
    ){
        LoadingImage()
    }
}

@Composable
fun LoadingImage(){
    val rotateImageAnimatable = remember {
        Animatable(0f)
    }
    LaunchedEffect(key1 = rotateImageAnimatable){
        rotateImageAnimatable.animateTo(
            targetValue = 1.5f,
            animationSpec = infiniteRepeatable(
                animation = keyframes {
                    durationMillis = 1500
                },
                repeatMode = RepeatMode.Restart
            )
        )
    }
    Image(
        painterResource(R.drawable.ic_spaceship),
        contentDescription = "spaceship loading",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(150.dp)
            .graphicsLayer {
                rotationY = if(rotateImageAnimatable.value > 1f && rotateImageAnimatable.value <= 1.5f) (rotateImageAnimatable.value - 1f) * 360 else rotationY
            }
    )
}