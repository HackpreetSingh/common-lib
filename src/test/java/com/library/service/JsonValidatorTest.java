package com.library.service;

import org.everit.json.schema.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class JsonValidatorTest {

    private JsonValidatorService jsonValidatorServiceUnderTest;

    private static String schema;

    @BeforeEach
    public void setUp() throws IOException {
        jsonValidatorServiceUnderTest = new JsonValidatorService();
        schema = new String(Files.readAllBytes(Paths.get("src/test/resources/JsonValidationResources/schema_validation.json")));
    }

    @Test
    void testIsValid() throws Exception {
        // Setup
        String payload = new String(Files.readAllBytes(Paths.get("src/test/resources/JsonValidationResources/ValidInput.json")));

        // Run the test
        jsonValidatorServiceUnderTest.isValid(schema, payload);
    }

    @Test
    void testIsValid_ThrowsException() throws IOException {
        // Setup
        String payload = new String(Files.readAllBytes(Paths.get("src/test/resources/JsonValidationResources/InvalidInput.json")));

        // Run the test
        assertThrows(ValidationException.class, () -> jsonValidatorServiceUnderTest.isValid(schema, payload));
    }
}
