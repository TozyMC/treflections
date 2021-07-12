package xyz.tozymc.reflect.util;

import java.lang.reflect.AccessibleObject;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

public final class AccessUtil {

  private AccessUtil() {}

  @Contract("_ -> param1")
  public static <T extends AccessibleObject> @Nullable T forceAccess(@Nullable T accessibleObj) {
    if (accessibleObj == null) {
      return null;
    }
    accessibleObj.setAccessible(true);
    return accessibleObj;
  }
}
