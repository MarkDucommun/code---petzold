package io.ducommun.code.circuits

import io.ducommun.code.circuits.errors.ConnectionError
import io.ducommun.code.circuits.errors.DisconnectionError
import io.ducommun.code.results.Failure
import io.ducommun.code.results.Result
import io.ducommun.code.results.Success
import io.ducommun.code.results.map

class ImmutablePower private constructor(
        private val output: Pluggable
) : VoltageSource, ImmutableReceiver {

    private val current: Current = SimpleCurrent {
        output.powerOn()
        Success(Unit)
    }

    override fun destroy(): Result<DisconnectionError, Unit> {
        return if (!destroyed) {
            output.removeCurrent().map { destroyed = true }
        } else {
            Failure(DisconnectionError.NotConnected) // TODO different error
        }
    }

    private var destroyed: Boolean = false

    companion object : ImmutableConstructor<ImmutablePower> {

        override fun connect(output: Pluggable): Result<ConnectionError, ImmutablePower> {

            return ImmutablePower(output).let { power ->
                output.applyCurrent(power.current).map { power }
            }
        }
    }
}