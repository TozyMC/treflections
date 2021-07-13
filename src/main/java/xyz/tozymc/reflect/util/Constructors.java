package xyz.tozymc.reflect.util;

import static xyz.tozymc.reflect.util.NotFoundMessages.constructorNotFound;
import static xyz.tozymc.util.Preconditions.checkNotNull;
import static xyz.tozymc.util.Preconditions.checkState;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class Constructors {

  private Constructors() {}

  public static Constructor<?> getConstructor(@NotNull Class<?> clazz, Class<?>... paramTypes) {
    checkNotNull(clazz, "Clazz cannot be null");

    return getConstructor0(clazz, paramTypes);
  }

  public static Constructor<?> getConstructor(@NotNull Object instance, Class<?>... paramTypes) {
    checkNotNull(instance, "Instance cannot be null");

    return getConstructor0(instance.getClass(), paramTypes);
  }

  public static @NotNull Object newInstance(@NotNull Class<?> clazz,
      Class<?> @NotNull [] paramTypes, Object @NotNull ... params)
      throws InvocationTargetException, InstantiationException, IllegalAccessException {
    checkNotNull(clazz, "Clazz cannot be null");
    checkState(paramTypes.length == params.length,
        "The length of parameter passed not equals parameter types");

    Constructor<?> constructor = getConstructor0(clazz, paramTypes);
    return checkNotNull(constructor, constructorNotFound(clazz, paramTypes)).newInstance(params);
  }

  public static @NotNull Object newInstance(@NotNull Class<?> clazz, Object... params)
      throws InvocationTargetException, InstantiationException, IllegalAccessException {
    checkNotNull(clazz, "Clazz cannot be null");

    Class<?>[] paramTypes = Arrays.stream(params)
        .map(param -> param == null ? Object.class : param.getClass())
        .toArray(Class[]::new);

    Constructor<?> constructor = getConstructor0(clazz, paramTypes);
    return checkNotNull(constructor, constructorNotFound(clazz, paramTypes)).newInstance(params);
  }

  public static @NotNull Object newInstance(@NotNull Object instance, Object @NotNull ... params)
      throws InvocationTargetException, InstantiationException, IllegalAccessException {
    checkNotNull(instance, "Instance cannot be null");

    Class<?>[] paramTypes = Arrays.stream(params)
        .map(param -> param == null ? Object.class : param.getClass())
        .toArray(Class[]::new);

    Class<?> clazz = instance.getClass();
    Constructor<?> constructor = getConstructor0(clazz, paramTypes);
    return checkNotNull(constructor, constructorNotFound(clazz, paramTypes)).newInstance(params);
  }

  public static @NotNull Object newInstance(@NotNull Object instance,
      Class<?> @NotNull [] paramTypes, Object @NotNull ... params)
      throws InvocationTargetException, InstantiationException, IllegalAccessException {
    checkNotNull(instance, "Instance cannot be null");
    checkState(paramTypes.length == params.length,
        "The length of parameter passed not equals parameter types");

    Class<?> clazz = instance.getClass();
    Constructor<?> constructor = getConstructor0(clazz, paramTypes);
    return checkNotNull(constructor, constructorNotFound(clazz, paramTypes)).newInstance(params);
  }

  private static Constructor<?> getConstructor0(Class<?> clazz, Class<?>[] paramTypes) {
    return Arrays.stream(clazz.getDeclaredConstructors())
        .filter(constructor -> Arrays.equals(constructor.getParameterTypes(), paramTypes))
        .findFirst()
        .orElse(null);
  }
}
