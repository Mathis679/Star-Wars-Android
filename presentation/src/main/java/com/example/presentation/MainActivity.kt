package com.example.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.domain.model.Film
import com.example.utils.constants.FEED_FILMS_SCOPE_ID
import com.example.utils.constants.FEED_FILMS_SCOPE_NAME
import com.example.utils.extension.getOrCreateScope
import com.example.utils.extension.getViewModelScope
import org.koin.androidx.scope.bindScope

class MainActivity : ComponentActivity() {
    private val feedFilmViewModelScope =
            getOrCreateScope(FEED_FILMS_SCOPE_ID, FEED_FILMS_SCOPE_NAME)
    private val feedFilmViewModel =
            feedFilmViewModelScope.getViewModelScope<FeedFilmViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val films by feedFilmViewModel?.films?.collectAsState() as State<List<Film>>
            FeedFilms(films = films)
        }


        bindScope(feedFilmViewModelScope)

    }
}