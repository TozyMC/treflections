package xyz.tozymc.reflect.accessor;

import java.util.Arrays;
import java.util.Objects;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Interface to mark accessor classes.
 *
 * @since 1.0
 */
public interface Accessor {

  /**
   * Represents the accessor type.
   *
   * @since 1.0
   */
  enum Type {
    FIELD, METHOD
  }

  /**
   * The class that stores the query for the accessor.
   *
   * @author TozyMC
   * @since 1.0
   */
  class Query {

    private final Class<?> clazz;
    private final Type type;
    private final String name;
    private final Class<?>[] paramTypes;

    private Query(Class<?> clazz, @NotNull Type type, String name, Class<?>[] paramTypes) {
      this.clazz = clazz;
      this.type = type;
      this.name = name;
      this.paramTypes = paramTypes;
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
          query.paramTypes);
    }

    @Override
    public int hashCode() {
      int result = Objects.hash(clazz, type, name);
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
          '}';
    }
  }

  /**
   * The class used to construct the query for the accessor.
   *
   * @author TozyMC
   * @since 1.0
   */
  class QueryBuilder {

    private final Class<?> clazz;
    private Type type = null;
    private String name = "";
    private Class<?>[] paramTypes = new Class[0];

    QueryBuilder(@NotNull Class<?> clazz) {
      this.clazz = clazz;
    }

    /**
     * Creates new {@link QueryBuilder} instance with class.
     *
     * @param clazz Class to query.
     * @return An instance of {@code QueryBuilder}.
     */
    @Contract(value = "_ -> new", pure = true)
    public static @NotNull QueryBuilder builder(@NotNull Class<?> clazz) {
      return new QueryBuilder(clazz);
    }

    /**
     * Creates new {@code QueryBuilder} instance with default value from {@code query}.
     *
     * @param query The default query.
     * @return An instance of {@code QueryBuilder} with default value from {@code query}.
     */
    public static @NotNull QueryBuilder builder(@NotNull Query query) {
      return builder(query.clazz).type(query.type)
          .name(query.name)
          .paramTypes(query.paramTypes);
    }

    /**
     * Set a name to use for the query.
     *
     * <p>The name is only used in case of field and method queries.
     *
     * @param name Name to use for the query.
     * @return This object, for chaining.
     */
    public QueryBuilder name(@NotNull String name) {
      this.name = name;
      return this;
    }

    /**
     * Set type of the query, used for caching.
     *
     * <p>If you want to query the constructor, use {@link Type#METHOD}.
     *
     * @param type Type of the query
     * @return This object, for chaining.
     */
    public QueryBuilder type(@NotNull Type type) {
      this.type = type;
      return this;
    }

    /**
     * Set the parameter types to use for the query.
     *
     * <p>The parameter types is only used in case of constructor and method queries.
     *
     * @param paramTypes Type to use for the query.
     * @return This object, for chaining.
     */
    public QueryBuilder paramTypes(@NotNull Class<?>... paramTypes) {
      this.paramTypes = paramTypes;
      return this;
    }

    /**
     * Creates new query for the accessor.
     *
     * @return A query for the accessor.
     * @throws NullPointerException If type is null.
     */
    public Query build() {
      if (type == null) {
        if (!name.isEmpty()) {
          throw new NullPointerException("Type cannot be null");
        }
        type = Type.METHOD;
      }
      return new Query(clazz, type, name, paramTypes);
    }
  }
}
