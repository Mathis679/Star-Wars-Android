package com.example.domain.interactor

import com.example.domain.exception.Failure
import com.example.domain.exception.NoInternetException
import com.example.domain.exception.TimeoutException
import com.example.domain.functional.Either
import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeoutOrNull

abstract class UseCase<Type, in Params>(private val timeOut: Long? = null) {

    operator fun invoke(params: Params, scope: CoroutineScope, onResult: (Either<Failure, Type>) -> Unit = {}) {
        timeOut?.let {
            scope.launch {
                withTimeoutOrNull(it){
                    onResult.invoke(call(params))
                } ?: run { onResult.invoke(handleException(TimeoutException())) }
            }
        } ?: run {
            scope.launch {
                onResult.invoke(call(params))
            }
        }

    }

    abstract suspend fun call(params: Params): Either<Failure, Type>

    private fun handleException(e: Throwable): Either<Failure, Type> {
        return when (e) {

            is NoInternetException -> Either.Left(Failure.NetworkConnection)

            is JsonSyntaxException -> Either.Left(Failure.JsonSyntax)

            else -> Either.Left(Failure.Unknown)
        }
    }
}