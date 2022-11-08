package com.example.data.extension

import com.example.domain.exception.Failure
import com.example.domain.functional.Either
import retrofit2.Response


fun <T, R> Response<T>.toEither(transformer: (T) -> R): Either<Failure, R> {
    return if (isSuccessful) {
            val body = body()
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
            Either.Left(Failure.codeToFailure(code()))
        }
}
