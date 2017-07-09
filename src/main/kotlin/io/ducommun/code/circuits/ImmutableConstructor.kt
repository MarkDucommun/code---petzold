package io.ducommun.code.circuits

import io.ducommun.code.circuits.errors.ConnectionError
import io.ducommun.code.results.Result

interface ImmutableConstructor<type> {

    fun connect(output: Pluggable): Result<ConnectionError, type>
}