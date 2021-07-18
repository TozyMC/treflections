package xyz.tozymc.reflect.accessor;

import static xyz.tozymc.reflect.accessor.Accessor.Type.FIELD;
import static xyz.tozymc.reflect.accessor.Accessor.Type.METHOD;
import static xyz.tozymc.reflect.util.NotFoundMessages.constructorNotFound;
import static xyz.tozymc.reflect.util.NotFoundMessages.fieldNotFound;
import static xyz.tozymc.reflect.util.NotFoundMessages.methodNotFound;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import xyz.tozymc.reflect.accessor.Accessor.Query;
import xyz.tozymc.reflect.accessor.Accessor.QueryBuilder;
import xyz.tozymc.reflect.util.Constructors;
import xyz.tozymc.reflect.util.Fields;
import xyz.tozymc.reflect.util.Methods;
import xyz.tozymc.util.Preconditions;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Utilities to access class members.
 *
 * <p>Accessed members will be cached to optimize performance.
 *
 * @author TozyMC
 * @since 1.0
 */
public final class Accessors {

  private static final Map<Query, Accessor> accessedObjects = new HashMap<>();

  /**
   * Creates new field accessor with field is accessible.
   *
   * <p><b>Notes: </b>This field will not be cached.
   *
   * @param field Field to access.
   * @return Field accessor with field is accessible.
   */
  @Contract("_ -> new")
  public static @NotNull FieldAccessor accessField(@NotNull Field field) {
    return new FieldAccessor(field);
  }

  /**
   * Creates new constructor accessor with constructor is accessible.
   *
   * <p><b>Notes: </b>This constructor will not be cached.
   *
   * @param constructor Constructor to access.
   * @param <T>         Type of constructor
   * @return Constructor accessor with constructor is accessible.
   */
  @Contract("_ -> new")
  public static <T> @NotNull ConstructorAccessor<T> accessConstructor(
      @NotNull Constructor<T> constructor) {
    return new ConstructorAccessor<>(constructor);
  }

  /**
   * Creates new method accessor with method is accessible.
   *
   * <p><b>Notes: </b>This method will not be cached.
   *
   * @param method Method to access.
   * @return Method accessor with field is accessible.
   */
  @Contract("_ -> new")
  public static @NotNull MethodAccessor accessMethod(@NotNull Method method) {
    return new MethodAccessor(method);
  }

  /**
   * Finds and accesses the field in the class if the field is found.
   *
   * @param clazz Class contains the field.
   * @param name  Name of the field.
   * @return Field accessor with field found.
   * @see #accessField(Query)
   */
  public static FieldAccessor accessField(@NotNull Class<?> clazz, @NotNull String name) {
    return accessField(QueryBuilder.builder(clazz).type(FIELD).name(name).build());
  }

  /**
   * Finds and accesses the constructor in the class if the constructor is found.
   *
   * @param clazz      Class contains the constructor.
   * @param paramTypes Types of constructor parameter.
   * @param <T>        Type of constructor.
   * @return Constructor accessor with constructor found.
   * @see #accessConstructor(Query)
   */
  public static <T> ConstructorAccessor<T> accessConstructor(@NotNull Class<?> clazz,
      @NotNull Class<?>... paramTypes) {
    return accessConstructor(
        QueryBuilder.builder(clazz).type(METHOD).paramTypes(paramTypes).build());
  }

  /**
   * Finds and accesses the method in the class if the method is found.
   *
   * @param clazz      Class contains the method.
   * @param name       Name of the method.
   * @param paramTypes Types of method parameter.
   * @return Method accessor with method found.
   * @see #accessMethod(Query)
   */
  public static MethodAccessor accessMethod(@NotNull Class<?> clazz, @NotNull String name,
      @NotNull Class<?>... paramTypes) {
    return accessMethod(
        QueryBuilder.builder(clazz).type(METHOD).name(name).paramTypes(paramTypes).build());
  }

  /**
   * Finds and accesses which field is found first.
   *
   * @param queries Queries for accessor.
   * @return The field accessor found first.
   * @throws RuntimeException If no fields are found among the passed queries.
   */
  public static @NotNull FieldAccessor accessField(Query @NotNull [] queries) {
    for (Query query : queries) {
      FieldAccessor accessed = null;
      try {
        accessed = accessField(query);
      } catch (Exception ignored) {
      }
      if (accessed != null) {
        return accessed;
      }
    }
    throw new RuntimeException("Queries: " + Arrays.toString(queries), new NoSuchFieldException());
  }

