package com.accounts.accountsmanagement.domain.exception;

public final class AccountAlreadyExistsException extends DomainException {

  private static final String MESSAGE_OWNER_AND_TYPE =
      "The user '%s' already has an account of type '%s'.";

  private AccountAlreadyExistsException(final String message) {
    super(message);
  }

  public static AccountAlreadyExistsException becauseOwnerAlreadyHasAccountOfType(
      final String ownerUserId, final String type) {
    return new AccountAlreadyExistsException(
        String.format(MESSAGE_OWNER_AND_TYPE, ownerUserId, type));
  }
}