package xyz.tozymc.reflect.accessor;

import java.lang.reflect.Method;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.tozymc.reflect.util.AccessUtil;

/**
 * The class that wraps an accessible {@code Method}.
 *
 * @author TozyMC
 * @since 1.0
 */
public class MethodAccessor implements Accessor {

  private final Method method;

  /**
   * Creates {@code MethodAccessor} instance and makes {@code Method} is accessible.
   *
   * @param method The method to access.
   */
  public MethodAccessor(@NotNull Method method) {
    this.method = AccessUtil.forceAccess(method);
  }

  /**
   * Invokes the underlying method represented by accessible {@code Method} object, with empty
   * parameters.
   *
   * @param instance The object the underlying method is invoked from.
   * @param <R>      Return type of the method.
   * @return The return value after the method call completes, or {@code null} for void methods.
   * @throws RuntimeException If invoking method is failed.
   * @see #invoke(Object, Object...)
   */
  @Nullable
  public <R> R invoke(@Nullable Object instance) {
    return invoke(instance, new Object[0]);
  }

  /**
   * Invokes the underlying method represented by accessible {@code Method} object.
   *
   * @param instance The object the underlying method is invoked from.
   * @param params   Parameters passed the method.
   * @param <R>      Return type of the method.
   * @return The return value after the method call completes, or {@code null} for void methods.
   * @throws RuntimeException If invoking method is failed.
   * @see Method#invoke(Object, Object...)
   */
  @Nullable
  public <R> R invoke(@Nullable Object instance, @Nullable Object... params) {
    try {
      //noinspection unchecked
      return (R) method.invoke(instance, params);
    } catch (ReflectiveOperationException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Gets the accessible method.
   *
   * @return The accessible method.
   */
  @NotNull
  public Method getMethod() {
    return method;
  }
}
