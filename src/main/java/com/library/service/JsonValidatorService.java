package com.library.service;

import lombok.extern.slf4j.Slf4j;
import org.everit.json.schema.Schema;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class JsonValidatorService {
    /***
     *
     * @param schemaString denotes the content of schema file as string.
     * @param content denotes the json content to be validated
     * @throws Exception
     */
    public void isValid(String schemaString, String content) throws Exception {
        JSONObject payload = new JSONObject(content);
        JSONObject jsonSchema = new JSONObject(schemaString);
        Schema schema = SchemaLoader.load(jsonSchema);
        schema.validate(payload);
    }
}
