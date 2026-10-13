package com.accounts.accountsmanagement.domain.enums;

import com.accounts.accountsmanagement.domain.exception.InvalidAccountStatusException;

public enum AccountStatus {
  ACTIVE,
  INACTIVE,
  BLOCKED;

  public static AccountStatus fromString(final String value) {
    for (final AccountStatus status : values()) {
      if (status.name().equalsIgnoreCase(value)) {
        return status;
      }
    }
    throw InvalidAccountStatusException.becauseValueIsInvalid(value);
  }
}