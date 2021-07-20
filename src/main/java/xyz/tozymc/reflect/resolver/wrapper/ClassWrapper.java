package xyz.tozymc.reflect.resolver.wrapper;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import xyz.tozymc.reflect.accessor.Accessors;
import xyz.tozymc.reflect.accessor.ConstructorAccessor;
import xyz.tozymc.reflect.accessor.FieldAccessor;
import xyz.tozymc.reflect.accessor.MethodAccessor;
import xyz.tozymc.util.Preconditions;

/**
 * {@code ClassWrapper} provides methods to easily get class members.
 *
 * @param <T> Type of declared class.
 * @author TozyMC
 * @since 1.0
 */
public class ClassWrapper<T> {

  private final Class<T> clazz;

  /**
   * Creates new class wrapper instance.
   *
   * @param clazz The class to wrap.
   */
  @Contract(pure = true)
  public ClassWrapper(@NotNull Class<T> clazz) {
    this.clazz = clazz;
  }

  /**
   * Gets the declared class that this object contains.
   *
   * @return The declared class that this object contains.
   */
  public @NotNull Class<T> getClazz() {
    return clazz;
  }

  /**
   * Finds and accesses the field in the class if the field is found.
   *
   * @param name Name of field.
   * @return Field accessor with field found.
   * @see Accessors#accessField(Class, String)
   */
  public FieldAccessor getField(@NotNull String name) {
    Preconditions.checkState(!name.isEmpty(), "Name cannot be empty");

    return Accessors.accessField(clazz, name);
  }

  /**
   * Finds and accesses the constructor in the class if the constructor is found.
   *
   * @param paramTypes Types of constructor parameter.
   * @return Constructor accessor with constructor found.
   * @see Accessors#accessConstructor(Class, Class[])
   */
  public ConstructorAccessor<T> getConstructor(Class<?>... paramTypes) {
    return Accessors.accessConstructor(clazz, paramTypes);
  }

  /**
   * Finds and accesses the method in the class if the method is found.
   *
   * @param name       Name of method.
   * @param paramTypes Types of method parameter.
   * @return Method accessor with method found.
   * @see Accessors#accessMethod(Class, String, Class[])
   */
  public MethodAccessor getMethod(@NotNull String name, Class<?>... paramTypes) {
    Preconditions.checkState(!name.isEmpty(), "Name cannot be empty");

    return Accessors.accessMethod(clazz, name, paramTypes);
  }
}