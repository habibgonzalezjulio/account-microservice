package com.accounts.accountsmanagement.domain.exception;

public final class InvalidAccountStatusException extends DomainException {

  private static final String MESSAGE_INVALID = "The account status '%s' is not valid.";

  private InvalidAccountStatusException(final String message) {
    super(message);
  }

  public static InvalidAccountStatusException becauseValueIsInvalid(final String status) {
    return new InvalidAccountStatusException(String.format(MESSAGE_INVALID, status));
  }
}