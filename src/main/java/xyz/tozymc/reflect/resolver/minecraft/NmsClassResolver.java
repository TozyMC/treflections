package xyz.tozymc.reflect.resolver.minecraft;

import static xyz.tozymc.minecraft.MinecraftVersion.NET_MINECRAFT_PACKAGE;
import static xyz.tozymc.minecraft.MinecraftVersion.getVersion;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import xyz.tozymc.reflect.resolver.ClassResolver;

/**
 * {@code NmsClassResolver} provides methods to find nms class and cache it if the class is found.
 *
 * <p>This class automatically appends net.minecraft.server to the class name.
 *
 * @author TozyMC
 * @see ClassResolver
 * @since 1.0
 */
public final class NmsClassResolver extends ClassResolver {

  private NmsClassResolver() {
    super();
  }

  /**
   * Gets the {@code NmsClassWrapper} instance.
   *
   * <p>This class automatically appends net.minecraft.server to the class name.
   *
   * @return The {@code NmsClassWrapper} instance.
   */
  @Contract(pure = true)
  public static @NotNull NmsClassResolver resolver() {
    return NmsClassResolverHelper.INSTANCE;
  }

  @Override
  public <T> Class<T> resolve(@NotNull String name) {
    if (name.contains(NET_MINECRAFT_PACKAGE)) {
      return super.resolve(name);
    }

    if (name.contains(".") && getVersion().isNmsPackage()) {
      String[] path = name.split("\\.");
      name = path[path.length - 1];
    }

    name = getVersion().getNmsPackage() + '.' + name;
    return super.resolve(name);
  }

  private static class NmsClassResolverHelper {

    private static final NmsClassResolver INSTANCE = new NmsClassResolver();
  }
}
