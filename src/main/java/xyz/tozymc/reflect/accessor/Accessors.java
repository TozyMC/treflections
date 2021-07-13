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

public final class Accessors {

  private static final Map<Query, Accessor> accessedObjects = new HashMap<>();

  @Contract("_ -> new")
  public static @NotNull FieldAccessor accessField(@NotNull Field field) {
    return new FieldAccessor(field);
  }

  @Contract("_ -> new")
  public static <T> @NotNull ConstructorAccessor<T> accessConstructor(
      @NotNull Constructor<T> constructor) {
    return new ConstructorAccessor<>(constructor);
  }

  @Contract("_ -> new")
  public static @NotNull MethodAccessor accessMethod(@NotNull Method method) {
    return new MethodAccessor(method);
  }

  public static FieldAccessor accessField(@NotNull Class<?> clazz, @NotNull String name) {
    return accessField(QueryBuilder.builder(clazz).type(FIELD).name(name).build());
  }

  public static <T> ConstructorAccessor<T> accessConstructor(@NotNull Class<?> clazz,
      @NotNull Class<?>... paramTypes) {
    return accessConstructor(
        QueryBuilder.builder(clazz).type(METHOD).paramTypes(paramTypes).build());
  }

  public static MethodAccessor accessMethod(@NotNull Class<?> clazz, @NotNull String name,
      @NotNull Class<?>... paramTypes) {
    return accessMethod(
        QueryBuilder.builder(clazz).type(METHOD).name(name).paramTypes(paramTypes).build());
  }

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