  /**
   * Finds and accesses which constructor is found first.
   *
   * @param queries Queries for accessor.
   * @return The constructor accessor found first.
   * @throws RuntimeException If no constructors are found among the passed queries.
   */
  public static @NotNull ConstructorAccessor<?> accessConstructor(Query @NotNull [] queries) {
    for (Query query : queries) {
      ConstructorAccessor<?> accessed = null;
      try {
        accessed = accessConstructor(query);
      } catch (Exception ignored) {
      }
      if (accessed != null) {
        return accessed;
      }
    }
    throw new RuntimeException("Queries: " + Arrays.toString(queries), new NoSuchMethodException());
  }

  /**
   * Finds and accesses which method is found first.
   *
   * @param queries Queries for accessor.
   * @return The method accessor found first.
   * @throws RuntimeException If no methods are found among the passed queries.
   */
  public static @NotNull MethodAccessor accessMethod(Query @NotNull [] queries) {
    for (Query query : queries) {
      MethodAccessor accessed = null;
      try {
        accessed = accessMethod(query);
      } catch (Exception ignored) {
      }
      if (accessed != null) {
        return accessed;
      }
    }
    throw new RuntimeException("Queries: " + Arrays.toString(queries), new NoSuchMethodException());
  }

  /**
   * Finds and accesses the field in the class if the field is found by using {@link Query} class.
   *
   * @param query The query for accessor.
   * @return Field accessor with field found.
   * @throws IllegalArgumentException If query type is not FIELD or query name is empty.
   * @throws NullPointerException     If method is not found.
   */
  public static FieldAccessor accessField(@NotNull Query query) {
    Preconditions.checkArgument(query.type().equals(FIELD), "Type must be FIELD");
    Preconditions.checkArgument(!query.name().isEmpty(), "Name cannot be empty");

    if (accessedObjects.containsKey(query)) {
      return (FieldAccessor) accessedObjects.get(query);
    }

    Field field = Fields.getField(query.clazz(), query.name());
    if (field == null) {
      throw new NullPointerException(fieldNotFound(query.name()));
    }

    FieldAccessor accessed = new FieldAccessor(field);
    accessedObjects.put(query, accessed);
    return accessed;
  }

  /**
   * Finds and accesses the constructor in the class if the constructor is found by using {@link
   * Query} class.
   *
   * @param query The query for accessor.
   * @param <T>   Type of constructor.
   * @return Constructor accessor with constructor found.
   * @throws IllegalArgumentException If query type is not METHOD.
   * @throws NullPointerException     If method is not found.
   */
  @SuppressWarnings("unchecked")
  public static <T> ConstructorAccessor<T> accessConstructor(@NotNull Query query) {
    Preconditions.checkArgument(query.type().equals(METHOD), "Type must be METHOD");

    if (accessedObjects.containsKey(query)) {
      return (ConstructorAccessor<T>) accessedObjects.get(query);
    }

    Constructor<?> constructor = Constructors.getConstructor(query.clazz(), query.paramTypes());
    if (constructor == null) {
      throw new NullPointerException(constructorNotFound(query.clazz(), query.paramTypes()));
    }

    ConstructorAccessor<T> accessed = new ConstructorAccessor<>((Constructor<T>) constructor);
    accessedObjects.put(query, accessed);
    return accessed;
  }

  /**
   * Finds and accesses the method in the class if the method is found by using {@link Query}
   * class.
   *
   * @param query The query for accessor.
   * @return Method accessor with method found.
   * @throws IllegalArgumentException If query type is not METHOD or query name is empty.
   * @throws NullPointerException     If method is not found.
   */
  public static MethodAccessor accessMethod(@NotNull Query query) {
    Preconditions.checkArgument(query.type().equals(METHOD), "Type must be METHOD");
    Preconditions.checkArgument(!query.name().isEmpty(), "Name cannot be empty");

    if (accessedObjects.containsKey(query)) {
      return (MethodAccessor) accessedObjects.get(query);
    }

    Method method = Methods.getMethod(query.clazz(), query.name(), query.paramTypes());
    if (method == null) {
      throw new NullPointerException(methodNotFound(query.name(), query.paramTypes()));
    }

    MethodAccessor accessed = new MethodAccessor(method);
    accessedObjects.put(query, accessed);
    return accessed;
  }
}
