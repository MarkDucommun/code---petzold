package io.ducommun.code.circuits

import io.ducommun.code.circuits.errors.ConnectionError
import io.ducommun.code.circuits.errors.ConnectionError.AlreadyConnected
import io.ducommun.code.circuits.errors.DisconnectionError
import io.ducommun.code.circuits.errors.DisconnectionError.NotConnected
import io.ducommun.code.failsWithReason
import io.ducommun.code.results.flatMap
import io.ducommun.code.results.mapError
import io.ducommun.code.succeeded
import io.ducommun.code.succeedsAnd
import org.assertj.core.api.KotlinAssertions.assertThat
import org.junit.Test

class BasicComponentTest {

    val power = Power()

    val subject = BasicComponent()

    val ground = Ground()

    @Test
    fun `connect - another component can be plugged into it`() {

        assertThat(subject.connect(ground)).succeeded()
    }

    @Test
    fun `connect - cannot have two components plugged into it`() {

        subject.connect(ground).flatMap { subject.connect(Bulb()) } failsWithReason AlreadyConnected
    }

    @Test
    fun `disconnect - a component can be disconnected from it if it has been plugged in`() {

        val result = subject.connect(ground)
                .mapError<ConnectionError, DisconnectionError, Unit> { NotConnected }
                .flatMap<DisconnectionError, Unit, Unit> {
                    subject.disconnect()
                }

        assertThat(result).succeeded()
    }

    @Test
    fun `disconnect - a component cannot be disconnected from when it has nothing plugged in`() {

        subject.disconnect() failsWithReason NotConnected
    }
    
    @Test
    fun `apply current - can be applied when it does not currently have a current`() {

        assertThat(power.connect(subject)).succeeded()
    }

    @Test
    fun `apply current - does not work when it has a current`() {

        power.connect(subject).succeedsAnd {
            Power().connect(subject).failsWithReason()
        }
    }
}