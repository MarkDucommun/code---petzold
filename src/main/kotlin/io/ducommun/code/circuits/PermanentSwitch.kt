package io.ducommun.code.circuits

import io.ducommun.code.circuits.errors.ConnectionError
import io.ducommun.code.circuits.errors.DisconnectionError
import io.ducommun.code.results.Result

class PermanentSwitch(
        closedInitially: Boolean = false,
        pluggedIn: Pluggable
) : ImmutableReceiverSwitch {

    private val internalSwitch = SimpleSwitch(closedInitially)

    init {
        internalSwitch.connect(pluggedIn)
    }

    override fun applyCurrent(appliedCurrent: Current?): Result<ConnectionError, Unit> =
            internalSwitch.applyCurrent(appliedCurrent)

    override fun removeCurrent(): Result<DisconnectionError, Unit> = internalSwitch.removeCurrent()

    override fun powerOn() = internalSwitch.powerOn()

    override fun powerOff() = internalSwitch.powerOff()

    override fun toggle(): Result<ToggleError, Unit> = internalSwitch.toggle()

    override val powered: Boolean get() = internalSwitch.powered

    override val closed: Boolean get() = internalSwitch.closed
}