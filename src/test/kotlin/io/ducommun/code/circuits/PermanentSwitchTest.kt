package io.ducommun.code.circuits

import io.ducommun.code.results.flatMap
import io.ducommun.code.succeeded
import org.assertj.core.api.KotlinAssertions
import org.junit.Before
import org.junit.Test

class PermanentSwitchTest {

    val power = Power()

    val bulb = Bulb()

    val subject = PermanentSwitch(
            closedInitially = false,
            pluggedIn = bulb
    )


    val ground = Ground()

    @Before
    fun setUp() {
        power
                .connect(subject)
                .flatMap { bulb.connect(ground) }
                .succeeded()
    }

    @Test
    fun `the switch turns on and off repeatedly`() {

        bulbIsOff()

        subject.toggle().succeeded()

        bulbIsOn()

        subject.toggle().succeeded()

        bulbIsOff()

        subject.toggle().succeeded()

        bulbIsOn()

        subject.toggle().succeeded()

        bulbIsOff()
    }

    private fun bulbIsOff() { KotlinAssertions.assertThat(bulb.powered).isFalse() }
    private fun bulbIsOn() { KotlinAssertions.assertThat(bulb.powered).isTrue() }
}