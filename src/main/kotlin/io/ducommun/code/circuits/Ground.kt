package io.ducommun.code.circuits

import io.ducommun.code.circuits.errors.ConnectionError
import io.ducommun.code.results.Result
import io.ducommun.code.results.Success

class Ground : VoltageSink {

    override fun applyCurrent(appliedCurrent: Current?): Result<ConnectionError, Unit> {
        current = appliedCurrent
        appliedCurrent?.complete = true
        current?.source?.power()
        return Success(Unit)
    }

    override fun removeCurrent() {
        current?.complete = false
        current = null
    }

    private var current: Current? = null

    override fun powerOn() {}

    override fun powerOff() {}

    override val powered: Boolean get() = current?.complete ?: false
}