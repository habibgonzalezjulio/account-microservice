package com.accounts.accountsmanagement.domain.valueobject;

import com.accounts.accountsmanagement.domain.exception.InvalidOwnerNameException;
import java.util.Objects;

public record OwnerName(String value) {

  private static final int MINIMUM_LENGTH = 3;

  public OwnerName {
    final String normalizedValue =
        Objects.requireNonNull(value, "OwnerName cannot be null").trim();
    validateNotEmpty(normalizedValue);
    validateMinimumLength(normalizedValue);
    value = normalizedValue;
  }

  private static void validateNotEmpty(final String normalizedValue) {
    if (normalizedValue.isEmpty()) {
      throw InvalidOwnerNameException.becauseValueIsEmpty();
    }
  }

  private static void validateMinimumLength(final String normalizedValue) {
    if (normalizedValue.length() < MINIMUM_LENGTH) {
      throw InvalidOwnerNameException.becauseLengthIsTooShort(MINIMUM_LENGTH);
    }
  }

  @Override
  public String toString() {
    return value;
  }
}