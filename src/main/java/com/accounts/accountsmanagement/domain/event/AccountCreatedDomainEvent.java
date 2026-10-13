package com.accounts.accountsmanagement.domain.event;

import com.accounts.accountsmanagement.domain.model.AccountModel;
import java.util.Map;
import lombok.Getter;

@Getter
public final class AccountCreatedDomainEvent extends DomainEvent {

  private static final String EVENT_NAME = "account.created";

  private final AccountModel account;

  public AccountCreatedDomainEvent(final AccountModel account) {
    super(EVENT_NAME);
    this.account = account;
  }

  @Override
  public Map<String, String> payload() {
    return Map.of(
        "id", account.getId().value(),
        "userId", account.getOwnerUserId().value(),
        "name", account.getOwnerName().value(),
        "email", account.getOwnerEmail().value(),
        "type", account.getType().name(),
        "status", account.getStatus().name());
  }
}