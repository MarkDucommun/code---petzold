package io.ducommun.code.circuits

import io.ducommun.code.circuits.errors.ConnectionError
import io.ducommun.code.circuits.errors.DisconnectionError
import io.ducommun.code.circuits.errors.DisconnectionError.NotConnected
import io.ducommun.code.results.Failure
import io.ducommun.code.results.Result
import io.ducommun.code.results.map

class MutablePower : SingleVoltageSource {

    var innerPower: ImmutablePower? = null

    override fun connect(other: Pluggable): Result<ConnectionError, Unit> {

        return ImmutablePower.connect(other).map {
            innerPower = it
        }
    }

    override fun disconnect(): Result<DisconnectionError, Unit> {

        return innerPower
                ?.destroy()
                ?.map { innerPower = null }
                ?: Failure<DisconnectionError, Unit>(NotConnected)
    }
}