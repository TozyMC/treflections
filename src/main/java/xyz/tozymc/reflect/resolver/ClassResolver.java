package xyz.tozymc.reflect.resolver;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import xyz.tozymc.reflect.resolver.wrapper.ClassWrapper;
import xyz.tozymc.util.Preconditions;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class ClassResolver {

  private final Map<String, Class<?>> resolvedClasses = new HashMap<>();

  protected ClassResolver() {}

  @Contract(pure = true)
  public static @NotNull ClassResolver resolver() {
    return ClassResolverHelper.INSTANCE;
  }

  public <T> ClassWrapper<T> resolveWrapper(@NotNull String name) {
    Preconditions.checkNotNull(name, "Name cannot be null");

    return new ClassWrapper<>(resolve(name));
  }

  public ClassWrapper<?> resolveWrapper(@NotNull String... names) {
    Preconditions.checkNotNull(names, "Names cannot be null");

    return new ClassWrapper<>(resolve(names));
  }

  public Class<?> resolve(String @NotNull ... names) {
    Preconditions.checkNotNull(names, "Names cannot be null");

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
    throw new RuntimeException("Cannot resolve classes " + Arrays.toString(names));
  }

  @SuppressWarnings("unchecked")
  public <T> Class<T> resolve(@NotNull String name) {
    Preconditions.checkNotNull(name, "Name cannot be null");

    if (resolvedClasses.containsKey(name)) {
      return (Class<T>) resolvedClasses.get(name);
    }
    try {
      Class<T> clazz = (Class<T>) Class.forName(name);
      resolvedClasses.put(name, clazz);
      return clazz;
    } catch (ClassNotFoundException e) {
      throw new RuntimeException("Cannot resolve class " + name);
    }
  }

  private static class ClassResolverHelper {

    private static final ClassResolver INSTANCE = new ClassResolver();
  }
}
