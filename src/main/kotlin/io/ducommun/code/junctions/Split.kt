package io.ducommun.code.junctions

import io.ducommun.code.circuits.Pluggable
import io.ducommun.code.circuits.errors.ConnectionError
import io.ducommun.code.circuits.errors.DisconnectionError
import io.ducommun.code.results.Result

interface Split : Pluggable {

    fun connectOne(other: Pluggable): Result<ConnectionError, Unit>

    fun connectTwo(other: Pluggable): Result<ConnectionError, Unit>

    fun disconnectOne(): Result<DisconnectionError, Unit>

    fun disconnectTwo(): Result<DisconnectionError, Unit>
}