package xyz.tozymc.reflect.resolver;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ClassResolver {

  private final Map<String, Class<?>> resolvedClasses = new HashMap<>();

  protected ClassResolver() {}

  @Contract(pure = true)
  public static @NotNull ClassResolver resolver() {
    return ClassResolverHelper.INSTANCE;
  }

  public Class<?> resolve(String @NotNull ... names) {
    Class<?> clazz = null;
    for (String name : names) {
      try {
        clazz = resolve(name);
      } catch (Exception ignored) {
      }
      if (clazz != null) {
        return clazz;
      }
    }
    String cannotResolveClasses = "Cannot resolve classes ";
    throw new RuntimeException(cannotResolveClasses + Arrays.toString(names),
        new ClassNotFoundException());
  }

  @SuppressWarnings("unchecked")
  public <T> Class<T> resolve(@NotNull String name) {
    if (resolvedClasses.containsKey(name)) {
      return (Class<T>) resolvedClasses.get(name);
    }
    try {
      Class<T> clazz = (Class<T>) Class.forName(name);
      resolvedClasses.put(name, clazz);
      return clazz;
    } catch (ClassNotFoundException e) {
      String cannotResolveClass = "Cannot resolve class ";
      throw new RuntimeException(cannotResolveClass + name, e.getCause());
    }
  }

  private static class ClassResolverHelper {

    private static final ClassResolver INSTANCE = new ClassResolver();
  }
}
