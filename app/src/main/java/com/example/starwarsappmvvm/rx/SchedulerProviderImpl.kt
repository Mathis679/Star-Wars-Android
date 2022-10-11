package com.example.starwarsappmvvm.rx

import com.example.domain.executor.SchedulerProvider
import com.example.presentation.rx.ThreadExecutor
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SchedulerProviderImpl(private val threadExecutor: ThreadExecutor) :
    SchedulerProvider {
    override fun io(): Scheduler {
        return Schedulers.from(threadExecutor)
    }

    override fun ui(): Scheduler {
        return AndroidSchedulers.mainThread()
    }
}