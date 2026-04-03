package io.openapiprocessor.samples.echo.server

class Echo(val echo: String) {
    fun reverse(): Echo {
        return Echo(echo.reversed())
    }
}
