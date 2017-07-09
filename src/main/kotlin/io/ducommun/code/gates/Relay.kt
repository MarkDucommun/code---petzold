package io.ducommun.code.gates

import io.ducommun.code.circuits.*
import io.ducommun.code.circuits.errors.ConnectionError
import io.ducommun.code.circuits.errors.DisconnectionError
import io.ducommun.code.results.Result
import io.ducommun.code.results.map

class Relay private constructor(
        private val switch: MutableSwitch,
        private val toggler: ImmutableSwitchToggler
) : Connectible {

    override fun connect(other: Pluggable): Result<ConnectionError, Unit> = switch.connect(other)

    override fun disconnect(): Result<DisconnectionError, Unit> = switch.disconnect()

    override fun applyCurrent(appliedCurrent: Current?): Result<ConnectionError, Unit> =
            toggler.applyCurrent(appliedCurrent)

    override fun removeCurrent(): Result<DisconnectionError, Unit> = toggler.removeCurrent()

    override fun powerOn() = toggler.powerOn()

    override fun powerOff() = toggler.powerOff()

    override val powered: Boolean get() = toggler.powered

    companion object {

        fun create(closedInitially: Boolean = false): Result<ConnectionError, Relay> {

            val switch = SimpleSwitch(closedInitially)

            val toggler = BetterSwitchToggler(switch = switch, pluggedIn = Ground())

            return ImmutablePower.connect(switch).map {
                Relay(switch = switch, toggler = toggler)
            }
        }

        fun createInverter(): Result<ConnectionError, Relay> = create(closedInitially = true)
    }
}