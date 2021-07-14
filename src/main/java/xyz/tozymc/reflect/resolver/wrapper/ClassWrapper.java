package xyz.tozymc.reflect.resolver.wrapper;

import org.jetbrains.annotations.NotNull;
import xyz.tozymc.reflect.accessor.Accessors;
import xyz.tozymc.reflect.accessor.ConstructorAccessor;
import xyz.tozymc.reflect.accessor.FieldAccessor;
import xyz.tozymc.reflect.accessor.MethodAccessor;
import xyz.tozymc.util.Preconditions;

public class ClassWrapper<T> {

  private final Class<T> clazz;

  public ClassWrapper(@NotNull Class<T> clazz) {
    this.clazz = clazz;
  }

  public @NotNull Class<T> getClazz() {
    return clazz;
  }

  public FieldAccessor getField(@NotNull String name) {
    Preconditions.checkState(!name.isEmpty(), "Name cannot be empty");

    return Accessors.accessField(clazz, name);
  }

  public ConstructorAccessor<T> getConstructor(Class<?>... paramTypes) {
    return Accessors.accessConstructor(clazz, paramTypes);
  }

  public MethodAccessor getMethod(@NotNull String name, Class<?>... paramTypes) {
    return Accessors.accessMethod(clazz, name, paramTypes);
  }
}