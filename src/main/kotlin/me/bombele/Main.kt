package me.bombele

import me.bombele.cyber.NetworkResilience

fun main(args: Array<String>) {
    val mode = args.getOrNull(0) ?: "help"
    val target = args.getOrNull(1) ?: "127.0.0.1"

    val resilience = NetworkResilience(target)

    when (mode) {
        "--audit" -> {
            resilience.auditTargetPorts(listOf(21, 22, 80, 443, 8080, 3306))
        }
        "--stress" -> {
            resilience.stressTest(80, 500)
        }
        else -> {
            println("Usage: java -jar core.jar [--audit | --stress] [target_ip]")
        }
    }
}
