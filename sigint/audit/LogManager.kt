package sigint.audit

import java.io.File
import java.text.SimpleDateFormat
import java.util.Date

object LogManager {
    private val logFile = File("data/logs/sigint_module.log")
    private val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

    fun info(msg: String) = write("INFO", msg)
    fun warn(msg: String) = write("WARN", msg)
    fun error(msg: String) = write("ERROR", msg)

    private fun write(level: String, msg: String) {
        val line = "${sdf.format(Date())} [$level] $msg\n"
        logFile.appendText(line)
        if (logFile.length() > 2_000_000) rotate()
    }

    private fun rotate() {
        val rotated = File("data/logs/sigint_module.${System.currentTimeMillis()}.log")
        logFile.copyTo(rotated, overwrite = true)
        logFile.writeText("")
    }
}