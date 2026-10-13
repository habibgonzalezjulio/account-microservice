package com.accounts.accountsmanagement.domain.exception;

public final class InvalidAccountTypeException extends DomainException {

  private static final String MESSAGE_INVALID = "The account type '%s' is not valid.";

  private InvalidAccountTypeException(final String message) {
    super(message);
  }

  public static InvalidAccountTypeException becauseValueIsInvalid(final String type) {
    return new InvalidAccountTypeException(String.format(MESSAGE_INVALID, type));
  }
}