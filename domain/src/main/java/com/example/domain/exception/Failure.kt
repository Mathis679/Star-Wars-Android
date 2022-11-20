package com.example.domain.exception

sealed class Failure {
    object NetworkConnection : Failure()

    object JsonSyntax : Failure()

    object ServerError : Failure()

    object UnauthorizedError : Failure()

    object Forbidden : Failure()

    object NotFoundError : Failure()

    object BadRequest : Failure()

    object BadGateway: Failure()

    object Unknown : Failure()

    companion object{
        fun codeToFailure(code: Int): Failure{
            return when(code){
                400 -> BadRequest
                401 -> UnauthorizedError
                403 -> Forbidden
                404 -> NotFoundError
                500 -> ServerError
                502 -> BadGateway
                666 -> NetworkConnection
                else -> Unknown
            }
        }
    }

}