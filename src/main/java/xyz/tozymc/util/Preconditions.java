package xyz.tozymc.util;

import java.util.Arrays;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("UnusedReturnValue")
public final class Preconditions {

  private Preconditions() {}

  @Contract(value = "!null, _ -> param1; null, _ -> fail", pure = true)
  public static <T> @NotNull T checkNotNull(T reference, String message) {
    if (reference != null) {
      return reference;
    }
    throw new NullPointerException(message);
  }

  @Contract("!null, _, _ -> param1; null, _, _ -> fail")
  public static <T> @NotNull T checkNotNull(T reference, String template, Object... args) {
    if (reference != null) {
      return reference;
    }
    throw new NullPointerException(formatMessage(template, args));
  }

  @Contract(value = "false, _ -> fail", pure = true)
  public static void checkArgument(boolean expression, String message) {
    if (!expression) {
      throw new IllegalArgumentException(message);
    }
  }

  @Contract("false, _, _ -> fail")
  public static void checkArgument(boolean expression, String template, Object... args) {
    if (!expression) {
      throw new IllegalArgumentException(formatMessage(template, args));
    }
  }

  @Contract(value = "false, _ -> fail", pure = true)
  public static void checkState(boolean expression, String message) {
    if (!expression) {
      throw new IllegalStateException(message);
    }
  }

  @Contract("false, _, _ -> fail")
  public static void checkState(boolean expression, String template, Object... args) {
    if (!expression) {
      throw new IllegalStateException(formatMessage(template, args));
    }
  }

  private static String formatMessage(String template, Object[] args) {
    return String.format(template, Arrays.stream(args).map(String::valueOf).toArray());
  }
}
