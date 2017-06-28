package io.ducommun.code.circuits

import io.ducommun.code.results.Result

interface Split : Pluggable {

    fun connectOne(other: Pluggable): Result<ConnectionError, Unit>

    fun connectTwo(other: Pluggable): Result<ConnectionError, Unit>

    fun disconnectOne(): Result<DisconnectionError, Unit>

    fun disconnectTwo(): Result<DisconnectionError, Unit>
}