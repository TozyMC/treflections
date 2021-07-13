package xyz.tozymc.reflect.resolver;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class ClassResolver {

  private final String classPackage;
  private final Map<String, Class<?>> resolvedClasses = new HashMap<>();

  protected ClassResolver(@NotNull String classPackage) {this.classPackage = classPackage;}

  private ClassResolver() {this("");}

  @Contract(pure = true)
  public static @NotNull ClassResolver resolver() {
    return ClassResolverHelper.INSTANCE;
  }

  public Class<?> resolve(String @NotNull ... names) {
    Class<?> clazz = null;
    for (String name : names) {
      try {
        clazz = resolve(name);
      } catch (Exception ignored) {
      }
      if (clazz != null) {
        return clazz;
      }
    }
    String cannotResolveClasses = "Cannot resolve classes ";
    throw new RuntimeException(cannotResolveClasses + Arrays.toString(names),
        new ClassCastException());
  }

  @SuppressWarnings("unchecked")
  public <T> Class<T> resolve(@NotNull String name) {
    String className = formatClassName(name);
    if (resolvedClasses.containsKey(className)) {
      return (Class<T>) resolvedClasses.get(className);
    }
    try {
      Class<T> clazz = (Class<T>) Class.forName(className);
      resolvedClasses.put(className, clazz);
      return clazz;
    } catch (ClassNotFoundException e) {
      String cannotResolveClass = "Cannot resolve class ";
      throw new RuntimeException(cannotResolveClass + className, e.getCause());
    }
  }

  private String formatClassName(String name) {
    return classPackage + '.' + name;
  }

  public @NotNull String getClassPackage() {
    return classPackage;
  }

  private static class ClassResolverHelper {

    private static final ClassResolver INSTANCE = new ClassResolver();
  }
}
