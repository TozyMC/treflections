package xyz.tozymc.reflect.resolver;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import xyz.tozymc.minecraft.MinecraftVersion;

public class NmsClassResolver extends ClassResolver {

  private NmsClassResolver() {
    super(MinecraftVersion.getVersion().getNmsPackage());
  }

  @Contract(pure = true)
  public static @NotNull NmsClassResolver resolver() {
    return NmsClassResolverHelper.INSTANCE;
  }

  private static class NmsClassResolverHelper {

    private static final NmsClassResolver INSTANCE = new NmsClassResolver();
  }
}
