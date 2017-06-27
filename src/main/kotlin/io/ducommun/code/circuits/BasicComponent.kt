package io.ducommun.code.circuits

import com.sun.org.apache.xpath.internal.operations.Bool
import io.ducommun.code.results.Failure
import io.ducommun.code.results.Result
import io.ducommun.code.results.Success

open class BasicComponent : Connectible {

    override fun connect(other: Pluggable): Result<ConnectionError, Unit> {
        if (output != null) return Failure(ConnectionError.AlreadyConnected)
        output = other
        other.applyCurrent(current)
        return Success(Unit)
    }

    override fun disconnect(): Result<DisconnectionError, Unit> {
        if (output == null) return Failure(DisconnectionError.NotConnected)
        output?.powerOff()
        output?.removeCurrent()
        if (output is Receiver) (output as Receiver).disconnect()
        return Success(Unit)
    }

    override fun applyCurrent(appliedCurrent: Current?) {
        current = appliedCurrent
        output?.applyCurrent(appliedCurrent)
    }

    override fun removeCurrent() {
        current = null
        output?.removeCurrent()
    }

    override fun powerOn() {
        poweredState = true
        output?.powerOn()
    }

    override fun powerOff() {
        poweredState = false
        output?.powerOff()
    }

    protected var poweredState: Boolean = false

    protected var current: Current? = null

    protected var output: Pluggable? = null

    override val powered: Boolean get() = poweredState
}