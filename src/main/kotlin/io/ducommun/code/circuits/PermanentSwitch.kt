package io.ducommun.code.circuits

import io.ducommun.code.circuits.errors.ConnectionError
import io.ducommun.code.circuits.errors.DisconnectionError
import io.ducommun.code.results.Result
import io.ducommun.code.results.Success

class PermanentSwitch(
        closedInitially: Boolean = false,
        override val pluggedIn: Pluggable
) : ImmutableReceiverSwitch {

//    private val internalSwitch = SimpleSwitch()

//    init { internalSwitch.connect() }

    val incomingConnectible: Connectible = BasicComponent()

    val outgoingConnectible: Connectible = BasicComponent()

    init { if (closedInitially) incomingConnectible.connect(outgoingConnectible) }

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

    override fun toggle(): Result<ToggleError, Unit> {

        if (closed) {
            incomingConnectible.disconnect()
        } else {
            incomingConnectible.connect(outgoingConnectible)
        }

        closedState = !closedState

        return Success(Unit)
    }

    override val closed: Boolean get() = closedState
}