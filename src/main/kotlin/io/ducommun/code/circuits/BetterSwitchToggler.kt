package io.ducommun.code.circuits

import io.ducommun.code.circuits.errors.ConnectionError
import io.ducommun.code.circuits.errors.DisconnectionError
import io.ducommun.code.results.Result

class BetterSwitchToggler(
        override val switch: Switch,
        pluggedIn: Pluggable
) : ImmutableSwitchToggler {

    private val electromagnet: Connectible = BasicComponent()

    // probably should make the constructor private
    init {
        electromagnet.connect(pluggedIn)
    }

    override fun applyCurrent(appliedCurrent: Current?): Result<ConnectionError, Unit> {
        return electromagnet.applyCurrent(appliedCurrent)
    }

    override fun removeCurrent(): Result<DisconnectionError, Unit> {
        return electromagnet.removeCurrent()
    }

    override fun powerOn() {
        switch.toggle()
        electromagnet.powerOn()
    }

    override fun powerOff() {
        switch.toggle()
        electromagnet.powerOff()
    }

    override val powered: Boolean get() = electromagnet.powered
}