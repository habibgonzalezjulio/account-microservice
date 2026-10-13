package com.accounts.accountsmanagement.infrastructure.adapter.persistence.exception;

public final class PersistenceException extends RuntimeException {

  private static final String MESSAGE_SAVE = "Failed to save account with ID: '%s'.";
  private static final String MESSAGE_FIND = "Failed to find account with ID: '%s'.";
  private static final String MESSAGE_FIND_BY_OWNER =
      "Failed to find account by owner user ID: '%s'.";
  private static final String MESSAGE_ALL = "Failed to retrieve all accounts.";

  private PersistenceException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public static PersistenceException becauseSaveFailed(
      final String accountId, final Throwable cause) {
    return new PersistenceException(String.format(MESSAGE_SAVE, accountId), cause);
  }

  public static PersistenceException becauseFindByIdFailed(
      final String accountId, final Throwable cause) {
    return new PersistenceException(String.format(MESSAGE_FIND, accountId), cause);
  }

  public static PersistenceException becauseFindByOwnerFailed(
      final String ownerUserId, final Throwable cause) {
    return new PersistenceException(String.format(MESSAGE_FIND_BY_OWNER, ownerUserId), cause);
  }

  public static PersistenceException becauseFindAllFailed(final Throwable cause) {
    return new PersistenceException(MESSAGE_ALL, cause);
  }
}