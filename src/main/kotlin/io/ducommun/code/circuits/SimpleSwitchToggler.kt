package io.ducommun.code.circuits

import io.ducommun.code.circuits.errors.ConnectionError
import io.ducommun.code.circuits.errors.DisconnectionError
import io.ducommun.code.results.Result
import io.ducommun.code.results.Success

class SimpleSwitchToggler(
        override var switch: MutableSwitch
) : SwitchToggler {

    override fun powerOn() {
        electromagnet.powerOn()
        switch.toggle()
    }

    override fun powerOff() {
        electromagnet.powerOff()
        switch.toggle()
    }

    var electromagnet: Connectible = BasicComponent()

    override fun connect(other: Pluggable): Result<ConnectionError, Unit> {
        return electromagnet.connect(other)
    }

    override fun disconnect(): Result<DisconnectionError, Unit> {
        return electromagnet.disconnect()
    }

    override fun applyCurrent(appliedCurrent: Current?) : Result<ConnectionError, Unit> {
        return electromagnet.applyCurrent(appliedCurrent)
    }

    override fun removeCurrent(): Result<DisconnectionError, Unit> {
        electromagnet.disconnect()
        return Success(Unit)
    }

    override val powered: Boolean get() = electromagnet.powered
}