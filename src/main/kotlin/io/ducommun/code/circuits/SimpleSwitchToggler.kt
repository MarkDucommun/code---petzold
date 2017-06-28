package io.ducommun.code.circuits

import io.ducommun.code.results.Result

class SimpleSwitchToggler(
        override var switch: MutableSwitch
) : SwitchToggler {

    override fun powerOn() {
        electroMagnet.powerOn()
        switch.toggle()
    }

    override fun powerOff() {
        electroMagnet.powerOff()
        switch.toggle()
    }

    var electroMagnet: Connectible = BasicComponent()

    override fun connect(other: Pluggable): Result<ConnectionError, Unit> {
        return electroMagnet.connect(other)
    }

    override fun disconnect(): Result<DisconnectionError, Unit> {
        return electroMagnet.disconnect()
    }

    override fun applyCurrent(appliedCurrent: Current?) {
        electroMagnet.applyCurrent(appliedCurrent)
    }

    override fun removeCurrent() {
        electroMagnet.disconnect()
    }

    override val powered: Boolean get() = electroMagnet.powered
}

class BetterSwitchToggler(
        override val switch: MutableSwitch,
        override val pluggedIn: Pluggable
) : ImmutableSwitchToggler {

    override fun powerOn() {
        electroMagnet.powerOn()
        switch.toggle()
    }

    override fun powerOff() {
        electroMagnet.powerOff()
        switch.toggle()
    }

    private val electroMagnet: Connectible = BasicComponent()

    // probably should make the constructor private
    init { electroMagnet.connect(pluggedIn) }

    override fun applyCurrent(appliedCurrent: Current?) {
        electroMagnet.applyCurrent(appliedCurrent)
    }

    override fun removeCurrent() {
        electroMagnet.removeCurrent()
    }

    override val powered: Boolean get() = electroMagnet.powered
}