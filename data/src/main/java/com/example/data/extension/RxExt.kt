package com.example.data.extension

import com.example.domain.exception.Failure
import com.example.domain.functional.Either
import io.reactivex.Observable
import retrofit2.Response

fun <T, R> Observable<Response<T>>.toEither(transformer: (T) -> R): Observable<Either<Failure, R>> {
    return map {
        if (it.isSuccessful) {
            val body = it.body()
            if (body != null) {
                Either.Right(transformer(body))
            } else {
                try {
                    Either.Right(transformer(Unit as T))
                } catch (e : Exception){
                    e.printStackTrace()
                    Either.Left(Failure.Unknown)
                }
            }
        } else {
            Either.Left(Failure.codeToFailure(it.code()))
        }
    }
}