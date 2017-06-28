package io.ducommun.code.circuits

import io.ducommun.code.results.Result

interface Join {

    val connectionOne: Pluggable

    val connectionTwo: Pluggable

    fun connect(other: Pluggable): Result<ConnectionError, Unit>

    fun disconnect(): Result<DisconnectionError, Unit>

    val powered: Boolean
}