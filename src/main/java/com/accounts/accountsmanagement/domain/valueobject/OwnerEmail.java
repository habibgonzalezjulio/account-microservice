package com.accounts.accountsmanagement.domain.valueobject;

import com.accounts.accountsmanagement.domain.exception.InvalidOwnerEmailException;
import java.util.Objects;
import java.util.regex.Pattern;

public record OwnerEmail(String value) {

  private static final Pattern EMAIL_PATTERN =
      Pattern.compile("^[a-zA-Z0-9._%+\\-]+@[a-zA-Z0-9.\\-]+\\.[a-zA-Z]{2,}$");

  public OwnerEmail {
    final String normalizedValue =
        Objects.requireNonNull(value, "OwnerEmail cannot be null").trim().toLowerCase();
    validateNotEmpty(normalizedValue);
    validateFormat(normalizedValue);
    value = normalizedValue;
  }

  private static void validateNotEmpty(final String normalizedValue) {
    if (normalizedValue.isEmpty()) {
      throw InvalidOwnerEmailException.becauseValueIsEmpty();
    }
  }

  private static void validateFormat(final String normalizedValue) {
    if (!EMAIL_PATTERN.matcher(normalizedValue).matches()) {
      throw InvalidOwnerEmailException.becauseFormatIsInvalid(normalizedValue);
    }
  }

  @Override
  public String toString() {
    return value;
  }
}