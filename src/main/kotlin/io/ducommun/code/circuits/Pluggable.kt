package io.ducommun.code.circuits

import io.ducommun.code.circuits.errors.ConnectionError
import io.ducommun.code.circuits.errors.DisconnectionError
import io.ducommun.code.results.Result

interface Pluggable {

    fun applyCurrent(appliedCurrent: Current?): Result<ConnectionError, Unit>

    fun removeCurrent(): Result<DisconnectionError, Unit>

    fun powerOn()

    fun powerOff()

    val powered: Boolean
}