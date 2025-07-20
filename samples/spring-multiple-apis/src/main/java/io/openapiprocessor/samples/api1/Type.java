package io.openapiprocessor.samples.api1;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Type
 */
public enum Type {
    OOF("foo"),
    RAB("bar");

    private final String value;

    Type(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return this.value;
    }

    @JsonCreator
    public static Type fromValue(String value) {
        for (Type val : Type.values()) {
            if (val.value.equals(value)) {
                return val;
            }
        }
        throw new IllegalArgumentException(
                String.format("No enum constant of %s has the value %s", Type.class.getCanonicalName(), value));
    }
}
