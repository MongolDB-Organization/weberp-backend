package org.mogoldb.weberpBackend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class WeberpBackendApplication

fun main(args: Array<String>) {
	runApplication<WeberpBackendApplication>(*args)
}
