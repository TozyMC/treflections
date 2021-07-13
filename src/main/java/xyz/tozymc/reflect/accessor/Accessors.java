package xyz.tozymc.reflect.accessor;

import static xyz.tozymc.reflect.accessor.Accessor.Type.FIELD;
import static xyz.tozymc.reflect.accessor.Accessor.Type.METHOD;
import static xyz.tozymc.reflect.accessor.util.ErrorMessages.constructorAccessFailure;
import static xyz.tozymc.reflect.accessor.util.ErrorMessages.fieldAccessFailure;
import static xyz.tozymc.reflect.accessor.util.ErrorMessages.methodAccessFailure;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import xyz.tozymc.reflect.accessor.Accessor.Query;
import xyz.tozymc.reflect.accessor.Accessor.QueryBuilder;
import xyz.tozymc.reflect.accessor.Accessor.Type;
import xyz.tozymc.util.Preconditions;

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

  public static FieldAccessor accessField(@NotNull Class<?> clazz, @NotNull String name,
      @NotNull Class<?> fieldType) {
    return accessField(
        QueryBuilder.builder(clazz).type(FIELD).name(name).returnType(fieldType).build());
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

  public static MethodAccessor accessMethod(@NotNull Class<?> clazz, @NotNull Class<?> rType,
      @NotNull String name, @NotNull Class<?>... paramTypes) {
    return accessMethod(
        QueryBuilder.builder(clazz)
            .type(METHOD)
            .name(name)
            .paramTypes(paramTypes)
            .returnType(rType)
            .build());
  }

  public static @NotNull FieldAccessor accessField(Query @NotNull [] queries) {
    FieldAccessor accessed = null;
    for (Query query : queries) {
      try {
        accessed = accessField(query);
      } catch (Exception ignored) {
      }
      if (accessed != null) {
        break;
      }
    }
    if (accessed == null) {
      throw new RuntimeException("Queries: " + Arrays.toString(queries),
          new NoSuchFieldException());
    }

    return accessed;
  }

  public static @NotNull ConstructorAccessor<?> accessConstructor(Query @NotNull [] queries) {
    ConstructorAccessor<?> accessed = null;
    for (Query query : queries) {
      try {
        accessed = accessConstructor(query);
      } catch (Exception ignored) {
      }
      if (accessed != null) {
        break;
      }
    }
    if (accessed == null) {
      throw new RuntimeException("Queries: " + Arrays.toString(queries),
          new NoSuchMethodException());
    }

    return accessed;
  }

  public static @NotNull MethodAccessor accessMethod(Query @NotNull [] queries) {
    MethodAccessor accessed = null;
    for (Query query : queries) {
      try {
        accessed = accessMethod(query);
      } catch (Exception ignored) {
      }
      if (accessed != null) {
        break;
      }
    }
    if (accessed == null) {
      throw new RuntimeException("Queries: " + Arrays.toString(queries),
          new NoSuchMethodException());
    }

    return accessed;
  }

  public static FieldAccessor accessField(@NotNull Query query) {
    Preconditions.checkArgument(query.type().equals(Type.FIELD), "Type must be FIELD");
    Preconditions.checkArgument(!query.name().isEmpty(), "Name cannot be empty");

    if (accessedObjects.containsKey(query)) {
      return (FieldAccessor) accessedObjects.get(query);
    }

    Field field;
    try {
      field = query.clazz().getDeclaredField(query.name());
    } catch (NoSuchFieldException e) {
      throw new RuntimeException(fieldAccessFailure(query), e.getCause());
    }

    Class<?> rType = query.returnType();
    if (rType != null && field.getType() != rType) {
      throw new RuntimeException(fieldAccessFailure(query), new NoSuchFieldException());
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

    Constructor<T> constructor;
    try {
      constructor = ((Class<T>) query.clazz()).getDeclaredConstructor(query.paramTypes());
    } catch (NoSuchMethodException e) {
      throw new RuntimeException(constructorAccessFailure(query), e.getCause());
    }

    ConstructorAccessor<T> accessed = new ConstructorAccessor<>(constructor);
    accessedObjects.put(query, accessed);
    return accessed;
  }

  public static MethodAccessor accessMethod(@NotNull Query query) {
    Preconditions.checkArgument(query.type().equals(METHOD), "Type must be METHOD");
    Preconditions.checkArgument(!query.name().isEmpty(), "Name cannot be empty");

    if (accessedObjects.containsKey(query)) {
      return (MethodAccessor) accessedObjects.get(query);
    }

    Method method;
    try {
      method = query.clazz().getDeclaredMethod(query.name(), query.paramTypes());
    } catch (NoSuchMethodException e) {
      throw new RuntimeException(methodAccessFailure(query), e.getCause());
    }

    Class<?> rType = query.returnType();
    if (rType != null && !method.getReturnType().equals(rType)) {
      throw new RuntimeException(methodAccessFailure(query), new NoSuchMethodException());
    }

    MethodAccessor accessed = new MethodAccessor(method);
    accessedObjects.put(query, accessed);
    return accessed;
  }
}
