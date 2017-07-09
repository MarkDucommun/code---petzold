package io.ducommun.code.gates

import io.ducommun.code.circuits.Bulb
import io.ducommun.code.circuits.Ground
import io.ducommun.code.circuits.Power
import io.ducommun.code.circuits.SimpleSwitch
import io.ducommun.code.get
import org.assertj.core.api.KotlinAssertions.assertThat
import org.junit.Test

class RelayTest {

    @Test
    fun `the inverter does the opposite of what the incoming power does`() {

        val power = Power()
        val switch = SimpleSwitch(closedInitially = false)
        val bulbOne = Bulb()
        val inverter = Relay.createInverter().get
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

        switch.toggle()

        assertThat(bulbOne.powered).isFalse()
        assertThat(bulbTwo.powered).isTrue()

        switch.toggle()

        assertThat(bulbOne.powered).isTrue()
        assertThat(bulbTwo.powered).isFalse()

        switch.toggle()

        assertThat(bulbOne.powered).isFalse()
        assertThat(bulbTwo.powered).isTrue()

        switch.toggle()

        assertThat(bulbOne.powered).isTrue()
        assertThat(bulbTwo.powered).isFalse()
    }

    @Test
    fun `the relay has the state of the incoming current`() {

//        val switch = SimpleSwitch(closedInitially = false)
        val bulbOne = Bulb()
        val relay = Relay.create().get
        val bulbTwo = Bulb()
        val ground = Ground()
        val power = Power()

//        power.connect(switch)
//        switch.connect(bulbOne)
        bulbOne.connect(relay)
        relay.connect(bulbTwo)
        bulbTwo.connect(ground)

        assertThat(bulbOne.powered).isFalse()
        assertThat(bulbTwo.powered).isFalse()

//        switch.toggle()
        power.connect(bulbOne)

        assertThat(bulbOne.powered).isTrue()
        assertThat(bulbTwo.powered).isTrue()

//        switch.toggle()
        power.disconnect()

        assertThat(bulbOne.powered).isFalse()
        assertThat(bulbTwo.powered).isFalse()

        power.connect(bulbOne)
//        switch.toggle()

        assertThat(bulbOne.powered).isTrue()
        assertThat(bulbTwo.powered).isTrue()

//        switch.toggle()
        power.disconnect()

        assertThat(bulbOne.powered).isFalse()
        assertThat(bulbTwo.powered).isFalse()

//        switch.toggle()
        power.connect(bulbOne)

        assertThat(bulbOne.powered).isTrue()
        assertThat(bulbTwo.powered).isTrue()
    }

}