package xyz.tozymc.reflect.resolver.minecraft;

import static xyz.tozymc.minecraft.MinecraftVersion.NET_MINECRAFT_PACKAGE;
import static xyz.tozymc.minecraft.MinecraftVersion.getVersion;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import xyz.tozymc.reflect.resolver.ClassResolver;

public final class NmsClassResolver extends ClassResolver {

  private NmsClassResolver() {super();}

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
