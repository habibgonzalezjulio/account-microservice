package com.accounts.accountsmanagement.domain.exception;

public final class InvalidOwnerNameException extends DomainException {

  private static final String MESSAGE_EMPTY = "The owner name must not be empty.";
  private static final String MESSAGE_TOO_SHORT =
      "The owner name must have at least %d characters.";

  private InvalidOwnerNameException(final String message) {
    super(message);
  }

  public static InvalidOwnerNameException becauseValueIsEmpty() {
    return new InvalidOwnerNameException(MESSAGE_EMPTY);
  }

  public static InvalidOwnerNameException becauseLengthIsTooShort(final int minimumLength) {
    return new InvalidOwnerNameException(String.format(MESSAGE_TOO_SHORT, minimumLength));
  }
}