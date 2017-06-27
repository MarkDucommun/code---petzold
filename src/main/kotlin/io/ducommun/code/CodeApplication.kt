package io.ducommun.code

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class CodeApplication

fun main(args: Array<String>) {
    SpringApplication.run(CodeApplication::class.java, *args)
}
