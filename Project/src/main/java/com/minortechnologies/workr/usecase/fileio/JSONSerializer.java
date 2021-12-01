package com.minortechnologies.workr.usecase.fileio;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class JSONSerializer implements IEntrySerializer, IEntryDeserializer{
    @Override
    public String serialize(HashMap<String, Object> serializedHashmap) {

        /*
         org.json's JSONObject does not support java null types, as in trying to add nulls into a JSONObject fails, and
         the entire key/value set is not added, despite JSON being able to use null.
         To work around this, I need to get a list of keys that are null, and then add the null values after creating the
         json object.
         */

        ArrayList<String> nullKeys = new ArrayList<>();
        for (String key:
             serializedHashmap.keySet()) {
            if (serializedHashmap.get(key) == null){
                nullKeys.add(key);
            }
        }

        JSONObject jsonData = new JSONObject(serializedHashmap);

        if (nullKeys.size() != 0){

            for (String key:
                    nullKeys) {
                jsonData.put(key, JSONObject.NULL);
            }
        }

        return jsonData.toString();
    }

    @Override
    public String serializerExtension(){
        return ".json";
    }

    @Override
    public HashMap<String, Object> deserialize(String data) {

        try{
            JSONObject jsonData = new JSONObject(data);
            Map<String, Object> deserializeMap = jsonData.toMap();
            return new HashMap<>(deserializeMap);
        }
        catch (JSONException e){
            return null;
        }
    }

}
