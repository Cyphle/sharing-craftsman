package fr.knowledge.common;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public class Mapper {
  private static final Logger log = LoggerFactory.getLogger(Mapper.class);
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

  public static <T> String fromObjectToJsonString(T object) {
    try {
      return Mapper.getMapper().writeValueAsString(object);
    } catch (JsonProcessingException e) {
      log.error("Error while serializing: " + object.getClass().getName() + ", " + e.getMessage());
      throw new RuntimeException("Error while serializing: " + object.getClass().getName() + ", " + e.getMessage());
    }
  }

  public static <T> T fromJsonStringToObject(String json, Class<T> type) {
    try {
      return Mapper.getMapper().readValue(json, type);
    } catch (IOException e) {
      log.error("Error while deserializing: " + type + ", " + e.getMessage());
      throw new RuntimeException("Error while deserializing: " + type + ", " + e.getMessage());
    }
  }

  public static <T, R> T fromJsonStringToObject(String json, Class<T> iteratorClass, Class<R> objectClass) throws IOException {
    if (iteratorClass.equals(List.class)) {
      return Mapper.getMapper().readValue(json, Mapper.getMapper().getTypeFactory().constructCollectionType(List.class, objectClass));
    }
    return Mapper.getMapper().readValue(json, Mapper.getMapper().getTypeFactory().constructCollectionType(Set.class, objectClass));
  }
}
