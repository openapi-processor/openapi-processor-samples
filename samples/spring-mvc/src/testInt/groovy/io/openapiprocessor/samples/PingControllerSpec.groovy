package io.openapiprocessor.samples

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content

@WebMvcTest
class PingControllerSpec extends Specification {

    @Autowired
    MockMvc mvc

    void "ping returns pong" () {
        expect:
        mvc.perform(get("/ping")).andExpect(content().string('pong'))
    }
}
