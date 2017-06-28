package io.ducommun.code.circuits

interface SwitchToggler : Connectible {

    var switch: MutableSwitch
}

interface ImmutableSwitchToggler : Pluggable {

    val switch: MutableSwitch

    val pluggedIn: Pluggable
}