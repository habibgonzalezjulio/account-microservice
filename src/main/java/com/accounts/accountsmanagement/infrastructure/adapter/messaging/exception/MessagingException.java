package com.accounts.accountsmanagement.infrastructure.adapter.messaging.exception;

public final class MessagingException extends RuntimeException {

  private static final String MESSAGE_SERIALIZATION =
      "Failed to serialize the event '%s' to JSON.";

  private MessagingException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public static MessagingException becauseSerializationFailed(
      final String eventName, final Throwable cause) {
    return new MessagingException(String.format(MESSAGE_SERIALIZATION, eventName), cause);
  }
}