package xyz.tozymc.minecraft;

import org.bukkit.Bukkit;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public enum MinecraftVersion {
  UNKNOWN(-1),

  v1_7_R1(1071),
  v1_7_R2(1072),
  v1_7_R3(1073),
  v1_7_R4(1074),

  v1_8_R1(1081),
  v1_8_R2(1082),
  v1_8_R3(1083),

  v1_9_R1(1091),
  v1_9_R2(1092),

  v1_10_R1(1101),

  v1_11_R1(1111),

  v1_12_R1(1121),

  v1_13_R1(1131),
  v1_13_R2(1132),

  v1_14_R1(1141),

  v1_15_R1(1151),

  v1_16_R1(1161),
  v1_16_R2(1162),
  v1_16_R3(1163),

  v1_17_R1(1164, false);

  public static final MinecraftVersion CURRENT_VERSION;
  private static final String NET_MINECRAFT_PACKAGE = "net.minecraft";
  private static final String NMS_PACKAGE_PREFIX = "net.minecraft.server.";
  private static final String OCB_PACKAGE_PREFIX = "org.bukkit.craftbukkit.";

  static {
    String packageVersion = "";
    try {
      String ocbPackage = Bukkit.getServer().getClass().getPackage().getName();
      packageVersion = ocbPackage.substring(ocbPackage.lastIndexOf('.') + 1);
    } catch (Exception e) {
      Bukkit.getLogger().warning("Cannot detect server version!");
    }
    CURRENT_VERSION = packageVersion.isEmpty() ? UNKNOWN : MinecraftVersion.valueOf(packageVersion);
  }

  private final int version;
  private final boolean nmsPackage;

  MinecraftVersion(int version) {
    this.version = version;
    this.nmsPackage = true;
  }

  MinecraftVersion(int version, boolean nmsPackage) {
    this.version = version;
    this.nmsPackage = nmsPackage;
  }

  public static MinecraftVersion getVersion() {
    return CURRENT_VERSION;
  }

  public boolean isNewerThan(@NotNull MinecraftVersion version) {
    return this.version >= version.version;
  }

  public boolean isOlderThan(@NotNull MinecraftVersion version) {
    return this.version < version.version;
  }

  public boolean equals(@NotNull MinecraftVersion version) {
    return this.version == version.version;
  }

  public boolean isInRange(@NotNull MinecraftVersion oldVer, @NotNull MinecraftVersion newVer) {
    return isNewerThan(oldVer) && isOlderThan(newVer);
  }

  @Contract(pure = true)
  public @NotNull String getNmsPackage() {
    if (nmsPackage) {
      return NMS_PACKAGE_PREFIX + name();
    }
    return NET_MINECRAFT_PACKAGE;
  }

  @Contract(pure = true)
  public @NotNull String getOcbPackage() {
    return OCB_PACKAGE_PREFIX + name();
  }

  @Contract(pure = true)
  public @NotNull String getPackageVersion() {
    return name();
  }

  public int version() {
    return version;
  }

  public boolean isNmsPackage() {
    return nmsPackage;
  }
}
