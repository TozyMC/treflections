package xyz.tozymc.reflect.accessor;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Objects;

public interface Accessor {

  enum Type {
    FIELD, METHOD
  }

  class Query {

    private final Class<?> clazz;
    private final Type type;
    private final String name;
    private final Class<?>[] paramTypes;
    private final Class<?> returnType;

    private Query(Class<?> clazz, @NotNull Type type, String name, Class<?>[] paramTypes,
        Class<?> returnType) {
      this.clazz = clazz;
      this.type = type;
      this.name = name;
      this.paramTypes = paramTypes;
      this.returnType = returnType;
    }

    @NotNull
    public Class<?> clazz() {
      return clazz;
    }

    @NotNull
    public Type type() {
      return this.type;
    }

    public String name() {
      return name;
    }

    public Class<?>[] paramTypes() {
      return paramTypes;
    }

    public Class<?> returnType() {
      return returnType;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (!(o instanceof Query)) {
        return false;
      }
      Query query = (Query) o;
      return Objects.equals(clazz, query.clazz) && type == query.type
          && Objects.equals(name, query.name) && Arrays.equals(paramTypes,
          query.paramTypes) && Objects.equals(returnType, query.returnType);
    }

    @Override
    public int hashCode() {
      int result = Objects.hash(clazz, type, name, returnType);
      result = 31 * result + Arrays.hashCode(paramTypes);
      return result;
    }

    @Override
    public String toString() {
      return "Query{" +
          "clazz=" + clazz +
          ", type=" + type +
          (name.isEmpty() ? "" : ", name='" + name + '\'') +
          (paramTypes.length == 0 ? "" : ", paramTypes=" + Arrays.toString(paramTypes)) +
          (returnType == null ? "" : ", returnType=" + returnType) +
          '}';
    }
  }

  class QueryBuilder {

    private final Class<?> clazz;
    private Type type = null;
    private String name = "";
    private Class<?>[] paramTypes = new Class[0];
    private Class<?> returnType = null;

    QueryBuilder(@NotNull Class<?> clazz) {
      this.clazz = clazz;
    }

    @Contract(value = "_ -> new", pure = true)
    public static @NotNull QueryBuilder builder(@NotNull Class<?> clazz) {
      return new QueryBuilder(clazz);
    }

    public static @NotNull QueryBuilder builder(@NotNull Query query) {
      return builder(query.clazz).type(query.type)
          .name(query.name)
          .paramTypes(query.paramTypes)
          .returnType(query.returnType);
    }

    public QueryBuilder name(@NotNull String name) {
      this.name = name;
      return this;
    }

    public QueryBuilder type(@NotNull Type type) {
      this.type = type;
      return this;
    }

    public QueryBuilder paramTypes(@NotNull Class<?>... paramTypes) {
      this.paramTypes = paramTypes;
      return this;
    }

    public QueryBuilder returnType(@NotNull Class<?> rType) {
      this.returnType = rType;
      return this;
    }

    public Query build() {
      if (type == null) {
        if (!name.isEmpty()) {
          throw new NullPointerException("Type cannot be null");
        }
        type = Type.METHOD;
      }
      return new Query(clazz, type, name, paramTypes, returnType);
    }
  }
}
