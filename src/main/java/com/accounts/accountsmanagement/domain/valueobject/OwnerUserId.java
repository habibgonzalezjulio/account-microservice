package com.accounts.accountsmanagement.domain.valueobject;

import com.accounts.accountsmanagement.domain.exception.InvalidOwnerUserIdException;
import java.util.Objects;

public record OwnerUserId(String value) {

  public OwnerUserId {
    final String normalizedValue =
        Objects.requireNonNull(value, "OwnerUserId cannot be null").trim();
    validateNotEmpty(normalizedValue);
    value = normalizedValue;
  }

  private static void validateNotEmpty(final String normalizedValue) {
    if (normalizedValue.isEmpty()) {
      throw InvalidOwnerUserIdException.becauseValueIsEmpty();
    }
  }

  @Override
  public String toString() {
    return value;
  }
}