package io.ducommun.code.circuits

import io.ducommun.code.*
import io.ducommun.code.circuits.errors.ConnectionError
import io.ducommun.code.circuits.errors.DisconnectionError
import org.assertj.core.api.KotlinAssertions.assertThat
import org.junit.Test

class CircuitTest {

    @Test
    fun `circuit with just a voltage source`() {

        val battery = Battery()

        assertThat(battery.powered).isFalse()

        battery.connect(battery)

        assertThat(battery.powered).isTrue()

        battery.disconnect()

        assertThat(battery.powered).isFalse()
    }

    @Test
    fun `battery cannot connect to an already connected Connectible`() {

        val battery = Battery()

        battery.connect(battery)

        battery.connect(Bulb()) failsWithReason ConnectionError.ReceiverAlreadyConnected
    }

    @Test
    fun `basic component cannot connect to an already connected Connectible`() {

        val subject = BasicComponent()

        subject.connect(Bulb())

        subject.connect(Bulb()) failsWithReason ConnectionError.ReceiverAlreadyConnected
    }

    @Test
    fun `switch cannot connect to an already connected Connectible`() {

        val subject = SimpleSwitch()

        subject.connect(subject)

        subject.connect(Bulb()) failsWithReason ConnectionError.ReceiverAlreadyConnected
    }

    @Test
    fun `battery cannot connect when it has nothing connected`() {

        val battery = Battery()

        battery.connect(battery)

        battery.connect(Bulb()) failsWithReason ConnectionError.ReceiverAlreadyConnected
    }

    @Test
    fun `basic component cannot disconnect when it has nothing connected`() {

        BasicComponent().disconnect() failsWithReason DisconnectionError.NotConnected
    }

    @Test
    fun `battery cannot disconnect when it has nothing connected`() {

        Battery().disconnect() failsWithReason DisconnectionError.NotConnected
    }

    @Test
    fun `switch cannot disconnect when it has nothing connected`() {

        SimpleSwitch().disconnect() failsWithReason DisconnectionError.NotConnected
    }

    @Test
    fun `all components of a complete circuit with a voltage source are powered on`() {

        val battery = Battery()
        val bulb = Bulb()

        battery.connect(bulb)

        assertThat(battery.powered).isFalse()
        assertThat(bulb.powered).isFalse()

        bulb.connect(battery)

        assertThat(battery.powered).isTrue()
        assertThat(bulb.powered).isTrue()

        battery.disconnect()

        assertThat(battery.powered).isFalse()
        assertThat(bulb.powered).isFalse()
    }

    @Test
    fun `an initially closed switch can power a complete circuit on and off`() {

        val battery = Battery()

        val bulb = Bulb()

        val switch = SimpleSwitch(closedInitially = false)

        battery.connect(switch)

        switch.connect(bulb)

        bulb.connect(battery)

        assertThat(battery.powered).isFalse()
        assertThat(switch.powered).isFalse()
        assertThat(bulb.powered).isFalse()

        switch.toggle()

        assertThat(battery.powered).isTrue()
        assertThat(switch.powered).isTrue()
        assertThat(bulb.powered).isTrue()

        switch.toggle()

        assertThat(battery.powered).isFalse()
        assertThat(switch.powered).isFalse()
        assertThat(bulb.powered).isFalse()
    }

    @Test
    fun `toggle flips a switch when it is powered`() {

        val batteryOne = Battery()
        val batteryTwo = Battery()
        val switchOne = SimpleSwitch(closedInitially = false)
        val switchTwo = SimpleSwitch(closedInitially = false)
        val toggler = SimpleSwitchToggler(switchTwo)
        val bulb = Bulb()

        batteryOne.connect(switchOne)
        switchOne.connect(toggler)
        toggler.connect(batteryOne)

        batteryTwo.connect(switchTwo)
        switchTwo.connect(bulb)
        bulb.connect(batteryTwo)

        assertThat(bulb.powered).isFalse()

        switchOne.toggle()

        assertThat(bulb.powered).isFalse()

        switchTwo.toggle()

        assertThat(bulb.powered).isTrue()
    }

    @Test
    fun `power and ground can create a circuit`() {

        val power = Power()
        val bulb = Bulb()
        val ground = Ground()

        power.connect(bulb)
        bulb.connect(ground)

        assertThat(bulb.powered).isTrue()

        bulb.disconnect()

        assertThat(bulb.powered).isFalse()
    }

    @Test
    fun `maybe this is an AND gate?`() {

        val powerOne = Power()
        val powerTwo = Power()
        val powerThree = Power()

        val switchOne = SimpleSwitch(closedInitially = false)
        val switchTwo = SimpleSwitch(closedInitially = false)
        val switchThree = SimpleSwitch(closedInitially = false)
        val switchFour = SimpleSwitch(closedInitially = false)

        val switchTogglerOne = SimpleSwitchToggler(switchOne)
        val switchTogglerTwo = SimpleSwitchToggler(switchTwo)

        val bulb = Bulb()

        val groundOne = Ground()
        val groundTwo = Ground()
        val groundThree = Ground()

        powerOne.connect(switchThree)
        switchThree.connect(switchTogglerOne)
        switchTogglerOne.connect(groundOne)

        powerTwo.connect(switchFour)
        switchFour.connect(switchTogglerTwo)
        switchTogglerTwo.connect(groundTwo)

        powerThree.connect(switchOne)
        switchOne.connect(switchTwo)
        switchTwo.connect(bulb)
        bulb.connect(groundThree)

        assertThat(bulb.powered).isFalse()

        switchThree.toggle()

        assertThat(bulb.powered).isFalse()

        switchFour.toggle()

        assertThat(bulb.powered).isTrue()

        switchThree.toggle()

        assertThat(bulb.powered).isFalse()
    }
}