package com.example.starwarsappmvvm

import android.app.Application
import com.example.data.di.repositoryModule
import com.example.domain.di.useCaseModule
import com.example.presentation.di.presentationModule
import com.example.starwarsappmvvm.di.appModule
import com.example.starwarsappmvvm.di.networkModule
import com.example.starwarsappmvvm.di.serviceModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.module.Module

class BaseApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@BaseApplication)
            modules(allModules())
        }
    }

    private fun allModules(): List<Module> {
        return listOf(
                appModule,
                networkModule,
                repositoryModule,
                serviceModule,
                useCaseModule,
                presentationModule
        )
    }

}