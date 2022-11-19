/*
 * Copyright 2019 the original authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.openapiprocessor.samples

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.web.util.UriComponentsBuilder
import spock.lang.Specification

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EchoControllerSpec extends Specification {

    @Autowired
    TestRestTemplate restTemplate

    void "echo returns 'pong'" () {
        def headers = new HttpHeaders()
        headers.setAccept ([MediaType.TEXT_PLAIN])

        def entity = new HttpEntity(headers)

        def uri = UriComponentsBuilder.fromUriString ('/echo')
            .queryParam ('source', 'pong')
            .build ()
            .toUri ()

        expect:
        restTemplate.exchange (uri, HttpMethod.GET, entity, String).body == 'pong'
    }

}
