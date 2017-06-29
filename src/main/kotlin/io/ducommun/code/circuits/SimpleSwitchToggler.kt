package io.ducommun.code.circuits

import io.ducommun.code.circuits.errors.ConnectionError
import io.ducommun.code.circuits.errors.DisconnectionError
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

    override fun applyCurrent(appliedCurrent: Current?) : Result<ConnectionError, Unit> {
        return electroMagnet.applyCurrent(appliedCurrent)
    }

    override fun removeCurrent() {
        electroMagnet.disconnect()
    }

    override val powered: Boolean get() = electroMagnet.powered
}