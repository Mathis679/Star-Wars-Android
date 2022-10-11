package com.example.starwarsappmvvm.di

import com.example.domain.executor.SchedulerProvider
import com.example.presentation.rx.ThreadExecutor
import com.example.starwarsappmvvm.rx.JobExecutor
import com.example.starwarsappmvvm.rx.SchedulerProviderImpl
import org.koin.dsl.module

val appModule = module {
    single<ThreadExecutor> { JobExecutor() }

    single<SchedulerProvider> { SchedulerProviderImpl(threadExecutor = get()) }
}