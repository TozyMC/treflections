package xyz.tozymc.reflect.accessor.util;

import org.jetbrains.annotations.NotNull;
import xyz.tozymc.reflect.accessor.Accessor.Query;

import java.util.Arrays;
import java.util.stream.Collectors;

public final class ErrorMessages {

  private static final String FIELD_ACCESS_FAILURE = "Cannot access field %s %s";
  private static final String CONSTRUCTOR_ACCESS_FAILURE = "Cannot access constructor %s";
  private static final String METHOD_ACCESS_FAILURE = "Cannot access method %s %s";

  private ErrorMessages() {}

  public static String fieldAccessFailure(@NotNull Query query) {
    Class<?> rType = query.returnType();
    return String.format(FIELD_ACCESS_FAILURE, rType == null ? "?" : rType.getSimpleName(),
        query.name());
  }

  public static String constructorAccessFailure(@NotNull Query query) {
    return String.format(CONSTRUCTOR_ACCESS_FAILURE, formatMethod(query.clazz().getSimpleName(),
        query.paramTypes()));
  }

  public static String methodAccessFailure(@NotNull Query query) {
    Class<?> rType = query.returnType();
    return String.format(METHOD_ACCESS_FAILURE, rType == null ? "?" : rType.getSimpleName(),
        formatMethod(query.name(), query.paramTypes()));
  }

  private static String formatMethod(String name, Object[] params) {
    return String.format("%s(%s)", name,
        Arrays.stream(params).map(String::valueOf).collect(Collectors.joining(",")));
  }
}
