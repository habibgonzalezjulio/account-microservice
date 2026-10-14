package com.accounts.accountsmanagement.domain.valueobject;

import com.accounts.accountsmanagement.domain.exception.InvalidAccountIdException;
import java.util.Objects;
import java.util.UUID;

public record AccountId(String value) {

  public AccountId {
    final String normalizedValue =
        Objects.requireNonNull(value, "AccountId cannot be null").trim();
    validateNotEmpty(normalizedValue);
    value = normalizedValue;
  }

  public static AccountId generate() {
    return new AccountId(UUID.randomUUID().toString());
  }

  private static void validateNotEmpty(final String normalizedValue) {
    if (normalizedValue.isEmpty()) {
      throw InvalidAccountIdException.becauseValueIsEmpty();
    }
  }

  @Override
  public String toString() {
    return value;
  }
}