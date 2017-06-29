package io.ducommun.code.gates

import io.ducommun.code.circuits.*
import io.ducommun.code.circuits.errors.ConnectionError
import io.ducommun.code.circuits.errors.DisconnectionError
import io.ducommun.code.junctions.Join
import io.ducommun.code.junctions.SimpleJoin
import io.ducommun.code.results.Result

class OrGate : ParallelGate(switchesInitiallyClosed = false)