package xyz.tozymc.reflect.resolver.minecraft;

import static xyz.tozymc.minecraft.MinecraftVersion.OCB_PACKAGE_PREFIX;
import static xyz.tozymc.minecraft.MinecraftVersion.getVersion;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import xyz.tozymc.reflect.resolver.ClassResolver;

/**
 * {@code OcbClassResolver} provides methods to find CraftBukkit class and cache it if the class is
 * found.
 *
 * <p>This class automatically appends org.bukkit.craftbukkit to the class name.
 *
 * @author TozyMC
 * @see ClassResolver
 * @since 1.0
 */
public final class OcbClassResolver extends ClassResolver {

  private OcbClassResolver() {
    super();
  }

  /**
   * Gets the {@code OcbClassWrapper} instance.
   *
   * <p>This class automatically appends org.bukkit.craftbukkit to the class name.
   *
   * @return The {@code OcbClassWrapper} instance.
   */
  @Contract(pure = true)
  public static @NotNull OcbClassResolver resolver() {
    return OcbClassResolverHelper.INSTANCE;
  }

  @Override
  public <T> Class<T> resolve(@NotNull String name) {
    if (!name.startsWith(OCB_PACKAGE_PREFIX)) {
      name = getVersion().getOcbPackage() + '.' + name;
    }
    return super.resolve(name);
  }

  private static class OcbClassResolverHelper {

    private static final OcbClassResolver INSTANCE = new OcbClassResolver();
  }
}
