package com.minortechnologies.workr.usecase.fileio;

import java.util.HashMap;

public interface IEntryDeserializer {

    HashMap<String, Object> deserialize(String data);
}
