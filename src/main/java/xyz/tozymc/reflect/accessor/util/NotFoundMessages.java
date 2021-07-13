package xyz.tozymc.reflect.accessor.util;

import org.jetbrains.annotations.NotNull;
import xyz.tozymc.reflect.accessor.Accessor.Query;

import java.util.Arrays;
import java.util.stream.Collectors;

public final class NotFoundMessages {

  private static final String FIELD_NOT_FOUND = "Field %s not found";
  private static final String CONSTRUCTOR_NOT_FOUND = "Constructor %s(%s) not found";
  private static final String METHOD_NOT_FOUND = "Method %s(%s) not found";

  private NotFoundMessages() {}

  public static String fieldNotFound(@NotNull Query query) {
    return String.format(FIELD_NOT_FOUND, query.name());
  }

  public static String constructorNotFound(@NotNull Query query) {
    return String.format(CONSTRUCTOR_NOT_FOUND, query.clazz().getSimpleName(),
        joinClassesToString(query.paramTypes()));
  }

  public static String methodNotFound(@NotNull Query query) {
    return String.format(METHOD_NOT_FOUND, query.name(), joinClassesToString(query.paramTypes()));
  }

  private static String joinClassesToString(Class<?>[] classes) {
    return Arrays.stream(classes).map(Class::getSimpleName).collect(Collectors.joining(","));
  }
}
