package com.example.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Text
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
            Text("Hello world!")
        }


        bindScope(feedFilmViewModelScope)

        feedFilmViewModel?.loadAllFilms()?.observe(this){
            it.fold(
                    {
                        Log.d("FAILED", "failure : $it")
                    },
                    {
                        it.forEach {
                            Log.d("Film", it.title)
                        }
                    }
            )
        }
    }
}