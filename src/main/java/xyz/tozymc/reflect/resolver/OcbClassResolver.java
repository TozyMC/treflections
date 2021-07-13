package xyz.tozymc.reflect.resolver;

import static xyz.tozymc.minecraft.MinecraftVersion.OCB_PACKAGE_PREFIX;
import static xyz.tozymc.minecraft.MinecraftVersion.getVersion;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public final class OcbClassResolver extends ClassResolver {

  private OcbClassResolver() {
    super();
  }

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
