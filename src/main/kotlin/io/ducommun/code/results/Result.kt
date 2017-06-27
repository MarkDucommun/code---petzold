package io.ducommun.code.results

sealed class Result<F : WithMessage, S>

data class Success<F : WithMessage, S>(val content: S) : Result<F, S>()

data class Failure<F : WithMessage, S>(val reason: F) : Result<F, S>()

interface WithMessage {

    val message: String
}