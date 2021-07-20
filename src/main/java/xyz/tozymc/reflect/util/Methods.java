package xyz.tozymc.reflect.util;

import static xyz.tozymc.reflect.util.NotFoundMessages.methodNotFound;
import static xyz.tozymc.util.Preconditions.checkNotNull;
import static xyz.tozymc.util.Preconditions.checkState;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class Methods {

  private Methods() {}

  public static Method getMethod(@NotNull Class<?> clazz, @NotNull String name,
      Class<?>... paramTypes) {
    checkNotNull(clazz, "Clazz cannot be null");
    checkNotNull(name, "Name cannot be null");

    return getMethod0(clazz, name, paramTypes);
  }

  public static Method getMethod(@NotNull Object instance, @NotNull String name,
      Class<?>... paramTypes) {
    checkNotNull(instance, "Instance cannot be null");
    checkNotNull(name, "Name cannot be null");

    return getMethod0(instance.getClass(), name, paramTypes);
  }

  public static Object invokeMethod(@NotNull Class<?> clazz, @Nullable Object instance,
      @NotNull String name, Object... params)
      throws InvocationTargetException, IllegalAccessException {
    checkNotNull(clazz, "Clazz cannot be null");
    checkNotNull(name, "Name cannot be null");

    Class<?>[] paramTypes = Arrays.stream(params)
        .map(param -> param == null ? Object.class : param.getClass())
        .toArray(Class[]::new);

    Method method = getMethod0(clazz, name, paramTypes);
    return checkNotNull(method, methodNotFound(name, paramTypes)).invoke(instance, params);
  }

  public static Object invokeMethod(@NotNull Class<?> clazz, @Nullable Object instance,
      @NotNull String name, Class<?> @NotNull [] paramTypes, Object @NotNull ... params)
      throws InvocationTargetException, IllegalAccessException {
    checkNotNull(clazz, "Clazz cannot be null");
    checkNotNull(name, "Name cannot be null");

    checkState(paramTypes.length == params.length,
        "The length of parameter passed not equals parameter types");

    Method method = getMethod0(clazz, name, paramTypes);
    return checkNotNull(method, methodNotFound(name, paramTypes)).invoke(instance, params);
  }

  public static Object invokeMethod(@NotNull Object instance, @NotNull String name,
      Class<?> @NotNull [] paramTypes, Object @NotNull ... params)
      throws InvocationTargetException, IllegalAccessException {
    checkNotNull(instance, "Instance cannot be null");
    checkNotNull(name, "Name cannot be null");

    checkState(paramTypes.length == params.length,
        "The length of parameter passed not equals parameter types");

    Method method = getMethod0(instance.getClass(), name, paramTypes);
    return checkNotNull(method, methodNotFound(name, paramTypes)).invoke(instance, params);
  }

  public static Object invokeMethod(@Nullable Object instance, @NotNull String name,
      Object... params)
      throws InvocationTargetException, IllegalAccessException {
    checkNotNull(instance, "Instance cannot be null");
    checkNotNull(name, "Name cannot be null");

    Class<?>[] paramTypes = Arrays.stream(params)
        .map(param -> param == null ? Object.class : param.getClass())
        .toArray(Class[]::new);

    Method method = getMethod0(instance.getClass(), name, paramTypes);
    return checkNotNull(method, methodNotFound(name, paramTypes)).invoke(instance, params);
  }

  private static Method getMethod0(Class<?> clazz, String name, Class<?>[] paramTypes) {
    return Arrays.stream(clazz.getDeclaredMethods())
        .filter(method -> method.getName().equals(name) && Arrays.equals(method.getParameterTypes(),
            paramTypes))
        .findFirst()
        .orElse(null);
  }
}
