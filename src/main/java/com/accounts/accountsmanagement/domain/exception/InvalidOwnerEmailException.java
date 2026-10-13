package com.accounts.accountsmanagement.domain.exception;

public final class InvalidOwnerEmailException extends DomainException {

  private static final String MESSAGE_EMPTY = "The owner email must not be empty.";
  private static final String MESSAGE_INVALID_FORMAT = "The owner email '%s' has an invalid format.";

  private InvalidOwnerEmailException(final String message) {
    super(message);
  }

  public static InvalidOwnerEmailException becauseValueIsEmpty() {
    return new InvalidOwnerEmailException(MESSAGE_EMPTY);
  }

  public static InvalidOwnerEmailException becauseFormatIsInvalid(final String email) {
    return new InvalidOwnerEmailException(String.format(MESSAGE_INVALID_FORMAT, email));
  }
}