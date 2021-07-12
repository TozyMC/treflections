package xyz.tozymc.reflect.accessor;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.tozymc.reflect.util.AccessUtil;

public class ConstructorAccessor<T> implements Accessor {

  private final Constructor<T> constructor;

  public ConstructorAccessor(@NotNull Constructor<T> constructor) {
    this.constructor = AccessUtil.forceAccess(constructor);
  }

  @Nullable
  public T newInstance() {
    return newInstance(new Object[0]);
  }

  @Nullable
  public T newInstance(@Nullable Object... params) {
    try {
      return constructor.newInstance(params);
    } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
      throw new RuntimeException(e);
    }
  }

  @NotNull
  public Constructor<T> getConstructor() {
    return constructor;
  }
}
