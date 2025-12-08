package io.openapiprocessor.samples

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content

@WebMvcTest
class EchoControllerSpec extends Specification {

    @Autowired
    MockMvc mvc

    void "echo returns 'ping'" () {
        expect:
        mvc.perform(get("/echo")
                .accept(MediaType.TEXT_PLAIN)
                .queryParam('source', 'ping'))
                .andExpect(content().string('ping'))
    }
}
