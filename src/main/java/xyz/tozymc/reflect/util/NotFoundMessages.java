package xyz.tozymc.reflect.util;

import java.util.Arrays;
import java.util.stream.Collectors;

public final class NotFoundMessages {

  private static final String FIELD_NOT_FOUND = "Field %s not found";
  private static final String CONSTRUCTOR_NOT_FOUND = "Constructor %s(%s) not found";
  private static final String METHOD_NOT_FOUND = "Method %s(%s) not found";

  private NotFoundMessages() {}

  public static String fieldNotFound(String name) {
    return String.format(FIELD_NOT_FOUND, name);
  }

  public static String constructorNotFound(Class<?> clazz, Class<?>... paramTypes) {
    return String.format(CONSTRUCTOR_NOT_FOUND, clazz.getSimpleName(),
        joinClassesToString(paramTypes));
  }

  public static String methodNotFound(String name, Class<?>... paramTypes) {
    return String.format(METHOD_NOT_FOUND, name, joinClassesToString(paramTypes));
  }

  private static String joinClassesToString(Class<?>[] classes) {
    return Arrays.stream(classes).map(Class::getSimpleName).collect(Collectors.joining(","));
  }
}
