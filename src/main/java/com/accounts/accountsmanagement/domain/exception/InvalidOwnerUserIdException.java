package com.accounts.accountsmanagement.domain.exception;

public final class InvalidOwnerUserIdException extends DomainException {

  private static final String MESSAGE_EMPTY = "The owner user id must not be empty.";

  private InvalidOwnerUserIdException(final String message) {
    super(message);
  }

  public static InvalidOwnerUserIdException becauseValueIsEmpty() {
    return new InvalidOwnerUserIdException(MESSAGE_EMPTY);
  }
}