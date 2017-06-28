package io.ducommun.code.circuits

import org.assertj.core.api.KotlinAssertions.assertThat
import org.junit.Test

class InverterTest {

    @Test
    fun `the inverter does the opposite of what the incoming power does`() {

        val power = Power()
        val switch = SimpleSwitch(closedInitially = false)
        val bulbOne = Bulb()
        val inverter = Inverter()
        val bulbTwo = Bulb()
        val ground = Ground()

        power.connect(switch)
        switch.connect(bulbOne)
        bulbOne.connect(inverter)
        inverter.connect(bulbTwo)
        bulbTwo.connect(ground)

        assertThat(bulbOne.powered).isFalse()
        assertThat(bulbTwo.powered).isTrue()

        switch.toggle()

        assertThat(bulbOne.powered).isTrue()
        assertThat(bulbTwo.powered).isFalse()
    }
}