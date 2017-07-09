package io.ducommun.code

import io.ducommun.code.results.Failure
import io.ducommun.code.results.Result
import io.ducommun.code.results.Success
import io.ducommun.code.results.WithMessage
import org.assertj.core.api.AbstractAssert
import org.assertj.core.api.AbstractObjectAssert
import org.assertj.core.api.Assert
import org.assertj.core.api.KotlinAssertions.assertThat
import org.assertj.core.api.ObjectAssert
import org.junit.Assert.fail

infix fun <F : WithMessage, S> Result<F, S>.succeedsAnd(fn: (S) -> Unit) {
    when (this) {
        is Success -> fn(content)
        is Failure -> fail("Should be success: ${reason.message}")
    }
}

infix fun <F : WithMessage, S> Result<F, S>.failsAnd(fn: (F) -> Unit) {
    when (this) {
        is Success -> fail("Should be failure")
        is Failure -> fn(reason)
    }
}

infix fun <F : WithMessage, S> Result<F, S>.failsWithReason(reason: F) {
    failsAnd { assertThat(it).isEqualTo(reason) }
}

fun <F : WithMessage, S> Result<F, S>.succeeded(): Unit {
    if (this is Failure) {
        fail("Should have been a success: $reason")
    }
}

fun <F : WithMessage, S> Assert<*, Result<F, S>>.succeeded(): Assert<*, *> = isInstanceOf(Success::class.java)!!

val <F, S> Result<F, S>.get: S get() = when (this) {
    is Success -> content
    is Failure -> throw RuntimeException("Should have been a success")
}