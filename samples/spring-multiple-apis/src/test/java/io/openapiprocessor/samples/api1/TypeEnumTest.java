package io.openapiprocessor.samples.api1;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class TypeEnumTest {

    @Test
    public void testJsonCreator() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String json = "\"foo\"";
        Type type = mapper.readValue(json, Type.class);
        assertEquals(Type.OOF, type);
    }
}
