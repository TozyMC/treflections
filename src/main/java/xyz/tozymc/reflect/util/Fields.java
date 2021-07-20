package xyz.tozymc.reflect.util;


import static xyz.tozymc.reflect.util.NotFoundMessages.fieldNotFound;
import static xyz.tozymc.util.Preconditions.checkNotNull;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.util.Arrays;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class Fields {

  private Fields() {}

  public static Field getField(@NotNull Class<?> clazz, @NotNull String name) {
    checkNotNull(clazz, "Clazz cannot be null");
    checkNotNull(name, "Name cannot be null");

    return getField0(clazz, name);
  }

  public static Field getField(@NotNull Object instance, @NotNull String name) {
    checkNotNull(instance, "Instance cannot be null");
    checkNotNull(name, "Name cannot be null");

    return getField0(instance.getClass(), name);
  }

  public static Object readField(@NotNull Class<?> clazz, @Nullable Object instance,
      @NotNull String name) throws IllegalAccessException {
    checkNotNull(clazz, "Clazz cannot be null");
    checkNotNull(name, "Name cannot be null");

    Field field = getField0(clazz, name);
    return checkNotNull(field, fieldNotFound(name)).get(instance);
  }

  public static Object readField(@NotNull Object instance, @NotNull String name)
      throws IllegalAccessException {
    checkNotNull(instance, "Instance cannot be null");
    checkNotNull(name, "Name cannot be null");

    Field field = getField0(instance.getClass(), name);
    return checkNotNull(field, fieldNotFound(name)).get(instance);
  }

  public static void writeField(@NotNull Class<?> clazz, @Nullable Object instance,
      @NotNull String name, @Nullable Object value) throws IllegalAccessException {
    checkNotNull(clazz, "Clazz cannot be null");
    checkNotNull(name, "Name cannot be null");

    Field field = getField0(clazz, name);
    checkNotNull(field, fieldNotFound(name)).set(instance, value);
  }

  public static void writeField(@NotNull Object instance,
      @NotNull String name, @Nullable Object value) throws IllegalAccessException {
    checkNotNull(instance, "Instance cannot be null");
    checkNotNull(name, "Name cannot be null");

    Field field = getField0(instance.getClass(), name);
    checkNotNull(field, fieldNotFound(name)).set(instance, value);
  }

  private static Field getField0(Class<?> clazz, String name) {
    return Arrays.stream(clazz.getDeclaredFields())
        .filter(field -> field.getName().equals(name))
        .findFirst()
        .orElse(null);
  }
}
