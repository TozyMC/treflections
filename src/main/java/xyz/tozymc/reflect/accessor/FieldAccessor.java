package xyz.tozymc.reflect.accessor;

import java.lang.reflect.Field;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.tozymc.reflect.util.AccessUtil;

/**
 * The class that wraps an accessible {@code Field}.
 *
 * @author TozyMC
 * @since 1.0
 */
public class FieldAccessor implements Accessor {

  private final Field field;

  /**
   * Creates {@code FieldAccessor} instance and makes {@code Field} is accessible.
   *
   * @param field The field to access.
   */
  public FieldAccessor(@NotNull Field field) {
    this.field = AccessUtil.forceAccess(field);
  }

  /**
   * Gets the value of the {@code Field}.
   *
   * @param instance Object from which the represented field's value is to be extracted.
   * @param <R>      Return type of field.
   * @return The value of the represented field in object {@code instance}.
   * @throws RuntimeException If getting the value of field is failed.
   * @see Field#get(Object)
   */
  @Nullable
  public <R> R get(@Nullable Object instance) {
    try {
      //noinspection unchecked
      return (R) field.get(instance);
    } catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Sets the value of the {@code Field}.
   *
   * @param instance The object whose field should be modified.
   * @param value    New value for the field.
   */
  public void set(@Nullable Object instance, @Nullable Object value) {
    try {
      field.set(instance, value);
    } catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Gets the accessible field.
   *
   * @return The accessible field.
   */
  @NotNull
  public Field getField() {
    return field;
  }
}
