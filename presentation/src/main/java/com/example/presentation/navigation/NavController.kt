package com.example.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.presentation.FeedFilms
import org.koin.core.scope.Scope

@Composable
fun setUpNavController(controller: NavHostController, viewModelScope: Scope){
    NavHost(controller, Screens.FeedFilms.route){
        composable(Screens.FeedFilms.route){
            FeedFilms(viewModelScope = viewModelScope)
        }
    }
}