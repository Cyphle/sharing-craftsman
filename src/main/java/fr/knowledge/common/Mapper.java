package fr.knowledge.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public class Mapper {
  private static ObjectMapper mapper = new ObjectMapper();

  public static <T> String fromObjectToJsonString(T object) throws JsonProcessingException {
    return mapper.writeValueAsString(object);
  }

  public static <T> T fromJsonStringToObject(String json, Class<T> type) throws IOException {
    return mapper.readValue(json, type);
  }

  public static <T, R> T fromJsonStringToObject(String json, Class<T> iteratorClass, Class<R> objectClass) throws IOException {
    if (iteratorClass.equals(List.class)) {
      return mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(List.class, objectClass));
    }
    return mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(Set.class, objectClass));
  }
}
