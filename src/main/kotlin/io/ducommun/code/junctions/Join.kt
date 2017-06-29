package io.ducommun.code.junctions

import io.ducommun.code.circuits.Pluggable
import io.ducommun.code.circuits.errors.ConnectionError
import io.ducommun.code.circuits.errors.DisconnectionError
import io.ducommun.code.results.Result

interface Join {

    val connectionOne: Pluggable

    val connectionTwo: Pluggable

    fun connect(other: Pluggable): Result<ConnectionError, Unit>

    fun disconnect(): Result<DisconnectionError, Unit>

    val powered: Boolean
}