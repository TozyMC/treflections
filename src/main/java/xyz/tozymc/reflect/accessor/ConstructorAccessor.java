package xyz.tozymc.reflect.accessor;

import java.lang.reflect.Constructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.tozymc.reflect.util.AccessUtil;

/**
 * The class that wraps an accessible {@code Constructor}.
 *
 * @param <T> The class in which the constructor is declared.
 * @author TozyMC
 * @since 1.0
 */
public class ConstructorAccessor<T> implements Accessor {

  private final Constructor<T> constructor;

  /**
   * Creates {@code ConstructorAccessor<T>} instance and makes {@code Constructor} is accessible.
   *
   * @param constructor The constructor to access.
   */
  public ConstructorAccessor(@NotNull Constructor<T> constructor) {
    this.constructor = AccessUtil.forceAccess(constructor);
  }

  /**
   * Creates new instance of the constructor's declaring class with empty parameters.
   *
   * @return A new object created.
   * @see #newInstance(Object...)
   */
  @Nullable
  public T newInstance() {
    return newInstance(new Object[0]);
  }

  /**
   * Creates new instance of the constructor's declaring class.
   *
   * @param params Parameters passed to the constructor.
   * @return A new object created.
   * @throws RuntimeException If invoke constructor is failed.
   * @see Constructor#newInstance(Object...)
   */
  @Nullable
  public T newInstance(@Nullable Object... params) {
    try {
      return constructor.newInstance(params);
    } catch (ReflectiveOperationException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Gets the accessible constructor.
   *
   * @return The accessible constructor.
   */
  @NotNull
  public Constructor<T> getConstructor() {
    return constructor;
  }
}
