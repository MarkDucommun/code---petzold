package io.ducommun.code.circuits

import io.ducommun.code.circuits.errors.ConnectionError.PluggableAlreadyConnected
import io.ducommun.code.circuits.errors.ConnectionError.ReceiverAlreadyConnected
import io.ducommun.code.circuits.errors.DisconnectionError.NotConnected
import io.ducommun.code.failsWithReason
import io.ducommun.code.results.flatMap
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

        subject.connect(ground).flatMap { subject.connect(Bulb()) } failsWithReason ReceiverAlreadyConnected
    }

    @Test
    fun `disconnect - a component can be disconnected from it if it has been plugged in`() {

        subject.connect(ground).succeedsAnd { assertThat(subject.disconnect()).succeeded() }
    }

    @Test
    fun `disconnect - a component cannot be disconnected from when it has nothing plugged in`() {

        subject.disconnect() failsWithReason NotConnected
    }

    val current = SimpleCurrent(Power())

    @Test
    fun `apply current - can be applied when it does not currently have a current`() {

        assertThat(subject.applyCurrent(current)).succeeded()
    }

    @Test
    fun `apply current - does not work when it has a current`() {

        power.connect(subject).succeeded()

        Power().connect(subject).failsWithReason(PluggableAlreadyConnected)
    }

    @Test
    fun `apply current - does not work when something down the circuit has a current`() {

        val other = BasicComponent()

        subject.connect(other)

        other.applyCurrent(SimpleCurrent(Power()))

        power.connect(subject).failsWithReason(PluggableAlreadyConnected)
    }

    @Test
    fun `remove current - works when there is a current to remove`() {

        subject.applyCurrent(SimpleCurrent(Power()))

        assertThat(subject.removeCurrent()).succeeded()
    }
}