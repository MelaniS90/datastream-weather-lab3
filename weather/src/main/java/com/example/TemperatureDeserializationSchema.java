package com.example;

import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.common.typeinfo.TypeInformation;

import com.fasterxml.jackson.databind.ObjectMapper;


import java.io.IOException;

public class TemperatureDeserializationSchema implements DeserializationSchema<Temperature> {

    private static final long serialVersionUID = 1L;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Temperature deserialize(byte[] bytes) throws IOException {
        return objectMapper.readValue(bytes, Temperature.class);
    }

    @Override
    public boolean isEndOfStream(Temperature temperature) {
        return false;
    }

    @Override
    public TypeInformation<Temperature> getProducedType() {
        return TypeInformation.of(Temperature.class);
    }
}
