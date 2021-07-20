package xyz.tozymc.reflect.util;

import java.lang.reflect.AccessibleObject;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

public final class AccessUtil {

  private AccessUtil() {}

  @Contract("null -> null")
  public static <T extends AccessibleObject> @Nullable T forceAccess(@Nullable T accessibleObj) {
    if (accessibleObj == null) {
      return null;
    }
    if (accessibleObj.isAccessible()) {
      return accessibleObj;
    }
    accessibleObj.setAccessible(true);
    return accessibleObj;
  }
}
