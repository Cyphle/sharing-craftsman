package fr.knowledge.common;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public class Mapper {
  private static ObjectMapper mapper;

  private static ObjectMapper getMapper() {
    if (mapper == null) {
      mapper = new ObjectMapper();
      VisibilityChecker visibilityChecker = mapper.getSerializationConfig().getDefaultVisibilityChecker()
              .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
              .withGetterVisibility(JsonAutoDetect.Visibility.NONE)
              .withSetterVisibility(JsonAutoDetect.Visibility.NONE)
              .withIsGetterVisibility(JsonAutoDetect.Visibility.NONE)
              .withCreatorVisibility(JsonAutoDetect.Visibility.NONE);
      mapper.setVisibility(visibilityChecker);
    }
    return mapper;
  }

  public static <T> String fromObjectToJsonString(T object) throws JsonProcessingException {
    return Mapper.getMapper().writeValueAsString(object);
  }

  public static <T> T fromJsonStringToObject(String json, Class<T> type) throws IOException {
    return Mapper.getMapper().readValue(json, type);
  }

  public static <T, R> T fromJsonStringToObject(String json, Class<T> iteratorClass, Class<R> objectClass) throws IOException {
    if (iteratorClass.equals(List.class)) {
      return Mapper.getMapper().readValue(json, Mapper.getMapper().getTypeFactory().constructCollectionType(List.class, objectClass));
    }
    return Mapper.getMapper().readValue(json, Mapper.getMapper().getTypeFactory().constructCollectionType(Set.class, objectClass));
  }
}
