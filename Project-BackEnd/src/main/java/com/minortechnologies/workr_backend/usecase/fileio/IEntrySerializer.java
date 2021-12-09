package com.minortechnologies.workr_backend.usecase.fileio;

import java.util.HashMap;

public interface IEntrySerializer {

    String serialize(HashMap<String, Object> serializedHashmap);

    String serializerExtension();
}
