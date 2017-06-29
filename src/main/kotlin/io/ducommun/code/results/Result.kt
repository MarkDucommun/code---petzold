package io.ducommun.code.results

sealed class Result<F : WithMessage, S>

data class Success<F : WithMessage, S>(val content: S) : Result<F, S>()

data class Failure<F : WithMessage, S>(val reason: F) : Result<F, S>()

interface WithMessage {

    val message: String
}

fun <F : WithMessage, S, newS> Result<F, S>.map(operation: (S) -> newS): Result<F, newS> =
        when (this) {
            is Success -> Success(operation(this.content))
            is Failure -> Failure(this.reason)
        }

fun <F : WithMessage, newF : WithMessage, S> Result<F, S>.mapError(operation: (F) -> newF): Result<newF, S> =
        when (this) {
            is Success -> Success(this.content)
            is Failure -> Failure(operation(this.reason))
        }

fun <F : WithMessage, S, newS> Result<F, S>.flatMap(operation: (S) -> Result<F, newS>): Result<F, newS> =
        when (this) {
            is Success -> operation(this.content)
            is Failure -> Failure(this.reason)
        }

fun <F : WithMessage, newF : WithMessage, S> Result<F, S>.flatMapError(operation: (F) -> Result<newF, S>): Result<newF, S> =
        when (this) {
            is Success -> Success(this.content)
            is Failure -> operation(this.reason)
        }