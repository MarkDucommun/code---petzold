package io.ducommun.code.circuits

import io.ducommun.code.circuits.errors.ConnectionError
import io.ducommun.code.circuits.errors.DisconnectionError
import io.ducommun.code.results.Result
import io.ducommun.code.results.Success

class BetterSwitchToggler(
        override val switch: Switch,
        pluggedIn: Pluggable
) : ImmutableSwitchToggler {

    private val electroMagnet: Connectible = BasicComponent()

    // probably should make the constructor private
    init {
        electroMagnet.connect(pluggedIn)
    }

    override fun applyCurrent(appliedCurrent: Current?): Result<ConnectionError, Unit> {
        return electroMagnet.applyCurrent(appliedCurrent)
    }

    override fun removeCurrent(): Result<DisconnectionError, Unit> {
        return electroMagnet.removeCurrent()
    }

    override fun powerOn() {
        switch.toggle()
        electroMagnet.powerOn()
    }

    override fun powerOff() {
        electroMagnet.powerOff()
        switch.toggle()
    }

    override val powered: Boolean get() = electroMagnet.powered
}