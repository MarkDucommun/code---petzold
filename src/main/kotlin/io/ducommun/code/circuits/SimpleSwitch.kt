package io.ducommun.code.circuits

import io.ducommun.code.circuits.errors.ConnectionError
import io.ducommun.code.circuits.errors.DisconnectionError
import io.ducommun.code.results.Result
import io.ducommun.code.results.Success

class SimpleSwitch(closedInitially: Boolean = false) : MutableSwitch {

    val incomingConnectible: Connectible = BasicComponent()

    val outgoingConnectible: Connectible = BasicComponent()

    init { if (closedInitially) incomingConnectible.connect(outgoingConnectible) }

    override fun connect(other: Pluggable): Result<ConnectionError, Unit> {
        return outgoingConnectible.connect(other)
    }

    override fun disconnect(): Result<DisconnectionError, Unit> {
        return outgoingConnectible.disconnect()
    }

    override fun applyCurrent(appliedCurrent: Current?) : Result<ConnectionError, Unit> {
        return incomingConnectible.applyCurrent(appliedCurrent)
    }

    override fun removeCurrent(): Result<DisconnectionError, Unit> {
        incomingConnectible.removeCurrent()
        return Success(Unit)
    }

    override fun powerOn() {
        incomingConnectible.powerOn()
    }

    override fun powerOff() {
        incomingConnectible.powerOff()
    }

    override val powered: Boolean get() = outgoingConnectible.powered

    var closedState: Boolean = closedInitially

    override fun toggle(): Boolean {

        if (closed) {
            incomingConnectible.disconnect()
        } else {
            incomingConnectible.connect(outgoingConnectible)
        }

        closedState = !closedState

        return true
    }

    override val closed: Boolean get() = closedState
}