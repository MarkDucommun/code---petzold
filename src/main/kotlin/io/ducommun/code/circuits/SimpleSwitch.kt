package io.ducommun.code.circuits

import io.ducommun.code.results.Result

class SimpleSwitch(closedInitially: Boolean = false) : Switch {

    val incomingConnectible: Connectible = BasicComponent()

    val outgoingConnectible: Connectible = BasicComponent()

    init { if (closedInitially) incomingConnectible.connect(outgoingConnectible) }

    override fun connect(other: Pluggable): Result<ConnectionError, Unit> {
        return outgoingConnectible.connect(other)
    }

    override fun disconnect(): Result<DisconnectionError, Unit> {
        return outgoingConnectible.disconnect()
    }

    override fun applyCurrent(appliedCurrent: Current?) {
        incomingConnectible.applyCurrent(appliedCurrent)
    }

    override fun removeCurrent() {
        incomingConnectible.removeCurrent()
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