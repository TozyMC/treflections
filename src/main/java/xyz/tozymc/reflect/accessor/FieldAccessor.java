package xyz.tozymc.reflect.accessor;

import java.lang.reflect.Field;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.tozymc.reflect.util.AccessUtil;

public class FieldAccessor implements Accessor {

  private final Field field;

  public FieldAccessor(@NotNull Field field) {
    this.field = AccessUtil.forceAccess(field);
  }

  @Nullable
  public <R> R get(@Nullable Object instance) {
    try {
      //noinspection unchecked
      return (R) field.get(instance);
    } catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    }
  }

  public void set(@Nullable Object instance, @Nullable Object value) {
    try {
      field.set(instance, value);
    } catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    }
  }

  @NotNull
  public Field getField() {
    return field;
  }
}
