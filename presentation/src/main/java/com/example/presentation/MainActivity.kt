package com.example.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.presentation.navigation.setUpNavController
import com.example.presentation.ui.theme.StarWarsAppMVVMTheme
import com.example.utils.constants.SCOPE_ID
import com.example.utils.constants.SCOPE_NAME
import com.example.utils.extension.getOrCreateScope
import org.koin.androidx.scope.bindScope

class MainActivity : ComponentActivity() {
    private val viewModelScope =
            getOrCreateScope(SCOPE_ID, SCOPE_NAME)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StarWarsAppMVVMTheme {
                Surface(
                    color = MaterialTheme.colors.primary,
                    modifier = Modifier.fillMaxSize()
                ) {
                    setUpNavController(rememberNavController(), viewModelScope)
                }
            }
        }


        bindScope(viewModelScope)

    }
}