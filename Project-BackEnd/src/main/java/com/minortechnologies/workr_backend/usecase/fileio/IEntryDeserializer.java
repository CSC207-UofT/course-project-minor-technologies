package com.minortechnologies.workr_backend.usecase.fileio;

import java.util.HashMap;

public interface IEntryDeserializer {

    HashMap<String, Object> deserialize(String data);
}
