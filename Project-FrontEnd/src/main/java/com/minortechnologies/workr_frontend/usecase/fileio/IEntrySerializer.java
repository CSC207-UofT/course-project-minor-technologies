package com.minortechnologies.workr_frontend.usecase.fileio;

import java.util.HashMap;

public interface IEntrySerializer {

    String serialize(HashMap<String, Object> serializedHashmap);

    String serializerExtension();
}
