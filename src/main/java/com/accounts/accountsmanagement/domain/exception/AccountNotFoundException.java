package com.accounts.accountsmanagement.domain.exception;

public final class AccountNotFoundException extends DomainException {

  private static final String MESSAGE_BY_ID = "The account with id '%s' was not found.";

  private AccountNotFoundException(final String message) {
    super(message);
  }

  public static AccountNotFoundException becauseIdWasNotFound(final String accountId) {
    return new AccountNotFoundException(String.format(MESSAGE_BY_ID, accountId));
  }
}