package io.ducommun.code.circuits

import io.ducommun.code.circuits.errors.ConnectionError
import io.ducommun.code.results.Result

interface Pluggable {

    fun applyCurrent(appliedCurrent: Current?): Result<ConnectionError, Unit>

    fun removeCurrent()

    fun powerOn()

    fun powerOff()

    val powered: Boolean
}