package xyz.tozymc.reflect.resolver;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import xyz.tozymc.reflect.resolver.minecraft.NmsClassResolver;
import xyz.tozymc.reflect.resolver.minecraft.OcbClassResolver;
import xyz.tozymc.reflect.resolver.wrapper.ClassWrapper;
import xyz.tozymc.util.Preconditions;

/**
 * {@code ClassResolver} provides methods to find class and cache it if the class is found.
 *
 * @author TozyMC
 * @see NmsClassResolver
 * @see OcbClassResolver
 * @since 1.0
 */
@SuppressWarnings("ResultOfMethodCallIgnored")
public class ClassResolver {

  private final Map<String, Class<?>> resolvedClasses = new HashMap<>();

  protected ClassResolver() {}

  /**
   * Gets the {@code ClassResolver} instance.
   *
   * @return The class resolver instance.
   */
  @Contract(pure = true)
  public static @NotNull ClassResolver resolver() {
    return ClassResolverHelper.INSTANCE;
  }

  /**
   * Resolves the class and wrap the resolved class if was found.
   *
   * @param name The class name.
   * @param <T>  The type of class.
   * @return The ClassWrapper wrapped resolved class if was found.
   * @see #resolve(String)
   */
  public <T> ClassWrapper<T> resolveWrapper(@NotNull String name) {
    Preconditions.checkNotNull(name, "Name cannot be null");

    return new ClassWrapper<>(resolve(name));
  }

  /**
   * Resolves an array of possible names to a class and wrap the first resolved class if was found.
   *
   * @param names Class names.
   * @return The ClassWrapper wrapped first resolved class if was found.
   * @throws RuntimeException If no class is found.
   * @see #resolve(String...)
   */
  public ClassWrapper<?> resolveWrapper(@NotNull String... names) {
    Preconditions.checkNotNull(names, "Names cannot be null");

    return new ClassWrapper<>(resolve(names));
  }

  /**
   * Attempts to find and resolve an array of possible names to a class.
   *
   * @param names Class names.
   * @return The first resolved class.
   * @throws RuntimeException If no class is found.
   * @see #resolve(String)
   */
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

  /**
   * Attempts to find and resolve the class.
   *
   * @param name The class name.
   * @param <T>  The type of class.
   * @return The resolved class if was found.
   * @throws RuntimeException If no class is found.
   */
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
