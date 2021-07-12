package xyz.tozymc.reflect.accessor;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.tozymc.reflect.util.AccessUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MethodAccessor implements Accessor {

  private final Method method;

  public MethodAccessor(@NotNull Method method) {
    this.method = AccessUtil.forceAccess(method);
  }

  @Nullable
  public <T> T invoke(@Nullable Object instance) {
    return invoke(instance, new Object[0]);
  }

  @Nullable
  public <R> R invoke(@Nullable Object instance, @Nullable Object... params) {
    try {
      //noinspection unchecked
      return (R) method.invoke(instance, params);
    } catch (IllegalAccessException ignored) {
    } catch (InvocationTargetException e) {
      throw new RuntimeException(e);
    }
    return null;
  }

  @NotNull
  public Method getMethod() {
    return method;
  }
}
