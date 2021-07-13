package xyz.tozymc.reflect.resolver;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import xyz.tozymc.minecraft.MinecraftVersion;

public class OcbClassResolver extends ClassResolver {

  protected OcbClassResolver() {
    super(MinecraftVersion.getVersion().getOcbPackage());
  }

  @Contract(pure = true)
  public static @NotNull OcbClassResolver resolver() {
    return OcbClassResolverHelper.INSTANCE;
  }

  private static class OcbClassResolverHelper {

    private static final OcbClassResolver INSTANCE = new OcbClassResolver();
  }
}
