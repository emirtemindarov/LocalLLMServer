package proj.emirtemindarov

import io.ktor.server.engine.*
import io.ktor.server.application.*

fun main(args: Array<String>) {

    System.setOut(
        java.io.PrintStream(
            System.out,
            true,
            Charsets.UTF_8
        )
    )

    System.setErr(
        java.io.PrintStream(
            System.err,
            true,
            Charsets.UTF_8
        )
    )

    io.ktor.server.cio.EngineMain.main(args)
}