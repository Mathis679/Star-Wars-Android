package com.example.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.utils.constants.FEED_FILMS_SCOPE_ID
import com.example.utils.constants.FEED_FILMS_SCOPE_NAME
import com.example.utils.extension.getOrCreateScope
import com.example.utils.extension.getViewModelScope
import org.koin.androidx.scope.bindScope

class MainActivity : AppCompatActivity() {
    private val feedFilmViewModelScope =
            getOrCreateScope(FEED_FILMS_SCOPE_ID, FEED_FILMS_SCOPE_NAME)
    private val feedFilmViewModel =
            feedFilmViewModelScope.getViewModelScope<FeedFilmViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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