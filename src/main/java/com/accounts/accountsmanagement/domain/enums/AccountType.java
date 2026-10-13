package com.accounts.accountsmanagement.domain.enums;

import com.accounts.accountsmanagement.domain.exception.InvalidAccountTypeException;

public enum AccountType {
  SAVINGS,
  CHECKING;

  public static AccountType fromString(final String value) {
    for (final AccountType type : values()) {
      if (type.name().equalsIgnoreCase(value)) {
        return type;
      }
    }
    throw InvalidAccountTypeException.becauseValueIsInvalid(value);
  }
}