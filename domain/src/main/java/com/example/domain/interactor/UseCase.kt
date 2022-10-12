package com.example.domain.interactor

import com.example.domain.exception.Failure
import com.example.domain.exception.NoInternetException
import com.example.domain.executor.SchedulerProvider
import com.example.domain.functional.Either
import com.google.gson.JsonSyntaxException
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import java.util.concurrent.TimeUnit

abstract class UseCase<Type, in Params>(private val schedulerProvider: SchedulerProvider, private val timeOut: Long? = null) {

    private var disposable: Disposable? = null


    operator fun invoke(params: Params, onResult: (Either<Failure, Type>) -> Unit = {}) {
        timeOut?.let {
            disposable = buildObservable(params)
                    .subscribeOn(schedulerProvider.io())
                    .observeOn(schedulerProvider.ui())
                    .timeout(it, TimeUnit.MILLISECONDS)
                    .subscribeWith(object : DisposableObserver<Either<Failure, Type>>() {
                        override fun onComplete() {

                        }

                        override fun onNext(t: Either<Failure, Type>) {
                            onResult.invoke(t)
                        }

                        override fun onError(e: Throwable) {
                            e.printStackTrace()
                            onResult.invoke(handleException(e))
                        }
                    })
        } ?: run {
            disposable = buildObservable(params)
                    .subscribeOn(schedulerProvider.io())
                    .observeOn(schedulerProvider.ui())
                    .subscribeWith(object : DisposableObserver<Either<Failure, Type>>() {
                        override fun onComplete() {

                        }

                        override fun onNext(t: Either<Failure, Type>) {
                            onResult.invoke(t)
                        }

                        override fun onError(e: Throwable) {
                            e.printStackTrace()
                            onResult.invoke(handleException(e))
                        }
                    })
        }

    }

    abstract fun buildObservable(params: Params): Observable<Either<Failure, Type>>

    private fun handleException(e: Throwable): Either<Failure, Type> {
        return when (e) {

            is NoInternetException -> Either.Left(Failure.NetworkConnection)

            is JsonSyntaxException -> Either.Left(Failure.JsonSyntax)

            else -> Either.Left(Failure.Unknown)
        }
    }

    fun dispose() {
        disposable?.let {
            if (it.isDisposed) it.dispose()
        }
    }
}