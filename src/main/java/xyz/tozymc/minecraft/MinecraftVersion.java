package xyz.tozymc.minecraft;

import org.bukkit.Bukkit;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * An enum of all Minecraft packager version since 1.7.
 *
 * @author TozyMC
 * @since 1.0
 */
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
  public static final String NET_MINECRAFT_PACKAGE = "net.minecraft";
  public static final String NMS_PACKAGE_PREFIX = "net.minecraft.server.";
  public static final String OCB_PACKAGE_PREFIX = "org.bukkit.craftbukkit.";

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

  /**
   * Gets current Minecraft running version.
   *
   * @return Current Minecraft running version.
   */
  @Contract(pure = true)
  public static MinecraftVersion getVersion() {
    return CURRENT_VERSION;
  }

  /**
   * Checks if the current version is newer than the specified version.
   *
   * @param version Specified version to check.
   * @return True if current version is newer than the specified version.
   */
  @Contract(pure = true)
  public boolean isNewerThan(@NotNull MinecraftVersion version) {
    return this.version >= version.version;
  }

  /**
   * Checks if the current version is older than the specified version.
   *
   * @param version Specified version to check.
   * @return True if current version is older than the specified version.
   */
  public boolean isOlderThan(@NotNull MinecraftVersion version) {
    return this.version < version.version;
  }

  /**
   * Checks if the current version is equal to the specified version.
   *
   * @param version Specified version to check.
   * @return True if the current version is equal to the specified version.
   */
  public boolean equals(@NotNull MinecraftVersion version) {
    return this.version == version.version;
  }

  /**
   * Checks if current version is in version range.
   *
   * @param oldVer Minimum allowed version
   * @param newVer Maximum allowed version
   * @return True if current version is in version range.
   */
  public boolean isInRange(@NotNull MinecraftVersion oldVer, @NotNull MinecraftVersion newVer) {
    return isNewerThan(oldVer) && isOlderThan(newVer);
  }

  /**
   * Gets net.minecraft.server package string with version.
   *
   * <p><b>Notes: </b>Since Minecraft 1.17, this method always returns {@link
   * #NET_MINECRAFT_PACKAGE}.
   *
   * @return Nms package string with version.
   */
  @Contract(pure = true)
  public @NotNull String getNmsPackage() {
    if (nmsPackage) {
      return NMS_PACKAGE_PREFIX + name();
    }
    return NET_MINECRAFT_PACKAGE;
  }

  /**
   * Gets org.bukkit.craftbukkit package string with version.
   *
   * @return Ocb package string with version.
   */
  @Contract(pure = true)
  public @NotNull String getOcbPackage() {
    return OCB_PACKAGE_PREFIX + name();
  }

  /**
   * Gets the package version.
   *
   * @return The package version.
   */
  @Contract(pure = true)
  public @NotNull String getPackageVersion() {
    return name();
  }

  /**
   * Gets version in integer.
   *
   * <p>It's often used to compare versions.
   *
   * @return Version in int.
   */
  @Contract(pure = true)
  public int version() {
    return version;
  }

  /**
   * Checks if the version has net.minecraft.server package.
   *
   * @return True if the version has net.minecraft.server package.
   */
  @Contract(pure = true)
  public boolean isNmsPackage() {
    return nmsPackage;
  }
}
